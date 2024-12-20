package pomponiosimone.Capstone_BackEnd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pomponiosimone.Capstone_BackEnd.entities.Scarpa;

import pomponiosimone.Capstone_BackEnd.exceptions.BadRequestException;
import pomponiosimone.Capstone_BackEnd.payloads.NewEntityRespDTO;
import pomponiosimone.Capstone_BackEnd.payloads.ScarpaDTO;

import pomponiosimone.Capstone_BackEnd.services.ScarpeService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/scarpa")
public class ScarpeController {
    @Autowired
    private ScarpeService scarpeService;

    @GetMapping("/view/all")
    public Page<Scarpa> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "12") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {
        return this.scarpeService.findAll(page, size, sortBy);
    }

    //Get Marca
    @GetMapping("/view/{marca}")

    public Page<Scarpa> findMarca(@PathVariable() String marca,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "12") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return this.scarpeService.findMarca(marca, page, size, sortBy);
    }
@GetMapping("/view/nome/{nome}")
public List<Scarpa> findNome (@PathVariable() String nome){
        return this.scarpeService.findByNomeScarpa(nome);
}
      //get Id

    @GetMapping("/view/details/{scarpaId}")

    public Scarpa findByIdScarpa (@PathVariable UUID scarpaId) {
        return this.scarpeService.findScarpaById(scarpaId);
    }



     //Creazione scarpe

@PostMapping("/creazione")
@PreAuthorize("hasAuthority('ADMIN')")
@ResponseStatus(HttpStatus.CREATED)
public NewEntityRespDTO save(@RequestBody @Validated ScarpaDTO body, BindingResult validationResult) {

    if (validationResult.hasErrors()) {

        String messages = validationResult.getAllErrors().stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.joining(". "));

        throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
    } else {


        return new NewEntityRespDTO(this.scarpeService.saveSneakers(body).getId());
    }

}
//Modifica scarpa
    @PutMapping("/put/{scarpaId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Scarpa findByscarpaIdAndUpdate(@PathVariable UUID scarpaId, @RequestBody Scarpa body) throws BadRequestException {
        return this.scarpeService.modificaScarpa(scarpaId, body);
    }
//Delete shoes
    @DeleteMapping("/delete/{scarpaId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete (@PathVariable UUID scarpaId){
        this.scarpeService.findByIdAndRemoveShoes(scarpaId);
    }
   // UPLOAD
    @PostMapping("/{scarpaId}/immagine")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void uploadImmagine(@RequestParam("immagine") MultipartFile image, @PathVariable UUID scarpaId) throws IOException {
        this.scarpeService.uploadImg(image, scarpaId);
    }

    //Filtro scarpe in base alla descrizione
    @GetMapping("view/filtro")
    public ResponseEntity<Page<Scarpa>> filterMenuByDescrizione(@RequestParam String descrizione,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "12") int size,
                                                              @RequestParam(defaultValue = "id") String sortBy) {

        Page<Scarpa> filterMenu = this.scarpeService.findByDescrizione(descrizione, page, size, sortBy);
        return new ResponseEntity<>(filterMenu, HttpStatus.OK);
    }
}


