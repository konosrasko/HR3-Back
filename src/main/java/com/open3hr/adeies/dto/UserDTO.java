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

        private Integer id;
        private String username;
        private String password;
        private Boolean isEnabled;
        private Integer employeeId;
        private Boolean isSupervisor;
        private Role role;

        public UserDTO(User user) {
                this.username = user.getUsername();
                this.password = user.getPassword();
                this.isEnabled = user.getIsEnabled();
                this.role = user.getRole();
        }

//        public User toUser(){
//                return new User(
//                        this.id,
//                        this.username,
//                        this.password,
//                        this.isEnabled,
//                        this.employeeId,
//                        this.isSupervisor,
//                        this.role
//                );
//        }
}
