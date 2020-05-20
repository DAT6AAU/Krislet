import java.io.IOException;
import java.net.InetAddress;
import java.awt.geom.Point2D;

public class Runner {

    public static void main(String[] args) throws IOException {

        String teamName;
        if(args.length == 1){
            teamName = args[0];
        }else{
            teamName = "Krislet";
        }

        /*
        int numberOfPlayers = 1; //Default 11 //TODO Change back!

        for (int i = 0; i < numberOfPlayers; i++) {
            KrisletThread player = new KrisletThread(-20,-20);
            player.start();
        }*/

        KrisletThread player1 = new KrisletThread(-1,-33, teamName);
        KrisletThread player2 = new KrisletThread(-1,-5, teamName);
        KrisletThread player3 = new KrisletThread(-15,-5, teamName);
        KrisletThread player4 = new KrisletThread(-19,3, teamName);
        KrisletThread player5 = new KrisletThread(-33,0, teamName);
        KrisletThread player6 = new KrisletThread(-23,15, teamName);
        KrisletThread player7 = new KrisletThread(-36,20, teamName);
        KrisletThread player8 = new KrisletThread(-43,20, teamName);
        KrisletThread player9 = new KrisletThread(-50,0, teamName);
        KrisletThread player10 = new KrisletThread(-51,33, teamName);
        KrisletThread player11 = new KrisletThread(-30,33, teamName);

        player1.start();/*
        player2.start();
        player3.start();
        player4.start();
        player5.start();
        player6.start();
        player7.start();
        player8.start();
        player9.start();
        player10.start();
        player11.start();*/
    }
}

class KrisletThread extends Thread {

    double x;
    double y;
    String teamName;

    public KrisletThread(double x, double y, String teamName){
        this.x = x;
        this.y = y;
        this.teamName = teamName;
    };

    public void run() {
        System.out.println("Starting player!");
        String hostName = "";
        int port = 6000;
        Krislet player = null;

        Point2D.Double startingCoordinate = new Point2D.Double(x, y);

        try {
            player = new Krislet(InetAddress.getByName(hostName), port, teamName, startingCoordinate);

            player.mainLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}