package Mail.Main;

import java.nio.channels.SocketChannel;

import Mail.Interface.MainFrame;

public class Main {
    
    public static void main(String[] args) {
        Server server = new Server(12345);
        //Client client = new Client(server, new Account("a", "b", server));
        //Client client2 = new Client(server, new Account("a", "b", server));
        
        //server.changePort(12345);
        //client.connetti(12345);
        //client.output("Ciao\n");
        //client2.connetti(12345);
        
        new MainFrame(server);
        //new MainFrame(server);
    }

}


