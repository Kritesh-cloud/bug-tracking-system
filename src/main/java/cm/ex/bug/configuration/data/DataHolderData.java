package cm.ex.bug.configuration.data;

import cm.ex.bug.entity.Authority;
import cm.ex.bug.entity.DataHolder;
import cm.ex.bug.entity.Team;
import cm.ex.bug.entity.User;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DataHolderData {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Authority> authorityList = new ArrayList<>();

    public List<DataHolder> dataHolderStatus = new ArrayList<>();
    public List<DataHolder> dataHolderResolution = new ArrayList<>();
    public List<DataHolder> dataHolderPriority = new ArrayList<>();
    public List<DataHolder> dataHolderSeverity = new ArrayList<>();
    public List<DataHolder> dataHolderRole = new ArrayList<>();

    public User admin;
    public User moderator;
    public List<User> userList = new ArrayList<>();

    public List<Team> teamList = new ArrayList<>();


    public DataHolderData() {

        authorityList.add(new Authority("admin", 1, "admin"));
        authorityList.add(new Authority("moderator", 1, "admin"));
        authorityList.add(new Authority("user", 1, "moderator"));

        dataHolderStatus.add(new DataHolder("", "test", true));

        dataHolderStatus.add(new DataHolder("new", "status", true));
        dataHolderStatus.add(new DataHolder("assigned", "status", true));
        dataHolderStatus.add(new DataHolder("in progress", "status", true));
        dataHolderStatus.add(new DataHolder("reopened", "status", true));
        dataHolderStatus.add(new DataHolder("verified", "status", true));
        dataHolderStatus.add(new DataHolder("closed", "status", true));
        dataHolderStatus.add(new DataHolder("reopened", "status", true));
        dataHolderStatus.add(new DataHolder("deferred", "status", true));
        dataHolderStatus.add(new DataHolder("rejected", "status", true));

        dataHolderPriority.add(new DataHolder("critical", "priority", true));
        dataHolderPriority.add(new DataHolder("high", "priority", true));
        dataHolderPriority.add(new DataHolder("medium", "priority", true));
        dataHolderPriority.add(new DataHolder("low", "priority", true));

        dataHolderSeverity.add(new DataHolder("blocker", "severity", true));
        dataHolderSeverity.add(new DataHolder("critical", "severity", true));
        dataHolderSeverity.add(new DataHolder("major", "severity", true));
        dataHolderSeverity.add(new DataHolder("minor", "severity", true));
        dataHolderSeverity.add(new DataHolder("trivial", "severity", true));

        dataHolderRole.add(new DataHolder("lead tester", "role", true));
        dataHolderRole.add(new DataHolder("manual tester", "role", true));
        dataHolderRole.add(new DataHolder("automation tester", "role", true));
        dataHolderRole.add(new DataHolder("end user tester", "role", true));


        admin = new User("Swastika Thapa", "swastika@gmail.com", "password", "");
        moderator = new User("System Thapa", "system@gmail.com", "password", "");

        userList.add(new User("User One", "user1@gmail.com", "password", ""));
        userList.add(new User("User Two", "user2@gmail.com", "password", ""));
        userList.add(new User("User Three", "user3@gmail.com", "password", ""));
        userList.add(new User("User Four", "user4@gmail.com", "password", ""));
        userList.add(new User("User Five", "user5@gmail.com", "password", ""));
        userList.add(new User("User Six", "user6@gmail.com", "password", ""));
        userList.add(new User("User Seven", "user7@gmail.com", "password", ""));
        userList.add(new User("User Eight", "user8@gmail.com", "password", ""));
        userList.add(new User("User Nine", "user9@gmail.com", "password", ""));
        userList.add(new User("User Ten", "user10@gmail.com", "password", ""));


        teamList.add(new Team("Apple Team","A for Apple",new User("user1@gmail.com")));
        teamList.add(new Team("Ball Team","B for Ball",new User("user1@gmail.com")));
        teamList.add(new Team("Cat Team","C for Cat",new User("user1@gmail.com")));
        teamList.add(new Team("Dog Team","D for Dog",new User("user1@gmail.com")));
        teamList.add(new Team("Elephant Team","E for Elephant",new User("user1@gmail.com")));

        teamList.add(new Team("Fox Team","F for Fox",new User("user2@gmail.com")));
        teamList.add(new Team("Goat Team","G for Goat",new User("user2@gmail.com")));
        teamList.add(new Team("Hen Team","H for Hen",new User("user2@gmail.com")));
        teamList.add(new Team("Ice Team","I for Ice",new User("user2@gmail.com")));
        teamList.add(new Team("Joker Team","J for Joker",new User("user2@gmail.com")));
    }


}
