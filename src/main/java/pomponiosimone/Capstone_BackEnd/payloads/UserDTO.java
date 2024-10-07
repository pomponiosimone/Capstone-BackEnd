package pomponiosimone.Capstone_BackEnd.payloads;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserDTO(
                      @NotEmpty(message = "L'email è obbligatoria")
                      @Email(message = "L'email non valida!")
                      String email,
                      @NotEmpty(message = "La password è obbligatoria")
                      @Size(min = 3, max = 40)
                      String password,
                      @NotEmpty(message = "Il nome è obbligatoria")
                      @Size(min = 3, max = 40)
                      String nome,
                      @NotEmpty(message = "Il cognome è obbligatoria")
                      @Size(min = 3, max = 40)
                      String cognome) {
}
