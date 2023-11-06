package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdExtendedDtoOut;

@Service("CheckUserService")
public class CheckUserService {

    private final AdService adService;

    public CheckUserService(AdService adService) {
        this.adService = adService;
    }

    public String getUsernameByAd(int id) {
        AdExtendedDtoOut adExtendedDtoOut = adService.getAdById(id);
        return adExtendedDtoOut.getEmail();
    }

    public String getUsernameByComment(int id) {

        return "username";
    }

}
