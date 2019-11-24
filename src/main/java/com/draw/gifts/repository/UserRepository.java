package com.draw.gifts.repository;

import com.draw.gifts.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByExcludedFromDrawingIsFalseAndDrawing_Id(Long id);

}
