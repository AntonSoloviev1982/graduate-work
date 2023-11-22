package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.core.internal.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.config.WebSecurityConfig;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.AdComment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CheckUserService;
import ru.skypro.homework.service.CommentService;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class)
@Import(WebSecurityConfig.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AdRepository adRepository;
    @MockBean
    private CommentRepository commentRepository;
    @SpyBean
    private CommentMapper commentMapper;
    @SpyBean
    private CommentService commentService;
    @SpyBean
    private CheckUserService checkUserService;
    @MockBean
    private AdService adService;

    private User user1;
    private User user2;
    private Ad ad;
    @BeforeEach
    void setup() {
        user1 = new User();
        user1.setId(1);
        user1.setUsername("aaaa@mail.ru");
        user1.setPassword(encoder.encode("aaaa1111"));
        user1.setFirstName("Harry");
        user1.setLastName("Potter");
        user1.setPhone("+7111-111-11-11");
        user1.setRole(Role.USER);
        user1.setImage(new byte[]{1, 1, 1, 1});

        user2 = new User();
        user2.setId(2);
        user2.setUsername("bbbb@mail.ru");
        user2.setPassword(encoder.encode("bbbb2222"));
        user2.setFirstName("Hermione");
        user2.setLastName("Granger");
        user2.setPhone("+7222-222-22-22");
        user2.setRole(Role.USER);
        user2.setImage(new byte[]{2, 2, 2, 2});

        ad = new Ad();
        ad.setId(3);
        ad.setUser(user2);
        ad.setTitle("Test title.");
        ad.setPrice(1000);
        ad.setDescription("Very good thing.");
        ad.setImage(new byte[]{3, 3, 3, 3});
    }

    @Test
    void getCommentsTest() {
    }

    @Test
    @WithMockUser(username = "aaaa@mail.ru")
    void addCommentTest() throws Exception{
        CreateOrUpdateComment createOrUpdateComment = new CreateOrUpdateComment();
        createOrUpdateComment.setText("Тестовый комментарий 1.");

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user1));
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user1));
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(ad));

        AdComment adComment = new AdComment();
        adComment.setId(777);
        adComment.setText("Тестовый комментарий 1.");
        adComment.setCreatedAt(LocalDateTime.now());
        adComment.setAd(adRepository.findById(3).orElseThrow(() -> new EntityNotFoundException("3")));
        adComment.setUser(userRepository.findById(1).orElseThrow(() -> new EntityNotFoundException("1")));

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return user1.getUsername();
            }
        };
        when(commentRepository.save(any(AdComment.class))).thenReturn(adComment);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/ads/3/comments")
                        .content(objectMapper.writeValueAsString(createOrUpdateComment))
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( result -> {
                    jsonPath("$.author").value(1);
                    jsonPath("$.authorFirstName").value("Harry");
                    jsonPath("$.text").value("Тестовый комментарий 1.");
                });
        verify(commentRepository, times(1)).save(any(AdComment.class));

    }

    @Test
    void updateCommentTest() {
    }
/*
    @Test
    @WithMockUser(username = "aaaa@mail.ru")
    void deleteCommentTest() throws Exception{
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user1));
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user1));
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(ad));

        AdComment adComment = new AdComment();
        adComment.setId(777);
        adComment.setText("Тестовый комментарий 1.");
        adComment.setCreatedAt(LocalDateTime.now());
        adComment.setAd(adRepository.findById(3).orElseThrow(() -> new EntityNotFoundException("3")));
        adComment.setUser(userRepository.findById(1).orElseThrow(() -> new EntityNotFoundException("1")));

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return user1.getUsername();
            }
        };

        when(commentRepository.findById(any(Integer.class))).thenReturn(Optional.of(adComment));
        when(checkUserService.getUsernameByComment(any(Integer.class))).thenReturn(user1.getUsername());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ads/3/comments/777")
                        .principal(principal))
                .andExpect(status().isOk());
    }

 */
}