package cm.ex.bug.service.interfaces;

import cm.ex.bug.entity.Comment;
import cm.ex.bug.request.CommentRequest;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.response.CommentResponse;

import java.util.List;

public interface CommentService {

    public BasicResponse createComment(CommentRequest commentRequest);

    public List<CommentResponse> listAllCommentByReport(String reportId);

    public BasicResponse updateComment(CommentRequest commentRequest);

    public BasicResponse removeComment(String commentId);
}
