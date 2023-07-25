package com.open3hr.adeies.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Column (name = "id")
    @Id
    Long id;

    @Column (name = "username")
    String username;

    @Column (name = "password")
    String password;

    @Column (name = "is_enabled")
    Boolean isEnabled;

    @Column (name = "employee_id")
    Long employeeId;

    @Column (name = "role")
    Role role;

}

