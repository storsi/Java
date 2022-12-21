package Mail.Main;

import java.nio.channels.SocketChannel;

import Mail.Interface.MainFrame;

public class Main {
    
    public static void main(String[] args) {
        Server server = new Server(12345);
        Client client = new Client(server, new Account("a", "b", server), true);
        //Client client2 = new Client(server, new Account("a", "b", server));
        
        //server.changePort(12345);
        //client.connetti(12345);
        //client.chiudiConnessione();

        //client.output("Ciao\n");
        //client2.connetti(25);
        
        new MainFrame(server);
        //new MainFrame(server);
    }

}


