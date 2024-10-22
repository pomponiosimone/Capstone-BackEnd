package pomponiosimone.Capstone_BackEnd.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "scarpa_ordine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScarpaOrdine {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_ordine", nullable = false)
    @JsonBackReference
    private Ordine ordine;

    @ManyToOne
    @JoinColumn(name = "id_scarpa", nullable = false)
    private Scarpa scarpa;

    @ManyToOne
    @JoinColumn(name = "id_taglia", nullable = false)
    private Taglia taglia;

    private Integer quantità;


    public ScarpaOrdine(Scarpa scarpa, Taglia taglia, Integer quantità) {
        this.scarpa = scarpa;
        this.taglia = taglia;
        this.quantità = quantità;
    }
}