package pomponiosimone.Capstone_BackEnd.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="clienti")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cliente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    private String cognome;
    private String email;
    private String indirizzoCompleto;
    private String password;
    private LocalDate dataDiNascita;

    public Cliente(String cognome, LocalDate dataDiNascita, String email, String indirizzoCompleto, String nome, String password) {
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.email = email;
        this.indirizzoCompleto = indirizzoCompleto;
        this.nome = nome;
        this.password = password;
    }
}
