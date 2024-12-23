package cm.ex.bug.service;

import cm.ex.bug.entity.Comment;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.service.interfaces.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public BasicResponse createComment(String reportId, String comment) {
        return null;
    }

    @Override
    public List<Comment> listAllCommentByReport(String reportId) {
        return List.of();
    }

    @Override
    public BasicResponse updateComment(String commentId, String comment) {
        return null;
    }

    @Override
    public BasicResponse removeComment(String commentIds) {
        return null;
    }
}
