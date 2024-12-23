package cm.ex.bug.service.interfaces;

import cm.ex.bug.entity.Team;
import cm.ex.bug.response.BasicResponse;

import java.util.List;

public interface TeamService {

    public BasicResponse createTeam(Team team);

    public List<Team> listTeamByUser();

    public List<Team> listTeamInvitation();

    public BasicResponse getTeamDetailById(String teamId);

    public BasicResponse updateTeam(Team team);

    public BasicResponse inviteToTeam(String userId);

}
