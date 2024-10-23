package pomponiosimone.Capstone_BackEnd.payloads;

import jakarta.validation.constraints.*;
import pomponiosimone.Capstone_BackEnd.entities.Cliente;
import pomponiosimone.Capstone_BackEnd.entities.Scarpa;
import pomponiosimone.Capstone_BackEnd.enums.StatoOrdine;


import java.util.List;
import java.util.Map;
import java.util.UUID;

public record OrdineDTO(

        @NotNull(message = "La lista degli articoli non può essere vuota")
        List<ArticoloDTO> articoli,


        UUID clienteId,

        @NotEmpty(message = "L'indirizzo di spedizione è obbligatorio VIA/CIVICO/CITTà/CODICEPOSTALE")
        @Size(min = 10, max = 200, message = "L'indirizzo di spedizione deve avere tra 10 e 200 caratteri")
        String indirizzoSpedizione,

        @NotEmpty(message = "Il metodo di pagamento è obbligatorio")
        String metodoPagamento,

        @NotNull(message = "Le spese di spedizione sono obbligatorie")
        Double speseSpedizione,

        @NotNull(message = "Lo stato dell'ordine è obbligatorio")
         String statoOrdine,

        @NotEmpty(message = "Il tipo di spedizione è obbligatorio")
        String tipoSpedizione,

        @NotNull(message = "Il totale dell'ordine è obbligatorio")
       Double totaleOrdine
) {
}