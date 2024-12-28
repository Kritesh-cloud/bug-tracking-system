package cm.ex.bug.service.interfaces;

import cm.ex.bug.entity.TeamRole;

import java.util.List;

public interface TeamRoleService {

    public TeamRole addTeamRole(String memberId, String teamId, String role);

    public List<TeamRole> listTeamRoleByTeam(String teamId);

    public TeamRole removeTeamRole(String memberId, String teamId, String role);
}
