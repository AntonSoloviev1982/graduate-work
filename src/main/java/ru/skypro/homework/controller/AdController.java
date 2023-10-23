package ru.skypro.homework.controller;

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
@CrossOrigin(value = "http://localhost:3000")
public class AdController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdController.class);

    @GetMapping
    public AdsDtoOut getAllAds() {
        LOGGER.info("Получен запрос для getAllAds");
        return new AdsDtoOut();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)  //MULTIPART_FORM_DATA_VALUE, иначе сваггер предложит заполнить json
    public AdDtoOut addAd(@RequestPart("properties") AdDtoIn adDtoIn,
                          //если первый параметр - объект типа AdDtoIn,
                          //то Swagger не справится в такой посылкой, он пошлет строку.
                          //А в Postman надо, используя 3 точки, открыть колонку ТипКонтента и задать там application/json
                          @RequestPart MultipartFile image ) {
        LOGGER.info("Получен запрос для addAd: properties = " + adDtoIn + ", image = " + image);
        return new AdDtoOut();
    }
    @GetMapping("{id}")
    public AdExtendedDtoOut getAdExtended(@PathVariable int id) {
        LOGGER.info("Получен запрос для getAdExtended: id = " + id);
        return new AdExtendedDtoOut();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAd(@PathVariable int id) {
        LOGGER.info("Получен запрос для deleteAd: id = " + id);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("{id}")
    public AdDtoOut updateAd(@PathVariable int id, @RequestBody AdDtoIn adDtoIn) {
        LOGGER.info("Получен запрос для updateAd: id = " + id +", adDtoIn = " + adDtoIn);
        return new AdDtoOut();
    }
    @GetMapping("me")
    public AdsDtoOut getMyAds() {
        LOGGER.info("Получен запрос для getMyAds");
        return new AdsDtoOut();
    }
    @PatchMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public byte[] updateImage(@PathVariable int id, @RequestPart MultipartFile image) {
        LOGGER.info("Получен запрос для updateImage:  id = " + id + ", image = " + image);
        byte[] arr = {1,2};
        return arr;
    }
}
