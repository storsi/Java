package Mail.Main;

import java.util.ArrayList;

import Mail.Interface.MailPanel;
import Mail.Protocolli.Imap;
import Mail.Protocolli.Pop;

public class Account {
    
    private String password, email;
    private MailPanel mailPanel;
    private String protocollo;
    private Pop pop;
    private Imap imap;
    private Server server;

    public Account(String email, String password, Server server) {
        pop = new Pop(server, this);
        imap = new Imap(server, this);

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
        if(protocollo.equals("POP")) {
            return pop.getEmail();
        } else {
            return imap.getMails();
        }
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
