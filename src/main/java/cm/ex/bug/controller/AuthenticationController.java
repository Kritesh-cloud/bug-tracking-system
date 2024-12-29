package cm.ex.bug.controller;

import cm.ex.bug.request.CreateUserRequest;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
