import java.io.IOException;
import java.net.InetAddress;
import java.awt.geom.Point2D;

public class Runner {

    public static void main(String[] args) throws IOException {

        /*
        int numberOfPlayers = 1; //Default 11 //TODO Change back!

        for (int i = 0; i < numberOfPlayers; i++) {
            KrisletThread player = new KrisletThread(-20,-20);
            player.start();
        }*/

        KrisletThread player1 = new KrisletThread(-1,-1);
        KrisletThread player2 = new KrisletThread(-10,-10);
        KrisletThread player3 = new KrisletThread(-20,-20);
        KrisletThread player4 = new KrisletThread(-10,-20);
        KrisletThread player5 = new KrisletThread(-20,-10);
        KrisletThread player6 = new KrisletThread(-30,-30);
        KrisletThread player7 = new KrisletThread(-30,-10);
        KrisletThread player8 = new KrisletThread(-10,-30);
        KrisletThread player9 = new KrisletThread(-20,-30);
        KrisletThread player10 = new KrisletThread(-5,-30);
        KrisletThread player11 = new KrisletThread(-15,-30);

        player1.start();
        //player2.start();
        //player3.start();
        //player4.start();
        //player5.start();
        //player6.start();
        //player7.start();
        //player8.start();
        //player9.start();
        //player10.start();
        //player11.start();
    }
}

class KrisletThread extends Thread {

    double x;
    double y;

    public KrisletThread(double x, double y){
        this.x = x;
        this.y = y;
    };

    public void run() {
        System.out.println("Starting player!");
        String hostName = "";
        int port = 6000;
        String team = "Krislet";
        Krislet player = null;

        Point2D.Double startingCoordinate = new Point2D.Double(x, y);

        try {
            player = new Krislet(InetAddress.getByName(hostName), port, team, startingCoordinate);

            player.mainLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}