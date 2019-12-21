package models;

import enums.Status;
import factories.StatusFactory;
import lombok.*;

import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 06.12.2019
 * @project repair_system
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Application {
    private Integer applicationId;
    private Integer userId;
    private String repairDetails;
    private Status status;
    private Integer managerId;
    private Integer price;
    private String managerDetails;

    private User manager;
    private Feedback feedback;


    public String getStatus() {
        return StatusFactory.getStringValue(status);
    }
}
