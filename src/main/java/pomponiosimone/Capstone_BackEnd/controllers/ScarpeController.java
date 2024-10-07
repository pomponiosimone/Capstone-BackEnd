package pomponiosimone.Capstone_BackEnd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pomponiosimone.Capstone_BackEnd.exceptions.BadRequestException;
import pomponiosimone.Capstone_BackEnd.payloads.NewEntityRespDTO;
import pomponiosimone.Capstone_BackEnd.payloads.ScarpaDTO;

import pomponiosimone.Capstone_BackEnd.services.ScarpeService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/scarpa")
public class ScarpeController {
    @Autowired
    private ScarpeService scarpeService;

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

}}


