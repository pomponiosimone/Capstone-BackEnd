package pomponiosimone.Capstone_BackEnd.services;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pomponiosimone.Capstone_BackEnd.entities.Cliente;
import pomponiosimone.Capstone_BackEnd.entities.Ordine;
import pomponiosimone.Capstone_BackEnd.exceptions.NotFoundException;
import pomponiosimone.Capstone_BackEnd.exceptions.UnauthorizedException;
import pomponiosimone.Capstone_BackEnd.payloads.ClienteDTO;
import pomponiosimone.Capstone_BackEnd.repositories.ClientiRepository;
import pomponiosimone.Capstone_BackEnd.security.JWTTools;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientiService {

    @Autowired
    private ClientiRepository clientiRepository;
    @Autowired
    private PasswordEncoder bcrypt;
@Autowired
private JWTTools jwtTools;
    //Find all
    public Page<Cliente> findAllCienti(int page, int size, String sortBy) {
           if (page > 12) page = 12;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.clientiRepository.findAll(pageable);

}
  //Find by Id
    public Cliente findClienteById(UUID clienteId) {
        return clientiRepository.findById(clienteId).orElseThrow(() -> new NotFoundException(clienteId));
    }

    //Delete
    public void findClienteByIdAndDelete (UUID clienteId) {
        Cliente found = this.findClienteById(clienteId);
        this.clientiRepository.delete(found);
    }

  //Salvataggio o registrazione
  public Cliente saveCliente(ClienteDTO body) throws BadRequestException {
      if (this.clientiRepository.existsByEmail(body.email()))

          throw new BadRequestException("Esiste già un cliente con email " + body.email());
      LocalDate dataDiNascita = LocalDate.parse(body.dataDiNascita());
return this.clientiRepository.save(new Cliente("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome(),body.cognome(),
        dataDiNascita, body.email(), body.indirizzoCompleto(), body.nome(), bcrypt.encode(body.password())));
}
    // Login
    public String createTokens(ClienteDTO body) throws BadRequestException {
        Cliente found = this.clientiRepository.findByEmail(body.email())
                .orElseThrow(() -> new BadRequestException("Cliente non trovato con email " + body.email()));
        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwtTools.createTokenCliente(found);
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
    //Modifica
    public Cliente findByClienteIdAndUpdate(UUID clienteId, Cliente body) throws BadRequestException {
        Cliente clienteFound = this.findClienteById(clienteId);
        if (!clienteFound.getId().equals(body.getId()) && this.clientiRepository.existsByEmail(body.getEmail()))
            throw new BadRequestException("Esiste già un cliente con email " + body.getEmail());
        clienteFound.setCognome(body.getCognome());
        clienteFound.setDataDiNascita(body.getDataDiNascita());
        clienteFound.setEmail(body.getEmail());
        clienteFound.setNome(body.getNome());
        clienteFound.setAvatarURL(body.getAvatarURL());
        clienteFound.setIndirizzoCompleto(body.getIndirizzoCompleto());
        return this.clientiRepository.save(clienteFound);
    }
    //Ordini
    public List<Ordine> getOrdiniByClienteId(UUID clienteId) {
        Optional<Cliente> cliente = clientiRepository.findById(clienteId);
        if (cliente.isPresent()) {
            return cliente.get().getOrdini();
        } else {
            throw new RuntimeException("Cliente non trovato con id: " + clienteId);
        }
    }
    public Cliente getClienteByEmail(String email) throws BadRequestException {
        return clientiRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Cliente non trovato con email: " + email));
    }
}



