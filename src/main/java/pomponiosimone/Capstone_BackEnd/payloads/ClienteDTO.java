package pomponiosimone.Capstone_BackEnd.payloads;

import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.Size;


public record ClienteDTO(

        @NotEmpty(message = "Il nome è obbligatorio")
        @Size(min = 2, max = 50, message = "Il nome deve avere tra 2 e 50 caratteri")
        String nome,

        @NotEmpty(message = "Il cognome è obbligatorio")
        @Size(min = 2, max = 50, message = "Il cognome deve avere tra 2 e 50 caratteri")
        String cognome,

        String avatarURL,

        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "L'email inserita non è valida")
        String email,

        @NotEmpty(message = "L'indirizzo è obbligatorio")
        @Size(min = 10, max = 200, message = "L'indirizzo deve avere tra 10 e 200 caratteri")
        String indirizzoCompleto,

        @NotEmpty(message = "La password è obbligatoria")
        @Size(min = 8, message = "La password deve avere almeno 8 caratteri")
        String password,

        @NotEmpty(message = "Data di nascita obbligatoria")
        String dataDiNascita
) {
}
