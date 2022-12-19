package Mail.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Mail.Interface.MailPanel;

public class Server{
    
    private ServerSocket serverSocket;
    private Socket socketClient;
    private int porta;
    private ArrayList<Mail> mailsDaInviare;
    private ArrayList<Account> account;

    public Server(int porta) {
        this.porta = porta;
        mailsDaInviare = new ArrayList<Mail>();
        account = new ArrayList<Account>();
    }

    public void connetti(Client client) {
        try{
            do{
                serverSocket = new ServerSocket(porta);

                //client.connetti();

                socketClient = serverSocket.accept();
                System.out.println("[Server] Connesso");

                //Apre un nuovo thread dedicato al client
                new Thread(new ClientThread(socketClient, this)).start();
            }while(true);

        }catch(Exception e) {
            System.err.println("[Server] Errore nella connessione " + e.getLocalizedMessage());
        }
    }

    public Account checkAccount(String email, String password) {
        boolean emailEsistente = false;

        for(int i = 0; i < account.size(); i++) {
            if(account.get(i).getMail().equals(email)) {
                emailEsistente = true;
                if(account.get(i).getPassword().equals(password)) {
                    return account.get(i);
                }
            }
        }

        if(!emailEsistente) {
            Account acc = new Account(email, password, this);
            //cercaMail(acc);
            this.account.add(acc);
            return acc;
        }

        return null;
    }

    public void addMail(Mail mail) {
        mailsDaInviare.add(mail);

        for(int i = 0; i < account.size(); i++) {
            cercaMail(account.get(i));
        }

    }

    public boolean autenticazione(Account acc) {
        for(int i = 0; i < account.size(); i++) {
            if(account.get(i).getMail().equals(acc.getMail()) && account.get(i).getPassword().equals(acc.getPassword()))
                return true;
        }

        return false;
    }

    public boolean cercaMail(String mail) {

        for(int i = 0; i < mailsDaInviare.size(); i++) {
            if(mail.equals(mailsDaInviare.get(i).getEmailDestinatario())) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Mail> getMail(String mail, String protocollo) {
        ArrayList<Mail> arrayMailDaMandare = new ArrayList<Mail>();

        for(int i = 0; i < mailsDaInviare.size(); i++) {
            if(mailsDaInviare.get(i).getEmailDestinatario().equals(mail)) {
                arrayMailDaMandare.add(mailsDaInviare.get(i));

                if(protocollo.equals("POP"))
                    mailsDaInviare.get(i).setDeleteFlag();
            }
        }
        return arrayMailDaMandare;
    }

    public void deleteMails() {
        for(int i = 0; i < mailsDaInviare.size(); i++) {
            if(mailsDaInviare.get(i).getDeleteFlag()) {
                mailsDaInviare.remove(i);
                i--;
            }
        }
    }

    public void chiudiConnessione() {
        try {
            serverSocket.close();
            socketClient.close();
            System.out.println("[Server] Chiusura effettuata");
        } catch (Exception e) {
            System.err.println("[Server] Errore nella chiusura dei socket");
        }
    }
}

class ClientThread implements Runnable{

    private InputStreamReader in;
    private BufferedReader reader;
    private PrintWriter out;
    private Socket socketClient;
    private Mail mail;
    private Server server;
    private boolean openCodeSent = false;

    ClientThread(Socket socketClient, Server server) {
        mail = new Mail();
        this.socketClient = socketClient;
        this.server = server;
        //System.out.println("[Server] Thread creato");
    }

    public void run() {
        String input = "";
        out = null;
        reader = null;
        boolean quit = true;
        
        try {
            do {
                input = null;
    
                out = new PrintWriter(socketClient.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
    
                if(!openCodeSent) {
                    out.println("220");
                    openCodeSent = true;
                }
    
                do {
                    input = reader.readLine();
                } while(input == null);
    
                
                
                //Controllo messaggi in arrivo
                
                
                if(input.contains("IP:")) {
                    
                    mail.setIpmandante(input);
                    out.println("250\n");
                
                } else 
                
                if(input.contains("FROM:")) {
                    
                    mail.setEmailMandante(input);
                    out.println("250\n");

                } else 
                
                if(input.contains("TO:")) {
                    
                    mail.setEmailDestinatario(input);
                    out.println("250\n");
                
                } else 
                
                if(input.equals("Ready for Data?")) {
                    
                    out.println("354\n");
                
                } else 
                
                if(input.equals("Quit!!")) {
                    
                    if(out != null) out.close();
                    if(in != null) in.close();
                    quit = false;
                
                } else

                if(input.contains("New Data?")) {
                    String[] str = input.split("?");
                    if(server.cercaMail(str[1])) {
                        out.println("Yes\n");
                    }
                }
                
                else{
                    
                    mail.setContenutoMail(input);
                    out.println("250\n");
                
                }
            }while(quit);
        } catch (Exception e) {
            System.err.println("Errore nel Thread");
            e.printStackTrace();
        } 
        
        //System.out.println(mail.toString());
        server.addMail(mail);
        //server.chiudiConnessione();

    }

}
