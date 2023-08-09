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
public class RolesDTO {

        private Role role;
        private boolean isSupervisor;

        public RolesDTO(User user) {
                this.role = user.getRole();
                this.isSupervisor = user.isSupervisor();
        }
}
