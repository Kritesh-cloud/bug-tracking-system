package cm.ex.bug.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "file")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String file;

    private String extension;

    private LocalDateTime createdAt;

    public File(String name, String file, String extension) {
        this.name = name;
        this.file = file;
        this.extension = extension;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
