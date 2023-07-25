package com.open3hr.adeies.dto;

import com.open3hr.adeies.entities.Role;
import com.open3hr.adeies.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

        Integer id;
        String username;
        String password;
        Boolean isEnabled;
        Integer employeeId;
        Role role;

        public UserDTO(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
                this.password = user.getPassword();
                this.isEnabled = user.getIsEnabled();
                this.employeeId = user.getEmployeeId();
                this.role = user.getRole();
        }

        public User toUser(){
                return new User(
                        this.id,
                        this.username,
                        this.password,
                        this.isEnabled,
                        this.employeeId,
                        this.role
                );
        }
}
