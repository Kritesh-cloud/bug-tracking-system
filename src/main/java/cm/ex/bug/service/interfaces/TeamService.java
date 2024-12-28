package cm.ex.bug.service.interfaces;

import cm.ex.bug.entity.Team;
import cm.ex.bug.entity.TeamRole;
import cm.ex.bug.request.CreateTeamRequest;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.response.TeamResponse;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface TeamService {

    public BasicResponse createTeam(CreateTeamRequest team);

    public List<TeamResponse> listTeamByUser();

    public List<TeamResponse> listTeamInvitation(String userId);

    public Team getTeamById(String teamId);

    public TeamResponse getTeamDetailById(String teamId);

    public List<TeamRole> listTeamRoleByTeam(String teamId);

    public BasicResponse updateTeam(CreateTeamRequest team);

    public BasicResponse inviteToTeam(String userId, String teamId);

    public BasicResponse removeFromTeam(String userId, String teamId);

    public BasicResponse acceptTeamInvitation(String teamId);

    public BasicResponse declineTeamInvitation(String teamId);

    public BasicResponse assignTeamRole(String userId, String teamId, String roleName) throws AccessDeniedException;

    public BasicResponse leaveTeam(String teamId);

}
