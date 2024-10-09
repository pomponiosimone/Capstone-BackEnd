package pomponiosimone.Capstone_BackEnd.controllers;

import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/scarpa")
public class ScarpeController {
    @Autowired
    private ScarpeService scarpeService;

    @GetMapping("/view")
    public Page<Scarpa> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {
        return this.scarpeService.findAll(page, size, sortBy);
    }

    //Get Marca
    @GetMapping("/view/{marca}")

    public Page<Scarpa> findMarca(@PathVariable() String marca,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return this.scarpeService.findMarca(marca, page, size, sortBy);
    }


     //Creazione scarpe
@PostMapping("/creazione")
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
   // UPLOAD
    @PostMapping("/{scarpaId}/immagine")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void uploadImmagine(@RequestParam("immagine") MultipartFile image, @PathVariable UUID scarpaId) throws IOException {
        this.scarpeService.uploadImg(image, scarpaId);
    }

}


