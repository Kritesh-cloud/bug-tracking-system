package cm.ex.bug.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "report")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reporter_id", referencedColumnName = "id")
    private User reporter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "report__assignees",
            joinColumns = @JoinColumn(name = "report_id", updatable = true),
            inverseJoinColumns = @JoinColumn(name = "user_id", updatable = true))
    private Set<User> assignees = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private DataHolder status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resolution_id", referencedColumnName = "id")
    private DataHolder resolution;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "priority_id", referencedColumnName = "id")
    private DataHolder priority;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "severity_id", referencedColumnName = "id")
    private DataHolder severity;

    private String stepsToReproduce;

    private String version;

    private String environment;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "report__files",
            joinColumns = @JoinColumn(name = "report_id", updatable = true),
            inverseJoinColumns = @JoinColumn(name = "file_id", updatable = true))
    private Set<File> fileSet = new HashSet<>();

    private LocalDateTime reportedDate;

    private LocalDateTime updatedDate;

    private LocalDateTime dueDate;

    @PrePersist
    protected void onCreate() {
        reportedDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
    }

}
