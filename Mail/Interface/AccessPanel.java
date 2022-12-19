package Mail.Interface;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Mail.Main.Account;
import Mail.Main.Client;
import Mail.Main.Server;

public class AccessPanel extends JPanel implements ActionListener{

    private MainPanel mainPanel;
    private Server server;
    private JTextArea ta_account, ta_password;
    private Account account;
    private MainFrame mainFrame;
    private MailPanel mailPanel;
    private ImpostazioniPanel imp;
    private Client client;
    
    public AccessPanel(Server server, MailPanel mailPanel, MainPanel mainPanel, MainFrame mainFrame) {
        account = null;
        this.server = server;
        this.mainFrame = mainFrame;
        this.mainPanel = mainPanel;
        this.mailPanel = mailPanel;
        

        setPreferredSize(new Dimension(400, 600));
        setLayout(null);
        
        JLabel lbl_titolo = new JLabel("Accedi al tuo account o registrati");
        lbl_titolo.setBounds(100, 50, 200, 50);
        JLabel lbl_account = new JLabel("Email:");
        lbl_account.setBounds(0, 200, 100, 30);
        JLabel lbl_password = new JLabel("Password:");
        lbl_password.setBounds(0, 250, 100, 30);

        ta_account = new JTextArea("");
        ta_account.setBounds(100, 200, 200, 30);
        ta_password = new JTextArea("");
        ta_password.setBounds(100, 250, 200, 30);

        JButton btn_prosegui = new JButton("Prosegui");
        btn_prosegui.setBounds(150, 350, 100, 50);
        btn_prosegui.addActionListener(this);

        add(lbl_titolo);
        add(ta_account);
        add(ta_password);
        add(btn_prosegui);
        add(lbl_account);
        add(lbl_password);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        account = null;

        

        if(!ta_account.getText().equals("") && !ta_password.getText().equals(""))
            account = server.checkAccount(ta_account.getText(), ta_password.getText());

        if(account != null) {
            setVisible(false);
            mainFrame.setTitle("Mail: " + ta_account.getText());
            ta_account.setText("");
            ta_password.setText("");
            account.setMailPanel(mailPanel);
            mainPanel.setVisible(true);
            mailPanel.setAccount(account);
            imp.setAccount(account);
            this.client = new Client(server, account);
        }
    }

    public void addImpostazioniPanel(ImpostazioniPanel imp) {
        this.imp = imp;
    }

    public Client getClient() {
        return client;
    }
}
