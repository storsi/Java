package Mail.Interface;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Mail.Main.Mail;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuardaMailPanel extends JPanel implements ActionListener{

    private MailPanel mailPanel;
    private MainPanel mainPanel;
    private JButton btn_tornaIndietro;
    private JButton btn_rispondiMail;
    private JButton btn_inoltra;
    private Mail mail;
    private JLabel lbl_mailFrom;
    private JLabel lbl_oggettoMail;
    private JLabel lbl_messaggioMail;

    public GuardaMailPanel(MailPanel mailPanel, MainPanel mainPanel) {
        setPreferredSize(new Dimension(400, 600));
        setLayout(null);
        
        this.mailPanel = mailPanel;
        this.mainPanel = mainPanel;
        

        btn_tornaIndietro = new JButton("<-");
        btn_tornaIndietro.setBounds(0, 0, 50, 50);
        btn_tornaIndietro.addActionListener(this);

        lbl_mailFrom = new JLabel();
        lbl_mailFrom.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_mailFrom.setBounds(100, 100, 200, 50);

        lbl_oggettoMail = new JLabel();
        lbl_oggettoMail.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_oggettoMail.setBounds(100, 170, 200, 50);

        lbl_messaggioMail = new JLabel();
        lbl_messaggioMail.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_messaggioMail.setBounds(100, 240, 200, 100);
        
        

        add(btn_tornaIndietro);
        add(lbl_mailFrom);
        add(lbl_oggettoMail);
        add(lbl_messaggioMail);
        

        setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(50, 159, 300, 2);
        g.fillRect(50, 219, 300, 2);
    }

    public void setEmail(Mail mail) {
        this.mail = mail;

        if(!mail.getEmailMandante().equals(mailPanel.getMail())) {

            btn_rispondiMail = new JButton("Rispondi");
            btn_rispondiMail.setBounds(0, 550, 100, 50);
            btn_rispondiMail.addActionListener(this);
            
            btn_inoltra = new JButton("Inoltra");
            btn_inoltra.setBounds(300, 550, 100, 50);
            btn_inoltra.addActionListener(this);

            lbl_mailFrom.setText("Mail from: " + mail.getEmailMandante());

            add(btn_inoltra);
            add(btn_rispondiMail);

        } else {
            lbl_mailFrom.setText("IO");
        }

        lbl_oggettoMail.setText("Oggetto: " + mail.getOggettoMail());
        lbl_messaggioMail.setText("Messaggio: " + mail.getContenutoMail());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_tornaIndietro) {
            setVisible(false);
            mailPanel.setVisible(true);
        }

        if(e.getSource() == btn_rispondiMail) {
            setVisible(false);
            mainPanel.setVisible(true);
            mainPanel.setJTextDestOgg(mail.getEmailMandante(), "Risposta a <" + mail.getOggettoMail() + ">");
        }

        if(e.getSource() == btn_inoltra) {
            setVisible(false);
            mainPanel.setVisible(true);
            mainPanel.setJTextOggMes(mail.getContenutoMail(), "Inoltro <" + mail.getOggettoMail() + ">");
        }
    }
    
}
