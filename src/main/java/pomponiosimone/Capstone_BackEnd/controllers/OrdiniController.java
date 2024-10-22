package pomponiosimone.Capstone_BackEnd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pomponiosimone.Capstone_BackEnd.entities.Ordine;
import pomponiosimone.Capstone_BackEnd.exceptions.BadRequestException;
import pomponiosimone.Capstone_BackEnd.payloads.NewEntityRespDTO;
import pomponiosimone.Capstone_BackEnd.payloads.OrdineDTO;
import pomponiosimone.Capstone_BackEnd.services.OrdiniService;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordini")
public class OrdiniController {
    @Autowired
    private OrdiniService ordiniService;
//ID
    @GetMapping("/{ordineId}")
    public Ordine getOrdineById(@PathVariable UUID ordineId) {
        return ordiniService.findOrdineById(ordineId);}
//GET ALL
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Ordine> findAllCliente (
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "12") int size,
                                        @RequestParam(defaultValue = "id") String sortBy) {
        return this.ordiniService.findAllOrdini(page, size, sortBy);
    }
    //Salvataggio
          @PostMapping("/crea/all")
        public NewEntityRespDTO saveOrdine(@RequestBody @Validated OrdineDTO body, BindingResult validationResult) throws BadRequestException {
            if (validationResult.hasErrors()) {
                String messages = validationResult.getAllErrors().stream()
                        .map(objectError -> objectError.getDefaultMessage())
                        .collect(Collectors.joining(". "));
                throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
            } else {
                return new NewEntityRespDTO(this.ordiniService.saveOrdine(body).getId());
            }
        }

        //Delete
        @DeleteMapping("/delete/{ordineId}")
        @PreAuthorize("hasAuthority('ADMIN')")
        public void findByIdAndDeleteOrder (@PathVariable UUID ordineId){
            this.ordiniService.findByIdAndRemoveOrder(ordineId);
        }

        @PutMapping("/modifica/{ordineId")
        @PreAuthorize("hasAuthority('ADMIN')")
        public Ordine findByOrderAndUpdate(@PathVariable UUID ordineId, @RequestBody OrdineDTO body) throws BadRequestException {
            return this.ordiniService.modificaOrdine(ordineId, body);
    }}

