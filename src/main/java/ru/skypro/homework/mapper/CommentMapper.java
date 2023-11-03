package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;

import java.time.ZoneOffset;
import java.util.List;

@Component
public class CommentMapper {

    private final UserRepository userRepository;
    private final AdRepository adRepository;

    public CommentMapper(UserRepository userRepository, AdRepository adRepository) {
        this.userRepository = userRepository;
        this.adRepository = adRepository;
    }

    //К Диме. Не вижу, где может пригодиться этот метод (toEntity)
    //Для создания и обновления комментария надо будет передать в методы сервиса
    //объект createOrUpdateComment, пришедший в контроллер,
    //а сущности Ad и User создать без обращения к базе, т.е. они будут неполноценные.
    //Ad ad = adRepository.getReferenceById(adId);

    //А кому может потребоваться созданная таким образом сущность? (с обращением к базе)
    //Да еще зачем-то писать отдельный метод для ее создания.
    public Comment toEntity(CreateOrUpdateComment createOrUpdateComment, Integer adId, Integer userId){
        Comment comment = new Comment();
        comment.setText(createOrUpdateComment.getText());
        comment.setAd(adRepository.findById(adId).get());
        comment.setUser(userRepository.findById(userId).get());
        return comment;
    }

    public CommentDtoOut toDto(Comment comment){
        CommentDtoOut commentDtoOut = new CommentDtoOut();
        User user = comment.getUser();
        commentDtoOut.setAuthor(user.getId());
        commentDtoOut.setAuthorImage(user.getImage());
        commentDtoOut.setAuthorFirstName(user.getFirstName());
        commentDtoOut.setCreatedAt(comment.getCreatedAt().toInstant(ZoneOffset.UTC).toEpochMilli());
        commentDtoOut.setPk(comment.getId());
        commentDtoOut.setText(comment.getText());
        return commentDtoOut;
    }
    public Comments toComments(List<CommentDtoOut> list) {
        Comments comments = new Comments();
        comments.setCount(list.size());
        comments.setResults(list);
        return comments;
    }
}
