package Mail.Protocolli;

import Mail.Main.Server;
import Mail.Main.Client;
import Mail.Main.Mail;

public class Smtp{
    
    //private String mailFrom;
    private Client client;
    private Server server;
    private Mail mail;

    public Smtp(Client client, Server server) {
        this.client = client;
        this.server = server;
    }

    public void mandaMail(Mail mail) {
        this.mail = mail;
        helo();
        client.chiudiConnessione();
    }

    private void helo() {
        //Apre la connessione con il server inviando il proprio IP o il nome della macchina
        server.connetti(client);
        
        if(attendi("220") < 300) {
        client.output("IP:localhost\n");

        if(attendi("250") < 300) mailFrom();
        }
        else System.out.println("[SMTP] Errore in Helo");

    }

    private void mailFrom() {
        //Invia l'intestazione della posta (FROM: propria email)
        client.output("FROM:" + mail.getEmailMandante() + "\n");
        
        if(attendi("250") < 300) rcptTo();
        else System.out.println("[SMTP] Errore in mailFrom");
    }

    private void rcptTo() {
        //Invia altre info tra cui la mail del destinatario (TO: email destinatario)
        client.output("TO:" + mail.getEmailDestinatario() + "\n");

        if(attendi("250") < 300) data();
        else System.out.println("[SMTP] Errore in rcptTo");
    }

    private void data() {
        //Inizio del contenuto del mesaggio

        client.output("Ready for Data?\n");

        if(attendi("354") < 300) sendMessage();
        else System.out.println("[SMTP] Errore in Data");
    }

    private void sendMessage() {
        //contenuto del messaggio (ogni linea termina con un punto)
        client.output(mail.getOggettoMail() + ":" + mail.getContenutoMail() + "\n");

        if(attendi("250") < 300) quit();
        else System.out.println("[SMTP] Errore in sendMessaggio");
    }

    private void quit() {
        //chiude la connessione
        client.output("Quit!!\n");
    }

    private int attendi(String messaggio) {
        int indice = 0;
            do{
                indice++;
            }while(!client.input().equals(messaggio) && indice < 300);

        return indice;
    }
}


