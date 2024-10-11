package pomponiosimone.Capstone_BackEnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pomponiosimone.Capstone_BackEnd.entities.ContattoRichiesta;
import pomponiosimone.Capstone_BackEnd.payloads.ContattoRichiestaDTO;
import pomponiosimone.Capstone_BackEnd.repositories.ContattiRichiestaRepository;
import pomponiosimone.Capstone_BackEnd.tools.MailgunSender;


@Service
public class ContattiRichiestaService {
    @Autowired
    ContattiRichiestaRepository contattiRichiestaRepository;
    @Autowired
    MailgunSender sender;

    //Post email

    public String sendEmail(ContattoRichiestaDTO body) {

        String subject = body.oggetto();
        String bodyEmail = "Nome: " + body.nome() + " " +
                "Messaggio: " + body.messaggio();

        sender.sendEmailHelp(body.email(), subject, bodyEmail);
        return "Email inviata all'azienda";
    }

}
