package cm.ex.bug.service.interfaces;

import cm.ex.bug.entity.User;
import cm.ex.bug.request.CreateUserRequest;
import cm.ex.bug.response.BasicResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

public interface UserService {

    public BasicResponse signUp(CreateUserRequest user, MultipartFile profileImage) throws IOException;

    public BasicResponse logIn(CreateUserRequest user);

    public User userInfo();

    public User getUserById(String userId);

    public List<User> userList();

    public List<User> listUserByAuthority(String authority);

    public List<User> listUserByTeam(String teamId);

    public List<User> listUserByTeamInvites(String teamId);

    public BasicResponse assignTeamRole(String userId, String teamId, String roleName) throws AccessDeniedException;

//    public BasicResponse removeTeamRole(String userId, String teamId, String role);

    public BasicResponse assignAuthority(String authority, String userId);

    public BasicResponse removeAuthority(String authority, String userId);

    public BasicResponse updateUser(CreateUserRequest user, MultipartFile profileImage) throws IOException;

    public BasicResponse deleteUser();
}