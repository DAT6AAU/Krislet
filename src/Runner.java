import java.io.IOException;
import java.net.InetAddress;

public class Runner {

    public static void main(String[] args) throws IOException {

        int numberOfPlayers = 11;

        for(int i = 0; i < numberOfPlayers; i++){
            KrisletThread player = new KrisletThread();
            player.start();
        }
    }
}

class KrisletThread extends Thread {

    public void run() {
        System.out.println("Starting player!");
        String hostName = new String("");
        int port = 6000;
        String team = new String("Krislet");
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