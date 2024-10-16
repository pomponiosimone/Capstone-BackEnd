package pomponiosimone.Capstone_BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;


import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    @OneToMany(mappedBy = "scarpa", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Taglia> taglie;
    @ManyToOne
    @JoinColumn(name = "id_ordine", nullable = true)
    private Ordine ordine;
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
    public List<Taglia> getTaglie() {
        
        return taglie.stream()
                .sorted(Comparator.comparingInt(Taglia::getTaglia))
                .collect(Collectors.toList());
    }
}

