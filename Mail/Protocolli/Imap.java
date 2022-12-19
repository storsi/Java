package Mail.Protocolli;

import java.util.ArrayList;

import Mail.Main.Mail;
import Mail.Main.Server;

public class Imap {

    private Server server;
    private Account account;
    private Client client;
    
    public Imap(Server server, Account account, Client client) {
        this.server = server;
        this.account = account;
        this.client = client;
    }

    /*public ArrayList<Mail> getMails() {
        return server.getMails(email, "Imap");
    }*/

    public void delete(Mail mail) {
        //La cancellazione di un messaggio si esegue contrassegnandone uno con il flag delete 
        //e poi cancellarli con il comando "expunge"
        mail.setDeleteFlag();
        expunge();
    }

    private void expunge() {
        server.deleteMails();
    }
}