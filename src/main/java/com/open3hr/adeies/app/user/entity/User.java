package com.open3hr.adeies.app.user.entity;

import com.open3hr.adeies.app.enums.Role;
import com.open3hr.adeies.app.user.dto.UserDTO;
import com.open3hr.adeies.app.employee.entity.Employee;
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

    @Column(name = "is_supervisor")
    private Boolean isSupervisor;

    @Column (name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    public User(UserDTO userDTO, Employee employee){
        this.id = userDTO.getId();
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.isEnabled = userDTO.getIsEnabled();
        this.role = userDTO.getRole();
        this.employee = employee;
        this.isSupervisor = userDTO.getIsSupervisor();
    }
}