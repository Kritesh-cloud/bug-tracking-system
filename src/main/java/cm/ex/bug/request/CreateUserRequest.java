package cm.ex.bug.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    private String id;

    private String name;

    private String email;

    private String password;

    private boolean profileChanged;

}
