package pomponiosimone.Capstone_BackEnd.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ContattoRichiestaDTO( @NotEmpty(message = "Il nome è obbligatoria")
                                    @Size(min = 3, max = 40)
                                    String nome,
        @NotEmpty(message = "L'email è obbligatoria")
                                   @Email(message = "L'email non valida!")
                                   String email,
                                   @NotEmpty(message = "Oggetto è obbligatorio")
                                   @Size(min = 3, max = 40)
                                   String oggetto,
                                   @NotEmpty(message = "Il messaggio è obbligatorio")
                                   @Size(min = 3, max = 200)
                                   String messaggio)  {
}
