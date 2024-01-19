package com.enotes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.enotes.entity.User;
@Service
public interface UserRepo extends JpaRepository<User,Integer> {
    public User findByEmail(String email);

    public User findByverificationCode(String code);
}
