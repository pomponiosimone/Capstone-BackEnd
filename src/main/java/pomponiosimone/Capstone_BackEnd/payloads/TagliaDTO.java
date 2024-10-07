package pomponiosimone.Capstone_BackEnd.payloads;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TagliaDTO(
        @NotNull(message = "La taglia è obbligatoriA")
        @Min(value = 37, message = "Il numero della taglia deve essere maggiore di 37")
        @Max(value= 47, message=" il numero della taglia deve essere minore di 47")
        Integer taglia,

        @NotNull(message = "La quantità è obbligatoria")
        @Min(value = 0, message = "La quantità non può essere negativa")
        Integer quantita
) {
}