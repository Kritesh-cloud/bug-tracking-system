package cm.ex.bug.service;

import cm.ex.bug.entity.Authority;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.service.interfaces.AuthorityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Override
    public Authority addAuthority(String authority) {
        return null;
    }

    @Override
    public List<Authority> listAuthority() {
        return List.of();
    }

    @Override
    public BasicResponse removeAuthority(String authority) {
        return null;
    }
}
