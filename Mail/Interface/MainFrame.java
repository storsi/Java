package Mail.Interface;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Mail.Main.Client;
import Mail.Main.Server;

public class MainFrame extends JFrame{

    private Client client;
    private MainPanel mainPanel;
    private AccessPanel accessPanel;
    private MailPanel mailPanel;
    private ImpostazioniPanel impostazioniPanel;
    private GuardaMailPanel guardaMailPanel;
    private JPanel pannelloGenerale;

    public MainFrame(Server server) {
        
        pannelloGenerale = new JPanel();
        pannelloGenerale.setPreferredSize(new Dimension(400, 600));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        mainPanel = new MainPanel(this);
        mailPanel = new MailPanel(mainPanel);

        accessPanel = new AccessPanel(server, mailPanel, mainPanel, this);
        impostazioniPanel = new ImpostazioniPanel(accessPanel, mainPanel, this);
        guardaMailPanel = new GuardaMailPanel(mailPanel, mainPanel);

        mainPanel.setPannelli(mailPanel, impostazioniPanel);
        accessPanel.addImpostazioniPanel(impostazioniPanel);
        mailPanel.setPanel(guardaMailPanel);
        mainPanel.setClient(accessPanel.getClient());

        pannelloGenerale.add(impostazioniPanel);
        pannelloGenerale.add(mailPanel);
        pannelloGenerale.add(accessPanel);
        pannelloGenerale.add(mainPanel);
        pannelloGenerale.add(guardaMailPanel);

        add(pannelloGenerale);

        pack();
        setVisible(true);
    }

    public MailPanel getMailPanel() {
        return mailPanel;
    }
}
