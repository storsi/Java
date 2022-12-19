package Mail.Interface;

import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Mail.Main.Account;

public class ImpostazioniPanel extends JPanel implements ActionListener{
    
    private AccessPanel accessPanel;
    private MainPanel mainPanel;
    private MainFrame mainFrame;
    private Account account;
    private JButton btn_tornaIndietro;
    private JButton btn_logout;
    private JButton btn_modPassword;
    private JTextArea ta_cambiaPassword;
    private JRadioButton pop;
    private JRadioButton imap;
    private JRadioButton on;
    private JRadioButton off;

    public ImpostazioniPanel(AccessPanel accessPanel, MainPanel mainPanel, MainFrame mainFrame) {

        setPreferredSize(new Dimension(400, 600));
        setLayout(null);

        this.accessPanel = accessPanel;
        this.mainPanel = mainPanel;
        this.mainFrame = mainFrame;

        btn_tornaIndietro = new JButton("<-");
        btn_tornaIndietro.setBounds(0, 0, 50, 50);
        btn_tornaIndietro.addActionListener(this);

        JLabel lbl_imp = new JLabel("Impostazioni", SwingConstants.CENTER);
        lbl_imp.setBounds(100, 10, 200, 30);

        //Zona selezione protocollo
        JLabel lbl_tipoProtocollo = new JLabel("Tipo di protocollo:", SwingConstants.CENTER);
        lbl_tipoProtocollo.setBounds(100, 80, 200, 30);

        pop = new JRadioButton("POP");
        pop.setBounds(175, 125, 100, 30);
        pop.setSelected(true);
        pop.addActionListener(this);
        imap = new JRadioButton("IMAP");
        imap.setBounds(175, 155, 100, 30);
        imap.addActionListener(this);

        ButtonGroup group1 = new ButtonGroup();
        group1.add(pop);
        group1.add(imap);

        //Zona account
        btn_logout = new JButton("Logout");
        btn_logout.setBounds(150, 235, 100, 20);
        btn_logout.addActionListener(this);

        JLabel lbl_cambiaPassword = new JLabel("Cambia password", SwingConstants.CENTER);
        lbl_cambiaPassword.setBounds(100, 305, 200, 30);
        ta_cambiaPassword = new JTextArea("");
        ta_cambiaPassword.setBounds(100, 335, 200, 30);
        
        btn_modPassword = new JButton("Modifica");
        btn_modPassword.setBounds(150, 385, 100, 20);
        btn_modPassword.addActionListener(this);

        //Zona crittografia
        JLabel lbl_crittografia = new JLabel("Crittografia:", SwingConstants.CENTER);
        lbl_crittografia.setBounds(100, 455, 200, 30);

        on = new JRadioButton("ON");
        on.setBounds(175, 500, 100, 30);
        on.setSelected(true);
        on.addActionListener(this);
        off = new JRadioButton("OFF");
        off.setBounds(175, 530, 100, 30);
        off.addActionListener(this);

        ButtonGroup group2 = new ButtonGroup();
        group2.add(on);
        group2.add(off);

        add(btn_tornaIndietro);
        add(lbl_imp);

        add(lbl_tipoProtocollo);
        add(pop);
        add(imap);

        add(btn_logout);
        add(lbl_cambiaPassword);
        add(ta_cambiaPassword);
        add(btn_modPassword);

        add(lbl_crittografia);
        add(on);
        add(off);

        setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(50, 70, 300, 2);
        g.fillRect(50, 210, 300, 2);
        g.fillRect(50, 442, 300, 2);
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_logout) {
            setVisible(false);
            mainFrame.setTitle("");
            accessPanel.setVisible(true);
        }

        if(e.getSource() == btn_modPassword) {
            if(!btn_modPassword.getText().equals(""))
                account.modificaPassword(ta_cambiaPassword.getText());
        }

        if(e.getSource() == btn_tornaIndietro) {
            setVisible(false);
            mainPanel.setVisible(true);
        }

        if(e.getSource() == pop) {
            account.setProtocollo("POP");
            mainPanel.getMailPanel().getPanelMidRic().removeAll();
            account.mailDaLeggere();
        }
        if(e.getSource() == imap) {
            account.setProtocollo("IMAP");
            mainPanel.getMailPanel().getPanelMidRic().removeAll();
        }
        if(e.getSource() == on) {
            System.out.println("ON");
        }
        if(e.getSource() == off) {
            System.out.println("OFF");
        }
        
    }
}
