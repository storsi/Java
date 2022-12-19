package Mail.Main;

import Mail.Interface.MainFrame;

public class Main {
    
    public static void main(String[] args) {
        Server server = new Server(12345);
        //Client client = new Client(12345, server, "spreafico.matteo@smail.com");

        //new AccessFrame(server);
        //new AccessFrame(server);
        //new MainFrame(client, server);
        new MainFrame(server);
        new MainFrame(server);
    }

}
