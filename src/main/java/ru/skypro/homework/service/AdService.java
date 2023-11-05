package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDtoIn;
import ru.skypro.homework.dto.AdDtoOut;
import ru.skypro.homework.dto.AdExtendedDtoOut;
import ru.skypro.homework.dto.AdsDtoOut;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class AdService {
    private final AdRepository adRepository;
    private final AdMapper adMapper;

    private final UserRepository userRepository;

    //Читать текст ошибки в теле ответа фронт не будет. Прокинем сообщение хотя бы для Logger
    private Supplier<EntityNotFoundException> excSuppl(int id){
        return ()-> new EntityNotFoundException("Ad with id " + id + " not found");}

    public AdService(AdRepository adRepository, AdMapper adMapper, UserRepository userRepository) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
        this.userRepository = userRepository;
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

    public AdsDtoOut getMyAds(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        //пока не знаю, как получить User или userId
        //Если бы был User, то можно взять у него объявления без обращения к репозиторию
        return adMapper.toAdsDtoOut(
                adRepository.findAllByUserId(user.getId())  //эта часть временная
                        .stream().map(adMapper::toAdDtoOut).collect(Collectors.toList()));
    }
    public AdsDtoOut getAllAds() {
        return adMapper.toAdsDtoOut(
                adRepository.findAll()
                        .stream().map(adMapper::toAdDtoOut).collect(Collectors.toList()));
    }

    @Transactional
    public byte[] getImage(int id) {
        return adRepository.findById(id).orElseThrow(excSuppl(id)).getImage();
    }

    public AdExtendedDtoOut getAdById(int id) {
        return adMapper.toAdExtendedDtoOut(adRepository.findById(id).orElseThrow(excSuppl(id)));
    }

    public AdDtoOut createAd(AdDtoIn adDtoIn, MultipartFile image, Principal principal) {
        Ad ad = adMapper.toEntity(adDtoIn, principal);
        try {
            ad.setImage(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return adMapper.toAdDtoOut(adRepository.save(ad));
    }

    public AdDtoOut updateAd(int id, AdDtoIn adDtoIn) {
        Ad ad = adRepository.findById(id).orElseThrow(excSuppl(id));
        ad.setTitle(adDtoIn.getTitle());
        ad.setPrice(adDtoIn.getPrice());
        ad.setDescription(adDtoIn.getDescription());
        return adMapper.toAdDtoOut(adRepository.save(ad));
    }

    public void deleteAd(int id) {
        adRepository.deleteById(id);
    }
}
