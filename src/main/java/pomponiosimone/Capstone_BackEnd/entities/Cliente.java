package pomponiosimone.Capstone_BackEnd.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="clienti")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Cliente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    private String cognome;
    private String avatarURL;
    private String email;
    private String indirizzoCompleto;
    private String password;
    private LocalDate dataDiNascita;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Ordine> ordini;

    //Costruttore

    public Cliente(String avatarURL, String cognome, LocalDate dataDiNascita, String email, String indirizzoCompleto, String nome, String password) {
        this.avatarURL = avatarURL;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.email = email;
        this.indirizzoCompleto = indirizzoCompleto;
        this.nome = nome;
        this.password = password;
    }
}
