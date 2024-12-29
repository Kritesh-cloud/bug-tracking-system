package cm.ex.bug.controller.user;

import cm.ex.bug.request.CommentRequest;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.service.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user/comment")
@RestController
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

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


    @PostMapping("/create-comment")
    public ResponseEntity<BasicResponse> createComment(@RequestBody CommentRequest comment) {
        BasicResponse response = commentService.createComment(comment);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @GetMapping("/list-comment/{reportId}")
    public ResponseEntity<BasicResponse> listComment(@PathVariable String reportId) {
        BasicResponse response = BasicResponse.builder().status(true).result(true).code(200).message("Authentication Controller Test").build();
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @GetMapping("/update-comment")
    public ResponseEntity<BasicResponse> updateComment(@RequestBody CommentRequest comment) {
        BasicResponse response = BasicResponse.builder().status(true).result(true).code(200).message("Authentication Controller Test").build();
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @PostMapping("/delete-comment/{commentId}")
    public ResponseEntity<BasicResponse> deleteComment(@PathVariable String commentId) {
        BasicResponse response = BasicResponse.builder().status(true).result(true).code(200).message("Authentication Controller Test").build();
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }
}

/*

create comment
list commnet
update commnet
delete comment

*/
