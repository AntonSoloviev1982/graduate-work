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

    //К Диме. Не вижу целесообразности в создании специального метода для преобразовании AdDtoIn в Ad
    //Этот метод может потребоваться только один раз - при создании нового объявления
    //и его текст может быть частью метода сервиса createAd.
    //Обращаться к базе и вытягивать комментарии не стал - не знаю, для чего они.
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
        //Вариант когда картинка лежит вместе с объявлением
        adDtoOut.setImage("http://localhost:8080/ads/"+ad.getId()+"/image");
                           //http://localhost:8080/ads/5/image
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
        adExtendedDtoOut.setImage("http://localhost:8080/ads/"+ad.getId()+"/image");
        return adExtendedDtoOut;
    }
    public AdsDtoOut toAdsDtoOut(List<AdDtoOut> list) {
        AdsDtoOut adsDtoOut = new AdsDtoOut();
        adsDtoOut.setCount(list.size());
        adsDtoOut.setResults(list);
        return adsDtoOut;
    }
}
