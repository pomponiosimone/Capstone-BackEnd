package pomponiosimone.Capstone_BackEnd.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "richieste_clienti")
@NoArgsConstructor
@ToString
public class ContattoRichiesta {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String nome;
    private String email;
    private String oggetto;
    private String messaggio;

    //Costruttore
    public ContattoRichiesta(String email, String messaggio, String nome, String oggetto) {
        this.email = email;
        this.messaggio = messaggio;
        this.nome = nome;
        this.oggetto = oggetto;
    }
}
