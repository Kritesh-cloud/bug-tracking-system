package cm.ex.bug.response;

import cm.ex.bug.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeamResponse {

    private UUID id;

    private String name;

    private String description;

    private String iconUrl;

    private String backgroundUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private User leader;

    private Set<User> teamMembers = new HashSet<>();

    private Set<User> teamInvitations = new HashSet<>();

}
