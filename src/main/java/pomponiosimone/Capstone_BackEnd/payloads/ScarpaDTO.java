package pomponiosimone.Capstone_BackEnd.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.util.List;

public record
ScarpaDTO(
        @NotEmpty(message = "il nome è obbligatorio")
        @Size(min = 3, max = 70)
        String nome,
        @NotNull
        double prezzo,
        @NotEmpty(message = "la desrizione è obbligatoria")
        @Size(min = 3, max = 150)
        String descrizione,
        @NotEmpty(message ="la  marca è obbligatoria")
        @Size(min = 3, max = 40)
        String marca,
        @NotEmpty(message ="immagine è obbligatoria")
        @Size(min = 3, max = 40)
        String immagine,
        List<TagliaDTO> taglie
) {
}

