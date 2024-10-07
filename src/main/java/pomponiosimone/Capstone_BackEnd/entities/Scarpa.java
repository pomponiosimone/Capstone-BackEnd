package pomponiosimone.Capstone_BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;
import java.util.UUID;

@Entity
@Table(name= "scarpe")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class Scarpa {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    private String marca;
    private String descrizione;
    private double prezzo;
    private String immagine;
    @OneToMany(mappedBy = "scarpa", cascade = CascadeType.ALL )
    private List<Taglia> taglie;

    //Costruttore

    public Scarpa(String descrizione, String immagine, String marca, String nome, double prezzo, List<Taglia> taglie) {
        this.descrizione = descrizione;
        this.immagine = immagine;
        this.marca = marca;
        this.nome = nome;
        this.prezzo = prezzo;
        this.taglie = taglie;
        for (Taglia taglia : taglie) {
            taglia.setScarpa(this);
        }
    }

}

