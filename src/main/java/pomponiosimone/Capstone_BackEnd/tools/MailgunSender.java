 package pomponiosimone.Capstone_BackEnd.tools;


import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pomponiosimone.Capstone_BackEnd.entities.ContattoRichiesta;

 @Component
public class MailgunSender {
    private String apiKey;
    private String domainName;
    private String fromEmail;

    public MailgunSender(@Value("${mailgun.key}") String apiKey,
                         @Value("${mailgun.domain}") String domainName,
                         @Value("${mailgun.email}") String fromEmail) {

        this.apiKey = apiKey;
        this.domainName = domainName;
        this.fromEmail = fromEmail;
    }

    public void sendEmail(String contattoEmail, String subject, String body) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", contattoEmail)
                .queryString("to", this.fromEmail)
                .queryString("subject", subject)
                .queryString("text", body)
                .asJson();

        System.out.println(response.getBody());
    }
}