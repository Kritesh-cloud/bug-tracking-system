package cm.ex.bug.service;

import cm.ex.bug.entity.*;
import cm.ex.bug.repository.*;
import cm.ex.bug.request.CreateUserRequest;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.security.authentication.UserAuth;
import cm.ex.bug.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private DataHolderRepository dataHolderRepository;

    @Autowired
    private TeamRoleRepository teamRoleRepository;

    @Override
    public BasicResponse signUp(CreateUserRequest user, MultipartFile profileImage) throws IOException {
        User userData = modelMapper.map(user, User.class);
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        userData.setAuthoritySet(Set.of(authorityRepository.findByAuthority("user").get()));

        Optional<User> checkUserEmail = userRepository.findByEmail(user.getEmail());
        if(checkUserEmail.isPresent()) throw new AccessDeniedException("This email is already in use");

        if (profileImage != null) {
            // && !Objects.requireNonNull(profileImage.getOriginalFilename()).isEmpty()
            File profile = fileService.addFile(profileImage);
            userData.setProfileUrl("http://localhost:8080/image/" + profile.getId());
        }

        userRepository.save(userData);
        return BasicResponse.builder().status(true).result(true).code(200).message("Account created successfully").build();
    }

    @Override
    public BasicResponse logIn(CreateUserRequest user) {
        Optional<User> userEmail = userRepository.findByEmail(user.getEmail());
        if (userEmail.isEmpty() || !passwordEncoder.matches(user.getPassword(), userEmail.get().getPassword()))
            return BasicResponse.builder().status(false).code(401).message("Email or password doesn't match").build();

        UserAuth userAuth = new UserAuth(userEmail.get().getId().toString(), true, user.getEmail(), user.getPassword(), null, userEmail.get().getName(), convertToGrantedAuthorities(userEmail.get().
                getAuthoritySet()), null);
        String jwtToken = jwtService.generateToken(userAuth);

        return BasicResponse.builder().status(true).code(200).message("Successfully logged in").token(jwtToken).build();
    }

    @Override
    public User userInfo() {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        return userRemovePassword(userAuth.getUser());
    }

    @Override
    public User getUserById(String userId) {
        Optional<User> user = userRepository.findById(UUID.fromString(userId));
        if (user.isEmpty()) throw new NoSuchElementException("User not found");
        return userRemovePassword(user.get());
    }

    @Override
    public List<User> userList() {
        List<User> userList = userListRemovePassword(userRepository.findAll());
        return userList.isEmpty() ? List.of() : userList;
    }

    @Override
    public List<User> listUserByAuthority(String authority) {
        List<User> userList = userListRemovePassword(userRepository.listByUserAuthority(authority));
        return userList.isEmpty() ? List.of() : userList;
    }

    @Override
    public List<User> listUserByTeam(String teamId) {
        Team team = getTeamById(teamId);
        List<User> userList = userListRemovePassword(userRepository.listUsersByTeam(team));
        return userList.isEmpty() ? List.of() : userList;
    }

    @Override
    public List<User> listUserByTeamInvites(String teamId) {
        Team team = getTeamById(teamId);
        List<User> userList = userListRemovePassword(userRepository.findUsersByTeamInvitations(team));
        return userList.isEmpty() ? List.of() : userList;
    }

    @Override
    public BasicResponse assignTeamRole(String userId, String teamId, String roleName) throws AccessDeniedException {
        User leader = getLoggedInUser();
        Team team = getTeamById(teamId);
        User user = getUserById(userId);
        DataHolder role = getRole(roleName);

        if (!team.getLeader().equals(leader)) throw new AccessDeniedException("Access denied");
        teamRoleRepository.save(new TeamRole(user, team, role));

        return BasicResponse.builder().status(true).result(true).code(200).message("Team member has been successfully assigned a role").build();
    }

    @Override
    public BasicResponse assignAuthority(String authority, String userId) {
        return null;
    }

    @Override
    public BasicResponse removeAuthority(String authority, String userId) {
        return null;
    }

    @Override
    public BasicResponse updateUser(CreateUserRequest user, MultipartFile profileImage) throws IOException {
        Optional<User> updateUser = userRepository.findById(UUID.fromString(user.getId()));
        if (updateUser.isEmpty())
            return BasicResponse.builder().status(false).code(409).message("Account not found").build();

        User userData = modelMapper.map(user, User.class);
        if (user.isProfileChanged()) {
            File profile = fileService.addFile(profileImage);
            userData.setProfileUrl("http://localhost:8080/image/" + profile.getId());
        }
        userData.setId(updateUser.get().getId());
        userData.setPassword(passwordEncoder.encode(user.getPassword()));
        userData.setAuthoritySet(updateUser.get().getAuthoritySet());

        userRepository.save(userData);
        return BasicResponse.builder().status(true).result(true).code(200).message("Account updated successfully").build();
    }

    @Override
    public BasicResponse deleteUser() {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        userRepository.delete(userAuth.getUser());
        return BasicResponse.builder().status(true).code(200).message("User account deleted successfully").build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User.withUsername(user.get().getEmail())
                .password(user.get().getPassword())
                .username(user.get().getEmail())
                .authorities(convertToGrantedAuthorities(user.get().getAuthoritySet()))
                .build();
    }

    private List<GrantedAuthority> convertToGrantedAuthorities(Set<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }

    public static List<String> convertToStringListAuthorities(List<GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority) // Extract the authority name
                .collect(Collectors.toList());
    }

    private static String extractImageId(String imageUrl) {
        String uuidRegex = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";
        Pattern pattern = Pattern.compile(uuidRegex);
        Matcher matcher = pattern.matcher(imageUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    private Authority checkForAuthorityUpdate(String authority) throws AccessDeniedException {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        Optional<Authority> newAuthority = authorityRepository.findByAuthority(authority);
        if (newAuthority.isEmpty()) throw new IllegalArgumentException("Authority cannot be blank.");

        Set<Authority> higherAuthoritySet = userAuth.getUser().getAuthoritySet();
        Optional<Authority> checkAuthority = authorityRepository.findByAuthority(newAuthority.get().getAuthorityAccept());
        if (checkAuthority.isEmpty()) throw new IllegalArgumentException("Authority not found.");

        if (!higherAuthoritySet.contains(checkAuthority.get()))
            throw new AccessDeniedException("Access denied for authority update");
        return newAuthority.get();
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

    public List<User> userListRemovePassword(List<User> userList) {
        return userList.stream().map(this::userRemovePassword).toList();
    }

    public Team getTeamById(String teamId) {
        Optional<Team> team = teamRepository.findById(UUID.fromString(teamId));
        if (team.isEmpty()) throw new NoSuchElementException("Team not found");
        return team.get();
    }

    public DataHolder getRole(String roleName) {
        Optional<DataHolder> role = dataHolderRepository.findByNameAndType(roleName, "role");
        if (role.isEmpty()) throw new NoSuchElementException("Team role not found");
        return role.get();
    }

    private User getLoggedInUser() {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        return userAuth.getUser();
    }
}
