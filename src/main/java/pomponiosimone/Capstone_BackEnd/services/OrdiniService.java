package pomponiosimone.Capstone_BackEnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pomponiosimone.Capstone_BackEnd.entities.Cliente;
import pomponiosimone.Capstone_BackEnd.entities.Ordine;

import pomponiosimone.Capstone_BackEnd.enums.StatoOrdine;
import pomponiosimone.Capstone_BackEnd.enums.TipoPagamento;
import pomponiosimone.Capstone_BackEnd.enums.TipoSpedizione;
import pomponiosimone.Capstone_BackEnd.exceptions.BadRequestException;
import pomponiosimone.Capstone_BackEnd.payloads.OrdineDTO;
import pomponiosimone.Capstone_BackEnd.repositories.ClientiRepository;
import pomponiosimone.Capstone_BackEnd.repositories.OrdiniRepository;
@Service
public class OrdiniService {
@Autowired
private OrdiniRepository ordiniRepository;
private ClientiRepository clientiRepository;
    //GET ALL
    public Page<Ordine> findAllOrdini(int page, int size, String sortBy) {
        if (page > 12) page = 12;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return this.ordiniRepository.findAll(pageable);
    }
   //Save
    public Ordine SaveOrdine(OrdineDTO body) throws BadRequestException {
        TipoSpedizione tipoSpedizione;

        if (body.tipoSpedizione().equalsIgnoreCase("DOMICILIO")) {
            tipoSpedizione = TipoSpedizione.DOMICILIO;
        } else if (body.tipoSpedizione().equalsIgnoreCase("RITIRO")) {
            tipoSpedizione = TipoSpedizione.RITIRO;
        } else {
            throw new BadRequestException("Spedizione non valida. Scegliere uno tra DOMICILIO O RITIRO ");
        }
        TipoPagamento metodoPagamento;
        if (body.metodoPagamento().equalsIgnoreCase("CONTANTI")) {
            metodoPagamento = TipoPagamento.CONTANTI;
        } else if (body.metodoPagamento().equalsIgnoreCase("CARTA")) {
            metodoPagamento = TipoPagamento.CARTADICREDITO;
        } else {
            throw new BadRequestException("Pagamento non valido. Scegliere uno tra CONTANTI o CARTA DI CREDITO ");
        }    Cliente cliente = clientiRepository.findById(body.clienteId())
                .orElseThrow(() -> new BadRequestException("Cliente non trovato con ID: " + body.clienteId()));
        return this.ordiniRepository.save(new Ordine(body.articoli(),cliente,body.indirizzoSpedizione(),metodoPagamento, body.speseSpedizione(),
                StatoOrdine.ATTESA,tipoSpedizione, body.totaleOrdine())); }
    }

