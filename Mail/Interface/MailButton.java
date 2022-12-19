package Mail.Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Mail.Main.Mail;

public class MailButton extends JButton implements ActionListener{
    
    private Mail mail;
    private MailPanel mailPanel;
    private MainPanel mainPanel;
    private GuardaMailPanel guardaMailPanel;

    public MailButton(Mail mail, MailPanel mailPanel, MainPanel mainPanel, GuardaMailPanel guardaMailPanel) {
        this.mainPanel = mainPanel;
        this.mailPanel = mailPanel;
        this.guardaMailPanel = guardaMailPanel;
        this.mail = mail;
        
        setPreferredSize(new Dimension(400, 50));
        setBackground(Color.BLUE);
        setText("<html>" + mail.getEmailMandante() + "<br>" + mail.getOggettoMail());
        setForeground(Color.WHITE);
        addActionListener(this);

        guardaMailPanel.setEmail(mail);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mail.mailLetta();
        setBackground(new Color(102, 102, 255));
        mailPanel.mailLetta();
        System.out.println("btn premuto!");
        mailPanel.setVisible(false);
        guardaMailPanel.setVisible(true);
    }


}
