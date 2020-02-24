package com.draw.gifts.repository;

import com.draw.gifts.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByIfHasDrawnIsFalseAndDrawing_Id(Long id);
    List<User> findAllByIfWasDrawnIsFalseAndExcludedTemporarilyIsFalseAndDrawing_Id(Long id);
    User findUserByExcludedTemporarilyIsTrueAndDrawing_Id(Long id);

}
