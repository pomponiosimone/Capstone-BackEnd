package pomponiosimone.Capstone_BackEnd.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ArticoloDTO(
        @NotNull(message = "ID della scarpa è obbligatorio")
        UUID scarpaId,

        @NotNull(message = "ID della taglia è obbligatorio")
        UUID tagliaId,

        @NotNull(message = "Quantità è obbligatoria")
        @Min(value = 1, message = "La quantità deve essere almeno 1")
        Integer quantità
) {
}