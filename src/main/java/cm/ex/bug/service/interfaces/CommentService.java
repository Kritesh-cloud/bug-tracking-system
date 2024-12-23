package cm.ex.bug.service.interfaces;

import cm.ex.bug.entity.Comment;
import cm.ex.bug.response.BasicResponse;

import java.util.List;

public interface CommentService {

    public BasicResponse createComment(String reportId, String comment);

    public List<Comment> listAllCommentByReport(String reportId);

    public BasicResponse updateComment(String commentId, String comment);

    public BasicResponse removeComment(String commentIds);
}
