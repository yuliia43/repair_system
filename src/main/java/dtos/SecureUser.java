package dtos;

import enums.Role;
import factories.RolesFactory;
import lombok.*;

/**
 * @author Yuliia Shcherbakova ON 06.12.2019
 * @project repair_system
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecureUser {
    private int userId;
    private String firstName;
    private String lastName;
    private Role role;
    private String email;

    public String getRole() {
        return RolesFactory.getStringValue(role);
    }
}
