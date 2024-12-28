package cm.ex.bug.service;

import cm.ex.bug.entity.TeamRole;
import cm.ex.bug.repository.TeamRoleRepository;
import cm.ex.bug.repository.UserRepository;
import cm.ex.bug.service.interfaces.TeamRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamRoleServiceImpl implements TeamRoleService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRoleRepository teamRoleRepository;

    @Override
    public TeamRole addTeamRole(String memberId, String teamId, String role) {
        return null;
    }

    @Override
    public List<TeamRole> listTeamRoleByTeam(String teamId) {
        return List.of();
    }

    @Override
    public TeamRole removeTeamRole(String memberId, String teamId, String role) {
        return null;
    }
}
