package pomponiosimone.Capstone_BackEnd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import pomponiosimone.Capstone_BackEnd.payloads.ContattoRichiestaDTO;
import pomponiosimone.Capstone_BackEnd.services.ContattiRichiestaService;

import java.util.UUID;
@RestController
@RequestMapping("/send-email")

public class ContattiRichiestaController {
    @Autowired
    ContattiRichiestaService contattiRichiestaService;

    @PostMapping("/help")
    public ResponseEntity<String> sendEmailToAzienda(
            @RequestBody ContattoRichiestaDTO contattoRichiestaDTO) {
        String response = contattiRichiestaService.sendEmail(contattoRichiestaDTO);
        if (response.startsWith("Email inviata all'azienda")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}

