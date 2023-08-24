package com.open3hr.adeies.app;

import com.open3hr.adeies.app.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryH2 extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);

}

