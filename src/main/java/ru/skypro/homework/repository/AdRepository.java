package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.entity.Ad;

import java.util.List;

@Repository
@Transactional  //для работы с Large Object
//иначе ошибка - Large Objects may not be used in auto-commit mode
public interface AdRepository extends JpaRepository<Ad, Integer> {

    List<Ad> findAllByUserId(Integer userId);

}
