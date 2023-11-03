package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdDtoIn;
import ru.skypro.homework.dto.AdDtoOut;
import ru.skypro.homework.dto.AdExtendedDtoOut;
import ru.skypro.homework.dto.AdsDtoOut;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import java.util.List;

@Component
public class AdMapper {
    //Можно было бы AdDtoIn использовать, передав в сервис, для заполнения полей при сохранении,
    //но мы превращаем AdDtoIn в Ad здесь, чтобы "отделить логику маппинга от логики сохранения"
    public Ad toEntity(AdDtoIn adDtoIn) {
        Ad ad = new Ad();
        User user = new User(); //пока не рассказали, как добыть текущего пользователя
        user.setId(1);
        ad.setUser(user);
        ad.setTitle(adDtoIn.getTitle());
        ad.setPrice(adDtoIn.getPrice());
        ad.setDescription(adDtoIn.getDescription());
        return ad;
    }
    public AdDtoOut toAdDtoOut(Ad ad) {
        AdDtoOut adDtoOut = new AdDtoOut();
        adDtoOut.setPk(ad.getId());
        adDtoOut.setAuthor(ad.getUser().getId());
        adDtoOut.setTitle(ad.getTitle());
        adDtoOut.setPrice(ad.getPrice());
        //http://localhost:8080/ads/5/image
        adDtoOut.setImage("/ads/"+ad.getId()+"/image"); //http://localhost:8080/ - не указываем

        return adDtoOut;
    }
    public AdExtendedDtoOut toAdExtendedDtoOut(Ad ad) {
        AdExtendedDtoOut adExtendedDtoOut = new AdExtendedDtoOut();
        adExtendedDtoOut.setPk(ad.getId());
        adExtendedDtoOut.setAuthorFirstName(ad.getUser().getFirstName());
        adExtendedDtoOut.setAuthorLastName(ad.getUser().getLastName());
        adExtendedDtoOut.setEmail(ad.getUser().getUsername());
        adExtendedDtoOut.setPhone(ad.getUser().getPhone());
        adExtendedDtoOut.setTitle(ad.getTitle());
        adExtendedDtoOut.setPrice(ad.getPrice());
        adExtendedDtoOut.setDescription(ad.getDescription());
        adExtendedDtoOut.setImage("/ads/"+ad.getId()+"/image");
        return adExtendedDtoOut;
    }
    public AdsDtoOut toAdsDtoOut(List<AdDtoOut> list) {
        AdsDtoOut adsDtoOut = new AdsDtoOut();
        adsDtoOut.setCount(list.size());
        adsDtoOut.setResults(list);
        return adsDtoOut;
    }
}
