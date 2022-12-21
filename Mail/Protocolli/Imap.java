package Mail.Protocolli;

import java.util.ArrayList;

import Mail.Main.Account;
import Mail.Main.Client;
import Mail.Main.Mail;
import Mail.Main.Server;

public class Imap {

    private Account account;
    private Client client;
    private ArrayList<Mail> nuoveMails;
    
    public Imap(Account account) {
        this.account = account;
        this.client = new Client(account, false);

        nuoveMails = new ArrayList<Mail>();
    }

    public ArrayList<Mail> getMails() {
        ArrayList<Mail> provvisorio;

        client.connetti(143);

        if(login()) {
            prendiMail();
        }

        client.chiudiConnessione();

        provvisorio = nuoveMails;
        nuoveMails.removeAll(nuoveMails);

        return provvisorio;
    }

    private boolean login() {

        client.output("AUTH:" + account.getMail() + ":" + account.getPassword() + "\n");

        String risp = client.input();

        if(risp.equals("T"))
            return true;
        else    
            return false;
    }

    private void prendiMail() {

        client.output("TRANS:" + account.getMail() + ":IMAP\n");
        do {
            String[] comp = client.input().split(":");
            nuoveMails.add(new Mail(comp[1], account.getMail(), comp[2], comp[3]));

        }while(!client.input().equals("DONE"));
    }
}
