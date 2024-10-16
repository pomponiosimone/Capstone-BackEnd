package pomponiosimone.Capstone_BackEnd.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pomponiosimone.Capstone_BackEnd.entities.Cliente;
import pomponiosimone.Capstone_BackEnd.entities.Scarpa;
import pomponiosimone.Capstone_BackEnd.payloads.ClienteDTO;
import pomponiosimone.Capstone_BackEnd.payloads.NewEntityRespDTO;
import pomponiosimone.Capstone_BackEnd.payloads.UserDTO;
import pomponiosimone.Capstone_BackEnd.security.JWTTools;
import pomponiosimone.Capstone_BackEnd.services.ClientiService;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clienti")
public class ClientiController {
    @Autowired
    private ClientiService clientiService;


      //Get All Clienti
    @GetMapping("/view/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Cliente> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "12") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {
        return this.clientiService.findAllCienti(page, size, sortBy);
    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<Cliente> login(@RequestBody ClienteDTO body) throws BadRequestException {
        Cliente cliente = clientiService.loginCliente(body);
        return ResponseEntity.ok(cliente);
    }
    //Registrazione Post
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewEntityRespDTO saveCliente(@RequestBody @Validated ClienteDTO body, BindingResult validationResult) throws BadRequestException {

        if (validationResult.hasErrors()) {

            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new pomponiosimone.Capstone_BackEnd.exceptions.BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {


            return new NewEntityRespDTO(this.clientiService.saveCliente(body).getId());
        }

    }
    //GET CLIENTE
    @GetMapping("/{clienteId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Cliente findByIdCliente(@PathVariable UUID clienteId) {
        return this.clientiService.findClienteById(clienteId);
    }

    //DELETE CLIENTE
    @DeleteMapping("/{clienteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID clienteId) {
        this.clientiService.findClienteByIdAndDelete(clienteId);
    }

    //PUT
    @PutMapping("/{clienteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente findByIdAndUpdate(@PathVariable UUID clienteId, @RequestBody Cliente body) throws org.apache.coyote.BadRequestException {
        return this.clientiService.findByClienteIdAndUpdate(clienteId, body);
    }
}



