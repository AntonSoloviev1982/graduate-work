package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.entity.AdComment;

import java.util.List;

public interface CommentRepository extends JpaRepository<AdComment, Integer> {

    @Transactional
    List<AdComment> findAllByAdId(Integer adId);

}
