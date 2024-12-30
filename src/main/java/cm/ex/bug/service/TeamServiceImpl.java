package cm.ex.bug.service;

import cm.ex.bug.entity.DataHolder;
import cm.ex.bug.entity.Team;
import cm.ex.bug.entity.TeamRole;
import cm.ex.bug.entity.User;
import cm.ex.bug.repository.DataHolderRepository;
import cm.ex.bug.repository.TeamRepository;
import cm.ex.bug.repository.TeamRoleRepository;
import cm.ex.bug.repository.UserRepository;
import cm.ex.bug.request.CreateTeamRequest;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.response.TeamResponse;
import cm.ex.bug.security.authentication.UserAuth;
import cm.ex.bug.service.interfaces.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TeamRoleRepository teamRoleRepository;

    @Autowired
    private DataHolderRepository dataHolderRepository;

    @Override
    public BasicResponse createTeam(CreateTeamRequest team) {
        User leader = getLoggedInUser();
        Team teamData = modelMapper.map(team, Team.class);
        teamData.setId(null);
        teamData.setLeader(leader);
        teamRepository.save(teamData);
        return BasicResponse.builder().status(true).result(true).code(200).message("New team created successfully").build();
    }

    @Override
    public List<TeamResponse> listTeamByUser() {
        User user = getLoggedInUser();
        List<Team> teamList = teamRepository.findByLeaderOrderByUpdatedAtAsc(user);
        System.out.println("teamList count " + teamList.size());
        List<TeamResponse> teamResponseList = teamList.stream().map(
                team -> {
                    TeamResponse teamResponse = modelMapper.map(team, TeamResponse.class);
                    teamResponse.setLeader(userRemovePassword(teamResponse.getLeader()));
                    teamResponse.setTeamMembers(userListRemovePassword(teamResponse.getTeamMembers()));
                    teamResponse.setTeamInvitations(userListRemovePassword(teamResponse.getTeamInvitations()));
                    return teamResponse;
                }
        ).toList();
        return teamResponseList.isEmpty() ? List.of() : teamResponseList;
    }

    @Override
    public List<TeamResponse> listTeamInvitation(String userId) {
        User user = getLoggedInUser();
        List<Team> teamList = teamRepository.findTeamsByInvitationUser(user);
        List<TeamResponse> teamResponseList = teamList.stream().map(
                team -> {
                    TeamResponse teamResponse = modelMapper.map(team, TeamResponse.class);
                    teamResponse.setLeader(userRemovePassword(teamResponse.getLeader()));
                    teamResponse.setTeamMembers(userListRemovePassword(teamResponse.getTeamMembers()));
                    teamResponse.setTeamInvitations(userListRemovePassword(teamResponse.getTeamInvitations()));
                    return teamResponse;
                }
        ).toList();
        return teamResponseList.isEmpty() ? List.of() : teamResponseList;
    }

    @Override
    public Team getTeamById(String teamId) {
        Optional<Team> team = teamRepository.findById(UUID.fromString(teamId));
        if (team.isEmpty()) throw new NoSuchElementException("Team not found");
        return team.get();
    }

    @Override
    public TeamResponse getTeamDetailById(String teamId) {
        Team team = getTeamById(teamId);
        TeamResponse teamResponse = modelMapper.map(team, TeamResponse.class);
        teamResponse.setLeader(userRemovePassword(teamResponse.getLeader()));
        teamResponse.setTeamMembers(userListRemovePassword(teamResponse.getTeamMembers()));
        teamResponse.setTeamInvitations(userListRemovePassword(teamResponse.getTeamInvitations()));
        return teamResponse;
    }

    @Override
    public List<TeamRole> listTeamRoleByTeam(String teamId) {
        Team team = getTeamById(teamId);
        List<TeamRole> teamRoleList = teamRoleRepository.findByTeam(team);
        return teamRoleList.isEmpty() ? List.of() : teamRoleList;
    }

    @Override
    public BasicResponse updateTeam(CreateTeamRequest team) {
        Optional<Team> teamData = teamRepository.findById(UUID.fromString(team.getId()));
        if (teamData.isEmpty()) throw new NoSuchElementException("Team not found");
        teamData.get().setId(UUID.fromString(team.getId()));
        teamData.get().setName(team.getName());
        teamData.get().setDescription(team.getDescription());
        teamRepository.save(teamData.get());
        return BasicResponse.builder().status(true).result(true).code(200).message("Team updated successfully").build();
    }

    @Override
    public BasicResponse inviteToTeam(String userId, String teamId) {
        User user = getUserById(userId);
        Team team = getTeamById(teamId);
        Set<User> invitedUser = team.getTeamInvitations();
        invitedUser.add(user);
        team.setTeamInvitations(invitedUser);
        return BasicResponse.builder().status(true).result(true).code(200).message("User invited to the team successfully").build();
    }

    @Override
    public BasicResponse removeFromTeam(String userId, String teamId) {
        User user = getUserById(userId);
        Team team = getTeamById(teamId);
        Set<User> teamMembers = team.getTeamMembers();
        teamMembers.remove(user);
        team.setTeamInvitations(Set.of());
        teamRepository.save(team);
        team.setTeamInvitations(teamMembers);
        teamRepository.save(team);
        return BasicResponse.builder().status(true).result(true).code(200).message("User removed from the team successfully").build();
    }

    @Override
    public BasicResponse acceptTeamInvitation(String teamId) {
        User loggedInUser = getLoggedInUser();
        Team team = getTeamById(teamId);
        Set<User> teamInvitations = team.getTeamInvitations();
        teamInvitations.remove(loggedInUser);
        Set<User> teamMembers = team.getTeamMembers();
        teamMembers.add(loggedInUser);
        teamRepository.save(team);
        return BasicResponse.builder().status(true).result(false).code(200).message("User accepted the team invitation successfully").build();
    }

    @Override
    public BasicResponse declineTeamInvitation(String teamId) {
        User loggedInUser = getLoggedInUser();
        Team team = getTeamById(teamId);
        Set<User> teamInvitations = team.getTeamInvitations();
        teamInvitations.remove(loggedInUser);
        teamRepository.save(team);
        return BasicResponse.builder().status(true).result(true).code(200).message("User declined the team invitation successfully").build();
    }

    @Override
    public BasicResponse assignTeamRole(String userId, String teamId, String roleName) throws AccessDeniedException {
        User leader = getLoggedInUser();
        Team team = getTeamById(teamId);
        User user = getUserById(userId);
        DataHolder role = getRole(roleName);

        if (!team.getLeader().equals(leader)) throw new AccessDeniedException("Access denied");
        teamRoleRepository.save(new TeamRole(user, team, role));

        return BasicResponse.builder().status(true).result(true).code(200).message("Team member has been successfully assigned a role").build();
    }

    @Override
    public BasicResponse leaveTeam(String teamId) {
        User user = getLoggedInUser();
        Team team = getTeamById(teamId);
        Set<User> teamMembers = team.getTeamMembers();
        teamMembers.remove(user);
        team.setTeamInvitations(Set.of());
        teamRepository.save(team);
        team.setTeamInvitations(teamMembers);
        teamRepository.save(team);
        return BasicResponse.builder().status(true).result(false).code(200).message("User left the team successfully").build();
    }

    @Override
    public BasicResponse deleteTeam(String teamId) throws AccessDeniedException {
        User user = getLoggedInUser();
        Team team = getTeamById(teamId);

        System.out.println("!team.getLeader().getId().equals(user.getId()): "+!team.getLeader().getId().equals(user.getId()));
        if (!team.getLeader().getId().equals(user.getId())) throw new AccessDeniedException("Unauthorized to delete team");
        teamRepository.delete(team);
        return BasicResponse.builder().status(true).result(false).code(200).message("Team deleted successfully").build();

    }

    private User userRemovePassword(User user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profileUrl(user.getProfileUrl())
                .authoritySet(user.getAuthoritySet())
                .build();
    }

    private Set<User> userListRemovePassword(Set<User> userSet) {
        return userSet.stream().map(this::userRemovePassword).collect(Collectors.toSet());
    }

    private User getLoggedInUser() {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        return userAuth.getUser();
    }

    public User getUserById(String userId) {
        Optional<User> user = userRepository.findById(UUID.fromString(userId));
        if (user.isEmpty()) throw new NoSuchElementException("User not found");
        return user.get();
    }

    public DataHolder getRole(String roleName) {
        Optional<DataHolder> role = dataHolderRepository.findByNameAndType(roleName, "role");
        if (role.isEmpty()) throw new NoSuchElementException("Team role not found");
        return role.get();
    }

}
