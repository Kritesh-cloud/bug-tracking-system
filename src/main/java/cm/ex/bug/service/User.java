package cm.ex.bug.service;

import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.service.interfaces.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class User implements UserService {

    @Override
    public BasicResponse signUp(cm.ex.bug.entity.User user, MultipartFile profileImage) throws IOException {
        return null;
    }

    @Override
    public BasicResponse logIn(cm.ex.bug.entity.User user) {
        return null;
    }

    @Override
    public cm.ex.bug.entity.User userInfo() {
        return null;
    }

    @Override
    public List<cm.ex.bug.entity.User> userList() {
        return List.of();
    }

    @Override
    public List<cm.ex.bug.entity.User> listUserByAuthority(String authority) {
        return List.of();
    }

    @Override
    public List<cm.ex.bug.entity.User> listUserByTeam(String teamId) {
        return List.of();
    }

    @Override
    public List<cm.ex.bug.entity.User> listUserByTeamInvites(String teamId) {
        return List.of();
    }

    @Override
    public BasicResponse assignAuthority(String authority, String userId) {
        return null;
    }

    @Override
    public BasicResponse removeAuthority(String authority, String userId) {
        return null;
    }

    @Override
    public BasicResponse updateUser(cm.ex.bug.entity.User user, MultipartFile profileImage) {
        return null;
    }

    @Override
    public BasicResponse deleteUser() {
        return null;
    }

    @Override
    public BasicResponse acceptTeamInvitation(String teamId) {
        return null;
    }

    @Override
    public BasicResponse declineTeamInvitation(String teamId) {
        return null;
    }
}
