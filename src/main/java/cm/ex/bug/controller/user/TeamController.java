package cm.ex.bug.controller.user;

import cm.ex.bug.entity.Team;
import cm.ex.bug.request.CreateTeamRequest;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.response.TeamResponse;
import cm.ex.bug.service.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RequestMapping("/user/team")
@RestController
public class TeamController {

    @Autowired
    private TeamServiceImpl teamService;

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        BasicResponse response = BasicResponse.builder().status(true).result(true).code(200).message("Authentication Controller Test").build();
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @GetMapping("/getTest")
    public ResponseEntity<BasicResponse> getTest() {
        BasicResponse response = BasicResponse.builder().status(true).result(true).code(200).message("Authentication Controller Test").build();
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }


    @PostMapping("/create-team")
    public ResponseEntity<BasicResponse> createTeam(@RequestBody CreateTeamRequest teamRequest) {
        BasicResponse response = teamService.createTeam(teamRequest);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @GetMapping("/list-team")
    public ResponseEntity<List<TeamResponse>> listTeam() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(teamService.listTeamByUser());
    }

    @GetMapping("/get-team-by-id/{teamId}")
    public ResponseEntity<TeamResponse> getTeamById(@PathVariable String teamId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(teamService.getTeamDetailById(teamId));
    }

    @PostMapping("/delete-team/{teamId}")
    public ResponseEntity<BasicResponse> deleteTeam(@PathVariable String teamId) throws AccessDeniedException {
        BasicResponse response = teamService.deleteTeam(teamId);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }
}


/*

create team
list team
update team
delete team

assign team role

*/