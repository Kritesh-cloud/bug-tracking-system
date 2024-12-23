package cm.ex.bug.service;

import cm.ex.bug.entity.Team;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.service.interfaces.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Override
    public BasicResponse createTeam(Team team) {
        return null;
    }

    @Override
    public List<Team> listTeamByUser() {
        return List.of();
    }

    @Override
    public List<Team> listTeamInvitation() {
        return List.of();
    }

    @Override
    public BasicResponse getTeamDetailById(String teamId) {
        return null;
    }

    @Override
    public BasicResponse updateTeam(Team team) {
        return null;
    }

    @Override
    public BasicResponse inviteToTeam(String userId) {
        return null;
    }
}
