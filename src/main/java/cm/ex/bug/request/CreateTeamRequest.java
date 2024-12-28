package cm.ex.bug.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamRequest {

    private String id;

    private String name;

    private String description;

    private boolean updateTeam;

}
