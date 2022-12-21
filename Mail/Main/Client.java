package Mail.Main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import java.net.Socket;

import Mail.Protocolli.Smtp;

public class Client {
    
    private Socket socket;
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
        this.server = server;
        this.account = account;

        protSmtp = new Smtp(this, server);

        //richiestaMail();
    }

    public void mandaMail(Mail mail) {
        protSmtp.mandaMail(mail);
    }

    public boolean staMandando() {
        return staMandando;
    }

    public void connetti(int porta) {
        System.out.println("Client richiede sulla porta: " + porta);
        try {
            socket = new Socket("localhost", porta);
            
            System.out.println("[Client] Connesso");
        } catch (Exception e) {
            System.err.println("[Client] Errore nella connessione " + e.getLocalizedMessage());
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
