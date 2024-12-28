package cm.ex.bug.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String id;

    private String name;

    private String email;

    private String profileUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<String> authorityList = new ArrayList<>();

}
