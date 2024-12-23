package cm.ex.bug.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "data_holder")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DataHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String type;

    private String extra;

    private int level;

    private boolean preBuild;

}
