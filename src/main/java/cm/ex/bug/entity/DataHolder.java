package cm.ex.bug.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "data_holder")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DataHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private String extra;

    private int level;

    private boolean preBuild;

}
