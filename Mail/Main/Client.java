package Mail.Main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import Mail.Protocolli.Pop;
import Mail.Protocolli.Smtp;

public class Client {
    
    private Socket socket;
    private InetSocketAddress Serv;
    private int porta;
    private Server server;
    private String risposta;
    private InputStreamReader in;
    private BufferedReader reader;
    private DataOutputStream out;
    private Account account;
    private boolean staMandando;

    private Smtp protSmtp;

    public Client(Server server, Account account) {
        staMandando = false;
        this.porta = 12345;
        this.server = server;
        this.account = account;

        protSmtp = new Smtp(this, server);

        //new Thread(new richiestaMail(this, account)).start();
    }

    public void mandaMail(Mail mail) {
        staMandando = true;
        protSmtp.mandaMail(mail);
        staMandando = false;
    }

    public boolean staMandando() {
        return staMandando;
    }

    public void connetti() {
        System.out.println("Client richiede sulla porta: " + porta);
        try {
            socket = new Socket("localhost", porta);
            
            System.out.println("[Client] Connesso");
        } catch (Exception e) {
            System.err.println("[Client] Errore nella connessione");
        }
    }

    public void output(String messaggio) {
        try {
            out = new DataOutputStream(socket.getOutputStream());
            out.writeBytes(messaggio);
        } catch (Exception e) {
            System.err.println("[Client] Errore nell'invio del messaggio");
            chiudiConnessione();
        }
    }

    public String input() {
        try {
            in = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(in);
            risposta = reader.readLine();
        } catch (Exception e) {
            System.err.println("[Client] Errore nella lettura del messaggio " + e.getLocalizedMessage());
            risposta = "";
            chiudiConnessione();
        }

        return risposta;
    }

    public void chiudiConnessione() {
        try {
            socket.close();
            System.out.println("[Client] Chiusura effettuata");
        } catch (Exception e) {
            System.err.println("[Client] Errore nella chiusura del socket");
        }
    }
}

class richiestaMail implements Runnable {

    private Client client;
    private Account account;

    public richiestaMail(Client client, Account account) {
        this.client = client;
        this.account = account;
    }

    @Override
    public void run() {
        do {
            if(!client.staMandando()) {
                client.connetti();

                client.output("New Data? " + account.getMail() + "\n");
                
                try {
                    this.wait(1000);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                if(client.input().equals("Yes")) {
                    System.out.println(client.input());
                    account.mailDaLeggere();
                }

                client.chiudiConnessione();
            }

        }while(true);
        
    }
}
