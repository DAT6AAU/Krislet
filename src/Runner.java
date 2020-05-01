import java.io.IOException;
import java.net.InetAddress;

public class Runner {

    public static void main(String[] args) throws IOException {

        int numberOfPlayers = 1; //Default 11 //TODO Change back!

        for (int i = 0; i < numberOfPlayers; i++) {
            KrisletThread player = new KrisletThread();
            player.start();
        }
    }
}

class KrisletThread extends Thread {

    public void run() {
        System.out.println("Starting player!");
        String hostName = "";
        int port = 6000;
        String team = "Krislet";
        Krislet player = null;
        try {
            player = new Krislet(InetAddress.getByName(hostName),
                    port, team);
            player.mainLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}