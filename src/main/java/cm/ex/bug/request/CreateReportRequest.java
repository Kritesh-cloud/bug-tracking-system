package cm.ex.bug.request;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateReportRequest {

    private UUID id;

    private String title;

    private String description;

    private String teamId;

    private String priority;

    private String severity;

    private String stepsToReproduce;

    private String version;

    private String environment;

    private String dueDate;
}
