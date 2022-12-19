package Mail.Interface;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Mail.Main.Client;
import Mail.Main.Mail;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel implements ActionListener{
    
    private JButton btn_impostazioni;
    private JButton btn_mail;
    private JButton btn_invia;
    private JTextArea ta_mailTo;
    private JTextArea ta_oggettoMail;
    private JTextArea ta_messaggioMail;
    private Client client;
    private MailPanel mailPanel;
    private ImpostazioniPanel impostazioniPanel;
    private MainFrame mainFrame;

    public MainPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setPreferredSize(new Dimension(400, 600));
        setLayout(null);

        btn_impostazioni = new JButton("Imp");
        btn_impostazioni.setBounds(0, 0, 50, 50);
        btn_impostazioni.addActionListener(this);

        btn_mail = new JButton("Mail");
        btn_mail.setBounds(350, 0, 50, 50);
        btn_mail.addActionListener(this);

        btn_invia = new JButton("Invia");
        btn_invia.setBounds(150, 500, 100, 50);
        btn_invia.addActionListener(this);

        JLabel lbl_mailTo = new JLabel("Mail to:");
        lbl_mailTo.setBounds(0, 200, 100, 30);
        JLabel lbl_oggettoMail = new JLabel("Oggetto:");
        lbl_oggettoMail.setBounds(0, 250, 100, 30);
        JLabel lbl_messaggioMail = new JLabel("Messaggio:");
        lbl_messaggioMail.setBounds(0, 300, 100, 30);

        ta_mailTo = new JTextArea("");
        ta_mailTo.setBounds(100, 200, 200, 30);

        ta_oggettoMail = new JTextArea();
        ta_oggettoMail.setBounds(100, 250, 200, 30);

        ta_messaggioMail = new JTextArea();
        ta_messaggioMail.setBounds(100, 300, 200, 100);

        add(btn_impostazioni);
        add(btn_mail);
        add(btn_invia);
        add(lbl_mailTo);
        add(lbl_oggettoMail);
        add(lbl_messaggioMail);
        add(ta_mailTo);
        add(ta_oggettoMail);
        add(ta_messaggioMail);


        setVisible(true);
    }

    public void setJTextDestOgg(String destinatario, String oggetto) {
        ta_mailTo.setText(destinatario);
        ta_oggettoMail.setText(oggetto);
    }
    
    public void setJTextOggMes(String messaggio, String oggetto) {
        ta_messaggioMail.setText(messaggio);
        ta_oggettoMail.setText(oggetto);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setPannelli(MailPanel mailPanel, ImpostazioniPanel impostazioniPanel) {
        this.mailPanel = mailPanel;
        this.impostazioniPanel = impostazioniPanel;
    }

    public MailPanel getMailPanel() {
        return mailPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_impostazioni) {
            setVisible(false);
            impostazioniPanel.setVisible(true);
        }

        if(e.getSource() == btn_mail) {
            setVisible(false);
            mailPanel.setVisible(true);
        }

        if(e.getSource() == btn_invia) {
            if(!ta_mailTo.getText().equals("")) {
                String[] strssplit = mainFrame.getTitle().split(": ");
                Mail mail = new Mail(strssplit[1], ta_mailTo.getText(), ta_oggettoMail.getText(), ta_messaggioMail.getText());
                client.mandaMail(mail);
                mailPanel.addMailInviate(mail);

                ta_mailTo.setText("");
                ta_oggettoMail.setText("");
                ta_messaggioMail.setText("");
            }
        }
    }
}
