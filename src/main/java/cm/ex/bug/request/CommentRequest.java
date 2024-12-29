package cm.ex.bug.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    private UUID id;

    private String content;

    private String commenterId;

    private String reportId;

    private String[] userMentionIds;
}
