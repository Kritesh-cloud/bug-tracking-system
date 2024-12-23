package cm.ex.bug.controller;

import cm.ex.bug.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("")
@RestController
public class AuthenticationController {

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        BasicResponse response = BasicResponse.builder().status(true).result(true).code(200).message("Authentication Controller Test").build();
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

//    @PostMapping("/signUp")
//    public ResponseEntity<BasicResponse> signUp(@RequestBody User user) throws IOException {
//        BasicResponse response = userService.signUp(user, null);
//        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
//    }
//
//    @PostMapping("/signIn")
//    public ResponseEntity<BasicResponse> signIn(@RequestBody User user) {
//        BasicResponse response = userService.logIn(user);
//        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
//    }
}
