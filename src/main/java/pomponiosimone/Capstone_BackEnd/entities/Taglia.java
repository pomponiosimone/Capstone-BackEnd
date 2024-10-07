package pomponiosimone.Capstone_BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name="taglie")
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Taglia {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private int taglia;
    private int quantità;

    @ManyToOne
    @JoinColumn(name = "scarpa_id")
    private Scarpa scarpa;

    public Taglia( int quantità, int taglia) {
        this.quantità = quantità;
        this.taglia = taglia;
    }
}
