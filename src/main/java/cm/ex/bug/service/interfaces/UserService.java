package cm.ex.bug.service.interfaces;

import cm.ex.bug.entity.User;
import cm.ex.bug.response.BasicResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    public BasicResponse signUp(User user, MultipartFile profileImage) throws IOException;

    public BasicResponse logIn(User user);

    public User userInfo();

    public List<User> userList();

    public List<User> listUserByAuthority(String authority);

    public List<User> listUserByTeam(String teamId);

    public List<User> listUserByTeamInvites(String teamId);

    public BasicResponse assignAuthority(String authority, String userId);

    public BasicResponse removeAuthority(String authority, String userId);

    public BasicResponse updateUser(User user, MultipartFile profileImage);

    public BasicResponse deleteUser();

    public BasicResponse acceptTeamInvitation(String teamId);

    public BasicResponse declineTeamInvitation(String teamId);
}
