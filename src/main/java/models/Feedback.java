package models;

import lombok.*;

/**
 * @author Yuliia Shcherbakova ON 08.12.2019
 * @project repair_system
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {
    private Integer feedbackId;
    private Integer applicationId;
    private Integer masterId;
    private String feedback;

    private User master;
}
