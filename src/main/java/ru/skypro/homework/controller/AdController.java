package ru.skypro.homework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDtoIn;
import ru.skypro.homework.dto.AdExtendedDtoOut;
import ru.skypro.homework.dto.AdsDtoOut;
import ru.skypro.homework.dto.AdDtoOut;

@RestController
@RequestMapping("ads")
public class AdController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdController.class);

    @GetMapping
    public AdsDtoOut getAllAds() {
        LOGGER.info("Получен запрос для getAllAds");
        return new AdsDtoOut();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)  //MULTIPART_FORM_DATA_VALUE, иначе сваггер предложит заполнить json
    public AdDtoOut addAd(@RequestPart("properties") String jsonAdDtoIn,
                          //если первый параметр - объект типа AdDtoIn,
                          //то и Postman и Swagger возвращают - status 415 Unsupported Media Type,
                          //а в логе будет Content type 'application/octet-stream' not supported
                          //строку же получаю без ошибок
                          //как я прочел, все части должны преобразовываться в поток
                          //Однако https://www.baeldung.com/sprint-boot-multipart-requests и др источники утверждают, что объект возможен

                          @RequestPart MultipartFile image ) {
        ObjectMapper objectMapper = new ObjectMapper();
        AdDtoIn adDtoIn;
        try {
            adDtoIn = objectMapper.readValue(jsonAdDtoIn, new TypeReference<AdDtoIn>(){});
        } catch( JsonProcessingException e) {
            throw  new RuntimeException(); //временно
        }
        LOGGER.info("Получен запрос для addAd: properties = " + adDtoIn + ", image = " + image);
        return new AdDtoOut();
    }
    @GetMapping("{id}")
    public AdExtendedDtoOut getAdExtended(@PathVariable("id") int id) {
        LOGGER.info("Получен запрос для getAdExtended: id = " + id);
        return new AdExtendedDtoOut();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> DeleteAd(@PathVariable("id") int id) {
        LOGGER.info("Получен запрос для DeleteAd: id = " + id);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("{id}")
    public AdDtoOut updateAd(@PathVariable("id") int id) {
        LOGGER.info("Получен запрос для updateAd: id = " + id);
        return new AdDtoOut();
    }
    @GetMapping("me")
    public AdsDtoOut getMyAds() {
        LOGGER.info("Получен запрос для getMyAds");
        return new AdsDtoOut();
    }
    @PatchMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public byte[] updateImage(@PathVariable("id") int id, @RequestBody MultipartFile image) {
        LOGGER.info("Получен запрос для updateImage:  id = " + id + ", image = " + image);
        return new byte[0];
    }

}
