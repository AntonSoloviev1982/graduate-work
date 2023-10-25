package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.AdComment;

import java.util.List;

public interface AdCommentRepository extends JpaRepository<AdComment, Long> {

    List<AdComment> findAllByAdId(Integer adId);

}
