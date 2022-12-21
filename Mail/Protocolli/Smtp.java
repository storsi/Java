package Mail.Protocolli;

import Mail.Main.Server;
import Mail.Main.Client;
import Mail.Main.Mail;

public class Smtp{
    
    //private String mailFrom;
    private Client client;
    private Mail mail;

    public Smtp(Client client) {
        this.client = client;
    }

    public void mandaMail(Mail mail) {
        this.mail = mail;
        helo();
        client.chiudiConnessione();
    }

    private void helo() {
        //Apre la connessione con il server inviando il proprio IP o il nome della macchina
        client.connetti(25);
        
        if(client.input().equals("220")) {
        client.output("IP:localhost\n");

        if(client.input().equals("250")) mailFrom();
        }
        else System.out.println("[SMTP] Errore in Helo");

    }

    private void mailFrom() {
        //Invia l'intestazione della posta (FROM: propria email)
        client.output("FROM:" + mail.getEmailMandante() + "\n");
        
        if(client.input().equals("250")) rcptTo();
        else System.out.println("[SMTP] Errore in mailFrom");
    }

    private void rcptTo() {
        //Invia altre info tra cui la mail del destinatario (TO: email destinatario)
        client.output("TO:" + mail.getEmailDestinatario() + "\n");

        if(client.input().equals("250")) data();
        else System.out.println("[SMTP] Errore in rcptTo");
    }

    private void data() {
        //Inizio del contenuto del mesaggio

        client.output("Ready for Data?\n");

        if(client.input().equals("354")) sendMessage();
        else System.out.println("[SMTP] Errore in Data");
    }

    private void sendMessage() {
        //contenuto del messaggio (ogni linea termina con un punto)
        client.output(mail.getOggettoMail() + ":" + mail.getContenutoMail() + "\n");

        if(client.input().equals("250")) quit();
        else System.out.println("[SMTP] Errore in sendMessaggio");
    }

    private void quit() {
        //chiude la connessione
        client.output("Quit!!\n");
    }
}


