package Mail.Main;

import Mail.Interface.MainFrame;

public class Main {
    
    public static void main(String[] args) {
        Server server = new Server(12345);
        //Client client = new Client(server, new Account("a", "b", server));
        //client.connetti(12345);
        new MainFrame(server);
        //new MainFrame(server);
    }

}
