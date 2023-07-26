package com.open3hr.adeies.entities;

import com.open3hr.adeies.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;

    @Column (name = "username")
    private String username;

    @Column (name = "password")
    private String password;

    @Column (name = "is_enabled")
    private Boolean isEnabled;

    @Column (name = "employee_id")
    private Integer employeeId;

    @Column(name = "is_supervisor")
    private Boolean isSupervisor;

    @Column (name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(UserDTO userDTO){
        this.id = userDTO.getId();
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.isEnabled = userDTO.getIsEnabled();
        this.employeeId = userDTO.getEmployeeId();
        this.isSupervisor = userDTO.getIsSupervisor();
        this.role = userDTO.getRole();
    }

}