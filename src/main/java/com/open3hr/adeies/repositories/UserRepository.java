package com.open3hr.adeies.repositories;

import com.open3hr.adeies.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
