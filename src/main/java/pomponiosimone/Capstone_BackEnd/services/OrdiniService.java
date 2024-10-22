package pomponiosimone.Capstone_BackEnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pomponiosimone.Capstone_BackEnd.entities.*;

import pomponiosimone.Capstone_BackEnd.enums.StatoOrdine;
import pomponiosimone.Capstone_BackEnd.enums.TipoPagamento;
import pomponiosimone.Capstone_BackEnd.enums.TipoSpedizione;
import pomponiosimone.Capstone_BackEnd.exceptions.BadRequestException;
import pomponiosimone.Capstone_BackEnd.payloads.ArticoloDTO;
import pomponiosimone.Capstone_BackEnd.payloads.OrdineDTO;
import pomponiosimone.Capstone_BackEnd.repositories.ClientiRepository;
import pomponiosimone.Capstone_BackEnd.repositories.OrdiniRepository;
import pomponiosimone.Capstone_BackEnd.repositories.ScarpeRepository;

import java.util.*;

@Service
public class OrdiniService {
@Autowired
private OrdiniRepository ordiniRepository;
@Autowired
private ClientiRepository clientiRepository;
@Autowired
private ScarpeRepository scarpeRepository;

//Get by id
public Ordine findOrdineById(UUID id) {
    return ordiniRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("Ordine non trovato con ID: " + id));
}
    //GET ALL
    public Page<Ordine> findAllOrdini(int page, int size, String sortBy) {
        if (page > 12) page = 12;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return this.ordiniRepository.findAll(pageable);
    }
   //Save
   public Ordine saveOrdine(OrdineDTO body) throws BadRequestException {

       TipoSpedizione tipoSpedizione;
       if (body.tipoSpedizione().equalsIgnoreCase("DOMICILIO")) {
           tipoSpedizione = TipoSpedizione.DOMICILIO;
       } else if (body.tipoSpedizione().equalsIgnoreCase("RITIRO")) {
           tipoSpedizione = TipoSpedizione.RITIRO;
       } else {
           throw new BadRequestException("Spedizione non valida. Scegliere uno tra DOMICILIO o RITIRO.");
       }
       TipoPagamento metodoPagamento;
       if (body.metodoPagamento().equalsIgnoreCase("CONTANTI")) {
           metodoPagamento = TipoPagamento.CONTANTI;
       } else if (body.metodoPagamento().equalsIgnoreCase("CARTA")) {
           metodoPagamento = TipoPagamento.CARTADICREDITO;
       } else {
           throw new BadRequestException("Pagamento non valido. Scegliere uno tra CONTANTI o CARTA DI CREDITO.");
       }
       Cliente cliente = clientiRepository.findById(body.clienteId())
               .orElseThrow(() -> new BadRequestException("Cliente non trovato con ID: " + body.clienteId()));
       List<ScarpaOrdine> scarpeOrdinate = new ArrayList<>();
       for (ArticoloDTO articolo : body.articoli()) {
           UUID scarpaId = articolo.scarpaId();
           UUID tagliaId = articolo.tagliaId();
           Integer quantita = articolo.quantità();
           if (scarpaId == null || tagliaId == null || quantita == null) {
               throw new BadRequestException("ID della scarpa, ID della taglia e quantità sono obbligatori.");
           }
           if (quantita <= 0) {
               throw new BadRequestException("Quantità non valida per l'articolo con ID: " + scarpaId);
           }
           Scarpa scarpa = scarpeRepository.findById(scarpaId)
                   .orElseThrow(() -> new BadRequestException("Scarpa non trovata con ID: " + scarpaId));
           Taglia tagliaSelezionata = scarpa.getTaglie().stream()
                   .filter(t -> t.getId().equals(tagliaId))
                   .findFirst()
                   .orElseThrow(() -> new BadRequestException("Taglia non trovata per la scarpa con ID: " + scarpaId));
           if (tagliaSelezionata.getQuantità() < quantita) {
               throw new BadRequestException("Quantità richiesta non disponibile per la taglia con ID: " + tagliaId);
           }

           tagliaSelezionata.setQuantità(tagliaSelezionata.getQuantità() - quantita);

           ScarpaOrdine scarpaOrdine = new ScarpaOrdine(scarpa, tagliaSelezionata, quantita);
           scarpaOrdine.setOrdine(null);
           scarpeOrdinate.add(scarpaOrdine);
       }

       Ordine ordine = new Ordine(
               scarpeOrdinate,
               cliente,
               body.indirizzoSpedizione(),
               metodoPagamento,
               body.speseSpedizione(),
               StatoOrdine.ATTESA,
               tipoSpedizione,
               body.totaleOrdine()
       );

       for (ScarpaOrdine scarpaOrdine : scarpeOrdinate) {
           scarpaOrdine.setOrdine(ordine);
       }

    return this.ordiniRepository.save(ordine);

   }
//Delete
public void findByIdAndRemoveOrder(UUID ordineId) {
    Ordine found = this.findOrdineById(ordineId);
    this.ordiniRepository.delete(found);
}
//PUT
public Ordine modificaOrdine(UUID id, OrdineDTO body) throws BadRequestException {

    Ordine ordineEsistente = ordiniRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("Ordine non trovato con ID: " + id));

    TipoSpedizione tipoSpedizione;
    if (body.tipoSpedizione().equalsIgnoreCase("DOMICILIO")) {
        tipoSpedizione = TipoSpedizione.DOMICILIO;
    } else if (body.tipoSpedizione().equalsIgnoreCase("RITIRO")) {
        tipoSpedizione = TipoSpedizione.RITIRO;
    } else {
        throw new BadRequestException("Spedizione non valida. Scegliere uno tra DOMICILIO o RITIRO.");
    }

    TipoPagamento metodoPagamento;
    if (body.metodoPagamento().equalsIgnoreCase("CONTANTI")) {
        metodoPagamento = TipoPagamento.CONTANTI;
    } else if (body.metodoPagamento().equalsIgnoreCase("CARTA")) {
        metodoPagamento = TipoPagamento.CARTADICREDITO;
    } else {
        throw new BadRequestException("Pagamento non valido. Scegliere uno tra CONTANTI o CARTA DI CREDITO.");
    }

    Cliente cliente = clientiRepository.findById(body.clienteId())
            .orElseThrow(() -> new BadRequestException("Cliente non trovato con ID: " + body.clienteId()));


    ordineEsistente.setCliente(cliente);
    ordineEsistente.setIndirizzoSpedizione(body.indirizzoSpedizione());
    ordineEsistente.setMetodoPagamento(metodoPagamento);
    ordineEsistente.setSpeseSpedizione(body.speseSpedizione());
    ordineEsistente.setTipoSpedizione(tipoSpedizione);
    ordineEsistente.setTotaleOrdine(body.totaleOrdine());
    ordineEsistente.setStatoOrdine(StatoOrdine.ATTESA);


    return ordiniRepository.save(ordineEsistente);
}
}