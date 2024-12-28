package cm.ex.bug.response;

import cm.ex.bug.entity.Report;
import cm.ex.bug.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    private UUID id;

    private String content;

    private String commenterId;

    private String reportId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<String> userMentionIds = new ArrayList<>();

}
