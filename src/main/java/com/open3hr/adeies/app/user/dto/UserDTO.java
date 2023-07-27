package com.open3hr.adeies.app.user.dto;

import com.open3hr.adeies.app.enums.Role;
import com.open3hr.adeies.app.user.entity.User;
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
}
