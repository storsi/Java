package Mail.Protocolli;

import java.util.ArrayList;
import java.lang.instrument.Instrumentation;

import Mail.Main.Server;
import Mail.Main.Account;
import Mail.Main.Client;
import Mail.Main.Mail;

public class Pop {
    //porta 110

    private Server server;
    private Account account;
    private Client client;
    private ArrayList<Mail> nuoveMails;

    public Pop(Server server, Account account) {
        this.client = new Client(server, account, false);
        this.server = server;
        this.account = account;
        nuoveMails = new ArrayList<Mail>();
    }

    public ArrayList<Mail> getEmail() {
        client.connetti(110);

        ArrayList<Mail> provvisorio;
        if(autenticazione()) {
            transazione();
            aggiornamento();
        }

        client.chiudiConnessione();

        provvisorio = nuoveMails;
        nuoveMails.removeAll(nuoveMails);

        return provvisorio;
    }

    private boolean autenticazione() {
        
        client.output("AUTH:" + account.getMail() + ":" + account.getPassword() + "\n");

        String risp = client.input();

        if(risp.equals("T"))
            return true;
        else    
            return false;
    }

    private void transazione() {
        //Il client ottiene i messaggi e li segna sul server con il flag delete
        //Contiene anche la funzione dele (contrassegna con il flag delete)
        client.output("TRANS:" + account.getMail() + ":POP\n");
        do {
            String[] comp = client.input().split(":");
            nuoveMails.add(new Mail(comp[1], account.getMail(), comp[2], comp[3]));

        }while(!client.input().equals("DONE"));

        aggiornamento();
    }

    private void aggiornamento() {
        //Il server elimina i messaggi contrassegnati con il flag delete
        client.output("UPDATE\n");
    } 
}
