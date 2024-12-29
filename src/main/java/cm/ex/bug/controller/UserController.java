package cm.ex.bug.controller;

import cm.ex.bug.entity.User;
import cm.ex.bug.request.CreateUserRequest;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.response.UserResponse;
import cm.ex.bug.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

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




    @GetMapping("/get-me")
    public ResponseEntity<User> getMyself() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(userService.userInfo());
    }

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(userService.getUserById(userId));
    }

    @GetMapping("/list-user-by-team/{teamId}")
    public ResponseEntity<List<User>> listUserByTeam(@PathVariable String teamId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(userService.listUserByTeam(teamId));
    }

    @PostMapping("/update-user")
    public ResponseEntity<BasicResponse> updateUser(@RequestBody CreateUserRequest userRequest) throws IOException {
        BasicResponse response = userService.updateUser(userRequest,null);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @PostMapping("/delete-user")
    public ResponseEntity<BasicResponse> deleteUser() {
        BasicResponse response = userService.deleteUser();
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }
}


/*


user
team
report
comment
notification

read user
list team member
update user
delete user


*/
