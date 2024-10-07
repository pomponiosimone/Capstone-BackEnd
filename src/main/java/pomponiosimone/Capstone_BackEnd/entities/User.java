package pomponiosimone.Capstone_BackEnd.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pomponiosimone.Capstone_BackEnd.enums.Ruolo;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "utenti")
@JsonIgnoreProperties({"enabled", "accountNonLocked", "credentialsNonExpired", "accountNonExpired"})
public class User implements UserDetails {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private UUID id;
    private String email;
    private String nome;
    private String cognome;
    private String password;
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    //Costruttori

    public User(String cognome, String email, String nome, String password) {
        this.cognome = cognome;
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.ruolo = Ruolo.ADMIN;
    }
    @Override
    public String getUsername() { return this.email; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }
}
