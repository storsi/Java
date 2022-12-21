package Mail.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server{
    
    private ServerSocket serverSocket12345;
    private ServerSocket serverSocket25;
    private ServerSocket serverSocket110;
    private ServerSocket serverSocket143;

    private Socket socketClient12345;
    private Socket socketClient25;
    private Socket socketClient110;
    private Socket socketClient143;
    private int porta;
    private ArrayList<Mail> mailsDaInviare;
    private ArrayList<Account> account;

    public Server(int porta) {
        this.porta = porta;
        mailsDaInviare = new ArrayList<Mail>();
        account = new ArrayList<Account>();

        connetti();
    }

    public void changePort(int porta) {
        this.porta = porta;
    }

    public boolean a() {
        return socketClient12345 == null;
    }

    public void connetti() {
        //System.out.println("Server in ascolto su porta: " + porta);
        Thread attesaConnessioni12345 = new Thread(() -> {
            try{
                do{
                    serverSocket12345 = new ServerSocket(12345);
    
                    socketClient12345 = serverSocket12345.accept();
                    System.out.println("[Server] Connesso");
    
                    //Apre un nuovo thread dedicato al client
                    new Thread(new ClientThread(socketClient12345, this)).start();
                
                }while(true);
    
            }catch(Exception e) {
                System.err.println("[Server] Errore nella connessione 12345 " + e.getLocalizedMessage());
            }
        });
        
        Thread attesaConnessioni25 = new Thread(() -> {
            try{
                do{
                    serverSocket25 = new ServerSocket(25);
    
                    socketClient25 = serverSocket25.accept();
                    System.out.println("[Server] Connesso");
    
                    //Apre un nuovo thread dedicato al client
                    new Thread(new ClientThread(socketClient25, this)).start();
                
                }while(true);
    
            }catch(Exception e) {
                System.err.println("[Server] Errore nella connessione 25 " + e.getLocalizedMessage());
            }
        });

        Thread attesaConnessioni110 = new Thread(() -> {
            try{
                do{
                    serverSocket110 = new ServerSocket(110);
    
                    socketClient110 = serverSocket110.accept();
                    System.out.println("[Server] Connesso");
    
                    //Apre un nuovo thread dedicato al client
                    new Thread(new ClientThread(socketClient110, this)).start();
                
                }while(true);
    
            }catch(Exception e) {
                System.err.println("[Server] Errore nella connessione 110 " + e.getLocalizedMessage());
            }
        });
        
        Thread attesaConnessioni143 = new Thread(() -> {
            try{
                do{
                    serverSocket143 = new ServerSocket(143);
    
                    socketClient143 = serverSocket143.accept();
                    System.out.println("[Server] Connesso");
    
                    //Apre un nuovo thread dedicato al client
                    new Thread(new ClientThread(socketClient143, this)).start();
                
                }while(true);
    
            }catch(Exception e) {
                System.err.println("[Server] Errore nella connessione 143 " + e.getLocalizedMessage());
            }
        });

        attesaConnessioni12345.start();
        attesaConnessioni25.start();
        attesaConnessioni110.start();
        attesaConnessioni143.start();
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

    public boolean searchAccount(String mail, String password) {
        for(int i = 0; i < account.size(); i++) {
            if(account.get(i).getMail().equals(mail) && account.get(i).getPassword().equals(password))
                return true;
        }

        return false;
    }

    public void addMail(Mail mail) {
        mailsDaInviare.add(mail);

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

    /*public void chiudiConnessione() {
        try {
            serverSocket.close();
            socketClient.close();
            System.out.println("[Server] Chiusura effettuata");
        } catch (Exception e) {
            System.err.println("[Server] Errore nella chiusura dei socket");
        }
    }*/
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
        System.out.println("[Server] Thread creato");
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
                
                if(input.contains("AUTH")) {
                    String[] comp = input.split(":");
                    if(server.searchAccount(comp[1], comp[2]))
                        out.println("T\n");
                    else    
                        out.println("F\n");
                }

                if(input.contains("TRANS")) {
                    String[] comp = input.split(":");

                    ArrayList<Mail> messaggi = server.getMail(comp[1], comp[2]);

                    for(int i = 0; i < messaggi.size(); i++) {
                        out.println("NM:" + 
                                messaggi.get(i).getEmailMandante() + ":" + 
                                messaggi.get(i).getOggettoMail() + ":" +
                                messaggi.get(i).getContenutoMail() + "\n");
                    }

                    out.println("DONE\n");
                }

                if(input.equals("UPDATE")) {
                    server.deleteMails();
                }

                if(input.contains("New Data?")) {
                    String[] str = input.split("\\?");
                    if(server.cercaMail(str[1])) {
                        out.println("Y\n");
                    }
                }

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
