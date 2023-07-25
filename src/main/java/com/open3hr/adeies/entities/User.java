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
    private Integer id;

    @Column (name = "username")
    private String username;

    @Column (name = "password")
    private String password;

    @Column (name = "is_enabled")
    private Boolean isEnabled;

    @Column (name = "employee_id")
    private Integer employeeId;

    @Column (name = "role")
    Role role;

}