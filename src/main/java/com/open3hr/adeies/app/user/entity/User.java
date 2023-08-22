package com.open3hr.adeies.app.user.entity;

import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.enums.Role;
import com.open3hr.adeies.app.user.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements  UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;

    @Column (name = "username")
    private String username;

    @Column (name = "password")
    private String password;

    @Column (name = "enabled")
    private boolean isEnable;

    @Column (name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column (name = "is_supervisor")
    private boolean isSupervisor;

    @Column (name = "is_pass_temp")
    private boolean isPassTemp;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = {
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(UserDTO userDTO, Employee employee){
        this.id = userDTO.getId();
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.isEnable = userDTO.isEnable();
        this.role = userDTO.getRole();
        this.employee = employee;
        this.isSupervisor = userDTO.isSupervisor();
        this.isPassTemp = userDTO.isPassTemp();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}