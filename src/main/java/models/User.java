package models;

import lombok.*;

import java.util.Date;

/**
 * @author Yuliia Shcherbakova ON 06.12.2019
 * @project repair_system
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private String password;
    private String salt;
}
