package cm.ex.bug.service.interfaces;

import cm.ex.bug.entity.Authority;
import cm.ex.bug.response.BasicResponse;

import java.util.List;

public interface AuthorityService {

    public Authority addAuthority(String authority);

    public List<Authority> listAuthority();

    public BasicResponse removeAuthority(String authority);

}
