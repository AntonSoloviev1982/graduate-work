package ru.skypro.homework.service;

import liquibase.pro.packaged.R;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdExtendedDtoOut;
import ru.skypro.homework.dto.AdsDtoOut;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class AdService {
    private final AdRepository adRepository;
    private final AdMapper adMapper;

    //Читать текст ошибки в теле ответа фронт не будет. Прокинем сообщение хотя бы для Logger
    private Supplier<EntityNotFoundException> excSuppl(int id){
        return ()-> new EntityNotFoundException("Ad with id " + id + " not found");}

    public AdService(AdRepository adRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
    }

    public void updateImage(int id, MultipartFile image) {
        Ad ad = adRepository.findById(id).orElseThrow(excSuppl(id));
        try {
            ad.setImage(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        adRepository.save(ad);
    }

    public AdsDtoOut getMyAds() {
        //пока не знаю, как получить User или userId
        //Если был User, то можно взять у него объявления без обращения к репозиторию
        return adMapper.toAdsDtoOut(
                adRepository.findAllByUserId(1)  //эта часть временная
                        .stream().map(adMapper::toAdDtoOut).collect(Collectors.toList()));
    }
    public AdsDtoOut getAllAds() {
        return adMapper.toAdsDtoOut(
                adRepository.findAll()
                        .stream().map(adMapper::toAdDtoOut).collect(Collectors.toList()));
    }

    public byte[] getImage(int id) {
        return adRepository.findById(id).orElseThrow(excSuppl(id)).getImage();
    }

    public AdExtendedDtoOut getAdById(int id) {
        return adMapper.toAdExtendedDtoOut(adRepository.findById(id).orElseThrow(excSuppl(id)));
    }
}
