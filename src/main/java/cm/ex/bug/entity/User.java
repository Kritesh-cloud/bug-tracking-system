package cm.ex.bug.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String email;

    private String password;

    private String profileUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    //old code
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user__authorities",
            joinColumns = @JoinColumn(name = "user_id", updatable = true),
            inverseJoinColumns = @JoinColumn(name = "authority_id", updatable = true))
    private Set<Authority> authoritySet = new HashSet<>();

    public User(String name, String email, String password, String profileUrl) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileUrl = profileUrl;
    }

    public User(String name, String email, String password, String profileUrl, Set<Authority> authoritySet) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileUrl = profileUrl;
        this.authoritySet = authoritySet;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public User(String email) {
        this.email = email;
    }
}