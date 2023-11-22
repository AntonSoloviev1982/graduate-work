package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.config.WebSecurityConfig;
import ru.skypro.homework.dto.AdDtoOut;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.dto.AdDtoIn;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.mapper.AdMapper;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdController.class)
@Import(WebSecurityConfig.class)
public class AdControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AdRepository adRepository;
    @MockBean
    private UserRepository userRepository;
    @SpyBean
    private AdService adService;
    @SpyBean
    private AdMapper adMapper;
    @Autowired
    private ObjectMapper objectMapper;

    Principal principal = mock(Principal.class);
    @Test
    //без этой аннотации MockMvc.perform будет выдавать NullPointerException
    @WithMockUser(username = "user")
    public void createAdTest()  throws Exception {
        //готовим тело запроса
        AdDtoIn adDtoIn = new AdDtoIn();
        adDtoIn.setTitle("MyAd");
        adDtoIn.setPrice(123);
        adDtoIn.setDescription("Описание");

        //готовим затычку для userRepository.findByUsername
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        //затыкаем userRepository.findByUsername
        when(principal.getName()).thenReturn("user"); //имя принципала будем искать в базе
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        //готовим затычку для userRepository.save
        Ad adBeforeSave = adMapper.toEntity(adDtoIn, principal);
        Ad adAfterSave = adMapper.toEntity(adDtoIn, principal);
        adAfterSave.setId(123);
        //Если в save задать adBeforeSave, то сравнение не удастся и заглушка не сработает
        //Поэтому затыкаю через any(), но потом проверю параметр
        when(adRepository.save(any())).thenReturn(adAfterSave);

        AdDtoOut expectedAdDtoOut = adMapper.toAdDtoOut(adAfterSave);

        //MockPart filePart = new MockPart("image", new byte[3]);
        //filePart.getHeaders().setContentType(MediaType.IMAGE_JPEG);
        //Cоздать два MockPart не удалось.
        //mockMvc.perform передает обе части (и properties, и image) в параметрах
        //а надо в теле. В результате тело (пустое) не идентифицируется контроллером.
        //Однако получилось передать в file и part
        MockMultipartFile file = new MockMultipartFile("image", "123".getBytes());
        MockPart adDtoInPart = new MockPart("properties", objectMapper.writeValueAsString(adDtoIn).getBytes());
        adDtoInPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(multipart(HttpMethod.POST,"/ads")
                        .file(file)
                        .part(adDtoInPart) //objectMapper.writeValueAsString(adDtoIn))
                ).andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    AdDtoOut actualAdDtoOut = objectMapper.readValue(content, AdDtoOut.class);
                    //проверяем, что к нам вернулся объект, которым мы замокали репозиторий
                    assertThat(actualAdDtoOut).isEqualTo(expectedAdDtoOut);
                });

        //Проверим, чем мы накормили adRepository.save
        ArgumentCaptor<Ad> adCaptor = ArgumentCaptor.forClass(Ad.class);
        verify(adRepository,times(1)).save(adCaptor.capture());
        Ad adFact = adCaptor.getValue();
        //assertEquals(adBeforeSave, adFact);  //так не равны, поэтому сравниваем поля
        assertEquals(adBeforeSave.getId(), adFact.getId()); //сравнение null дает истину
        assertEquals(adBeforeSave.getUser().getId(), adFact.getUser().getId());
        assertEquals(adBeforeSave.getUser().getUsername(), adFact.getUser().getUsername());
        assertEquals(adBeforeSave.getPrice(), adFact.getPrice());
        assertEquals(adBeforeSave.getTitle(), adFact.getTitle());
    }

    @Test
    @WithMockUser(username = "user", roles = "ADMIN")
    public void deleteAdTest()  throws Exception {
        mockMvc.perform(delete("/ads/123").principal(principal)
                ).andExpect(status().isOk());

        //Проверим, чем мы накормили adRepository.deleteById
        ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(adRepository,times(1)).deleteById(intCaptor.capture());
        Integer fact = intCaptor.getValue();
        assertEquals(123, fact);
    }
}
