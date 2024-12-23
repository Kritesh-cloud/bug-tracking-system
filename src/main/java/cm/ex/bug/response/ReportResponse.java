package cm.ex.bug.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {

    private UUID id;

    private String title;

    private String description;

    private String reporterId;

    private String teamId;

    private List<String> assigneesIds = new ArrayList<>();

    private String status;

    private String resolution;

    private String priority;

    private String severity;

    private String stepsToReproduce;

    private String version;

    private String environment;

    private List<String> fileIds = new ArrayList<>();

    private LocalDateTime reportedDate;

    private LocalDateTime updatedDate;

    private LocalDateTime dueDate;

}
