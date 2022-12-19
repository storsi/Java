package Mail.Main;

import java.util.ArrayList;

import Mail.Interface.MailPanel;
import Mail.Protocolli.Imap;
import Mail.Protocolli.Pop;

public class Account {
    
    private String password, email;
    private MailPanel mailPanel;
    private ArrayList<Mail> mailInAttesa;
    private String protocollo;
    private Pop pop;
    private Imap imap;
    private Server server;

    public Account(String email, String password, Server server) {
        pop = new Pop(server, this);
        imap = new Imap(server, this);
        mailInAttesa = new ArrayList<Mail>();

        this.server = server;
        this.email = email;
        this.password = password;

        protocollo = "POP";
    }

    public void setMailPanel(MailPanel mailPanel) {
        this.mailPanel = mailPanel;
    }

    public void mailDaLeggere() {
        mailPanel.recuperaMail(getEmail());
    }

    private ArrayList<Mail> getEmail() {
        return server.getMail(email, protocollo);
    }

    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
    }

    public void modificaPassword(String nuovaPassword) {
        this.password = nuovaPassword;
    }

    public String getMail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProtocollo() {
        return protocollo;
    }
}
