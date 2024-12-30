package cm.ex.bug.controller;

import cm.ex.bug.entity.User;
import cm.ex.bug.request.CreateUserRequest;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("")
@RestController
public class AuthenticationController {


    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        BasicResponse response = BasicResponse.builder().status(true).result(true).code(200).message("Authentication Controller Test").build();
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }



    @GetMapping("/list-user")
    public ResponseEntity<List<User>> listUser() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(userService.userList());
    }

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(userService.getUserById(userId));
    }

    @PostMapping("/signUpImage")
    public ResponseEntity<BasicResponse> signUp(@RequestBody CreateUserRequest user, MultipartFile profileImage) throws IOException {
        BasicResponse response = userService.signUp(user, profileImage);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @PostMapping("/signUp")
    public ResponseEntity<BasicResponse> signUp(@RequestBody CreateUserRequest user) throws IOException {
        BasicResponse response = userService.signUp(user, null);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @PostMapping("/signIn")
    public ResponseEntity<BasicResponse> signIn(@RequestBody CreateUserRequest user) {
        BasicResponse response = userService.logIn(user);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }
}
