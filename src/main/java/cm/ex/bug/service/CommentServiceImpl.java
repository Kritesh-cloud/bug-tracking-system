package cm.ex.bug.service;

import cm.ex.bug.entity.Comment;
import cm.ex.bug.entity.Report;
import cm.ex.bug.entity.Team;
import cm.ex.bug.entity.User;
import cm.ex.bug.repository.*;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.response.CommentResponse;
import cm.ex.bug.security.authentication.UserAuth;
import cm.ex.bug.service.interfaces.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private DataHolderRepository dataHolderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BasicResponse createComment(String reportId, String comment) {
        User commenter = getLoggedInUser();
        Report report = getReportById(reportId);
        commentRepository.save(new Comment(comment, commenter, report));
        return BasicResponse.builder().status(true).result(true).code(200).message("Comment created successfully").build();
    }

    @Override
    public List<CommentResponse> listAllCommentByReport(String reportId) {
        Report report = getReportById(reportId);
        List<Comment> commentList = commentRepository.findByReport(report);
        return commentList.isEmpty() ? List.of() : commentToResponseList(commentList);
    }

    @Override
    public BasicResponse updateComment(String commentId, String comment) {
        User commenter = getLoggedInUser();
        Optional<Comment> userComment = commentRepository.findById(UUID.fromString(commentId));
        if (userComment.isEmpty()) throw new NoSuchElementException("Comment not found");

        List<Comment> commentList = commentRepository.findByCommenter(commenter);
        if (!commentList.contains(userComment.get())) throw new AccessDeniedException("Unauthorized for modification");

        userComment.get().setContent(comment);
        commentRepository.save(userComment.get());

        return BasicResponse.builder().status(true).result(true).code(200).message("Comment updated successfully").build();
    }

    @Override
    public BasicResponse removeComment(String commentId) {
        User commenter = getLoggedInUser();
        Optional<Comment> userComment = commentRepository.findById(UUID.fromString(commentId));
        if (userComment.isEmpty()) throw new NoSuchElementException("Comment not found");

        List<Comment> commentList = commentRepository.findByCommenter(commenter);
        if (!commentList.contains(userComment.get())) throw new AccessDeniedException("Unauthorized for modification");

        commentRepository.delete(userComment.get());
        return BasicResponse.builder().status(true).result(false).code(200).message("Comment deleted successfully").build();
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

    public Team getTeamById(String teamId) {
        Optional<Team> team = teamRepository.findById(UUID.fromString(teamId));
        if (team.isEmpty()) throw new NoSuchElementException("Team not found");
        return team.get();
    }

    private Report getReportById(String reportId) {
        Optional<Report> report = reportRepository.findById(UUID.fromString(reportId));
        if (report.isEmpty()) throw new NoSuchElementException("Report not found");
        return report.get();
    }

    private List<CommentResponse> commentToResponseList(List<Comment> commentList) {
        return commentList.stream().map(comment -> {
            CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
            commentResponse.setCommenterId(String.valueOf(comment.getCommenter().getId()));
            commentResponse.setReportId(String.valueOf(comment.getReport().getId()));
            commentResponse.setUserMentionIds(comment.getUserMentions().stream()
                    .map(user -> String.valueOf(user.getId()))
                    .toList());
            return commentResponse;
        }).toList();
    }
}
