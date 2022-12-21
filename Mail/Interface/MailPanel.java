package Mail.Interface;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Mail.Main.Account;
import Mail.Main.Mail;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MailPanel extends JPanel implements ActionListener{

    private JPanel pannelloTop;
    private JPanel pannelloMidRic;
    private JPanel pannelloMidMan;
    private JPanel pannelloMid;
    private JPanel btnPanel;
    private int mailDaLeggere, totMail, mailInviate;
    private JLabel lbl_titolo;
    private MainPanel mainPanel;
    private Account account;
    private JButton btn_tornaIndietro;
    private JButton btn_Man;
    private JButton btn_Ric;
    private GuardaMailPanel guardaMailPanel;

    public MailPanel(MainPanel mainPanel) {
        setPreferredSize(new Dimension(400, 600));
        setLayout(new BorderLayout());

        mailDaLeggere = 0;
        totMail = 0;
        mailInviate = 0;
        this.mainPanel = mainPanel;

        //pannelloTop
        lbl_titolo = new JLabel();
        lbl_titolo.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_titolo.setBounds(150, 20, 100, 60);
        modificaTitolo("<html>Mail totali: " + totMail + "<br>Mail da leggere: " + mailDaLeggere);

        pannelloTop = new JPanel(); 
        pannelloTop.setPreferredSize(new Dimension(400, 100)); 
        pannelloTop.setLayout(null);
        
        btn_tornaIndietro = new JButton("<-");
        btn_tornaIndietro.setBounds(0, 0, 50, 50);
        btn_tornaIndietro.addActionListener(this);

        pannelloTop.add(lbl_titolo);
        pannelloTop.add(btn_tornaIndietro);

        //pannelloMid mail ricevute
        pannelloMid = new JPanel();

        pannelloMidRic = new JPanel();
        pannelloMidRic.setPreferredSize(new Dimension(400, 450));
        pannelloMidRic.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        pannelloMidRic.setVisible(true);

        //pannelloMid mail mandate
        pannelloMidMan = new JPanel();
        pannelloMidMan.setPreferredSize(new Dimension(400, 450));
        pannelloMidMan.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        pannelloMidMan.setVisible(false);

        pannelloMid.add(pannelloMidMan);
        pannelloMid.add(pannelloMidRic);

        //pannello bottoni per cambiare pannelloMid
        btnPanel = new JPanel();
        btnPanel.setPreferredSize(new Dimension(400, 50));
        btnPanel.setLayout(null);

        btn_Man = new JButton("Mail mandate");
        btn_Man.setBounds(200, 0, 200, 50);
        btn_Man.addActionListener(this);
        btn_Ric = new JButton("Mail ricevute");
        btn_Ric.setBounds(0, 0, 200, 50);
        btn_Ric.addActionListener(this);

        btnPanel.add(btn_Man);
        btnPanel.add(btn_Ric);

        add(pannelloTop, BorderLayout.NORTH);
        add(pannelloMid, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(false);
    }

    public void modificaTitolo(String text) {
        lbl_titolo.setText(text);
    }

    public void setPanel(GuardaMailPanel guardaMailPanel) {
        this.guardaMailPanel = guardaMailPanel;
    }

    public JPanel getPanelMidRic() {
        return pannelloMidRic;
    }

    public void recuperaMail(ArrayList<Mail> mails) {

        for(int i = 0; i < mails.size(); i++) {
            MailButton button = new MailButton(mails.get(i), this, mainPanel, guardaMailPanel);
            button.setPreferredSize(new Dimension(400, 50));
            pannelloMidRic.add(button);
            mailDaLeggere++;
            totMail++;
        }

        pannelloMidRic.setVisible(true);
    }

    public void addMailInviate(Mail mail) {
        pannelloMidMan.add(new MailButton(mail, this, mainPanel, guardaMailPanel));
        mailInviate++;
    }

    public void mailLetta() {
        mailDaLeggere--;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getMail() {
        return account.getMail();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btn_tornaIndietro) {
            setVisible(false);
            mainPanel.setVisible(true);
        }

        if(e.getSource() == btn_Man) {
            modificaTitolo("Mail inviate: " + mailInviate);
            pannelloMidRic.setVisible(false);
            pannelloMidMan.setVisible(true);
        }

        if(e.getSource() == btn_Ric) {
            modificaTitolo("<html>Mail totali: " + totMail + "<br>Mail da leggere: " + mailDaLeggere);
            pannelloMidMan.setVisible(false);
            pannelloMidRic.setVisible(true);
        }
        
    }
}
