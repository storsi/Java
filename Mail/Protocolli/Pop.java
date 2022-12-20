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
        this.server = server;
        this.account = account;
        nuoveMails = new ArrayList<Mail>();
    }

    /*public ArrayList<Mail> getEmail() {
        ArrayList<Mail> provvisorio;
        if(autenticazione()) {
            transazione();
            aggiornamento();
        }

        provvisorio = nuoveMails;
        nuoveMails.removeAll(nuoveMails);

        return provvisorio;
    }

    /*private boolean autenticazione() {
        return server.autenticazione(account);
    }

    private void transazione() {
        //Il client ottiene i messaggi e li segna sul server con il flag delete
        //Contiene anche la funzione dele (contrassegna con il flag delete)
        nuoveMails = server.getMail(account.getMail(), "POP");
        aggiornamento();
    }

    private void aggiornamento() {
        //Il server elimina i messaggi contrassegnati con il flag delete
        server.deleteMails();
    } */
}
