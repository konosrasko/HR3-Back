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
        private boolean isEnable;
        private Integer employeeId;
        private Role role;
        private boolean isSupervisor;

        public UserDTO(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
                this.password = user.getPassword();
                this.isEnable = user.isEnable();
                this.role = user.getRole();
                this.employeeId = user.getEmployee().getId();
                this.isSupervisor = user.isSupervisor();
        }
}
