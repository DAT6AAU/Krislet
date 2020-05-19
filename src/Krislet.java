//
//	File:			Krislet.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//
//********************************************
//      Updated:               2008/03/01
//      By:               Edgar Acosta
//
//********************************************


import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;
import java.awt.geom.Point2D;


//***************************************************************************
//
//	This is main object class
//
//***************************************************************************
class Krislet {
    private DatagramSocket socket; // Socket to communicate with server
    private InetAddress host;    // Server address
    private int port;    // server port
    private static final int MSG_SIZE = 4096;    // Size of socket buffer

    private Pattern message_pattern = Pattern.compile("^\\((\\w+?)\\s.*");
    private Pattern hear_pattern = Pattern.compile("^\\(hear\\s(\\w+?)\\s(\\w+?)\\s(.*)\\).*");
    //private Pattern coach_pattern = Pattern.compile("coach");

    private String teamName;
    private Brain brain; // input for sensor information
    private boolean playing; // controls the MainLoop todo naming?

    private int playerNumber;
    private Point2D.Double startingCoordinate;
    public double headAngle;

    /*
    //---------------------------------------------------------------------------
    // The main application function.
    // Command line format:
    //
    // krislet [-parameter value]
    //
    // Parameters:
    //
    //	host (default "localhost")
    //		The host name can either be a machine name, such as "java.sun.com" 
    //		or a string representing its IP address, such as "206.26.48.100."
    //
    //	port (default 6000)
    //		Port number for communication with server
    //
    //	team (default Kris)
    //		Team name. This name can not contain spaces.
    //
    //	
    public static void main(String[] a)
            throws IOException {
        String hostName = "";
        int port = 6000;
        String team = "Krislet3";

        try {
            // First look for parameters
            for (int c = 0; c < a.length; c += 2) {
                if (a[c].compareTo("-host") == 0) {
                    hostName = a[c + 1];
                } else if (a[c].compareTo("-port") == 0) {
                    port = Integer.parseInt(a[c + 1]);
                } else if (a[c].compareTo("-team") == 0) {
                    team = a[c + 1];
                } else {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            System.err.println();
            System.err.println("USAGE: krislet [-parameter value]");
            System.err.println();
            System.err.println("    Parameters  value        default");
            System.err.println("   ------------------------------------");
            System.err.println("    host        host_name    localhost");
            System.err.println("    port        port_number  6000");
            System.err.println("    team        team_name    Kris");
            System.err.println();
            System.err.println("    Example:");
            System.err.println("      krislet -host www.host.com -port 6000 -team Poland");
            System.err.println("    or");
            System.err.println("      krislet -host 193.117.005.223");
            return;
        }

        Krislet player = new Krislet(InetAddress.getByName(hostName), port, team);

        // enter main loop
        player.mainLoop();
    }
    */

    //---------------------------------------------------------------------------
    // This constructor opens socket for  connection with server
    public Krislet(InetAddress host, int port, String teamName, Point2D.Double startingCoordinate)
            throws SocketException {
        this.socket = new DatagramSocket();
        this.host = host;
        this.port = port;
        this.teamName = teamName;
        this.playing = true;

        this.startingCoordinate = startingCoordinate;
    }

    //---------------------------------------------------------------------------
    // This destructor closes socket to server
    public void finalize() {
        socket.close();
    }

    //===========================================================================
    // Protected member functions

    //---------------------------------------------------------------------------
    // This is main loop for player
    protected void mainLoop() throws IOException {
        byte[] buffer = new byte[MSG_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, MSG_SIZE);

        // first we need to initialize connection with server
        init();

        socket.receive(packet);
        parseInitCommand(new String(buffer));
        port = packet.getPort();

        // Now we should be connected to the server
        // and we know side, player number and play mode
        while (playing) {
            parseSensorInformation(receive());
        }
        finalize();
    }

    //===========================================================================
    // Implementation of SendCommand Interface

    //---------------------------------------------------------------------------
    // This function sends move command to the server
    public void move(double x, double y) {
        send("(move " + x + " " + y + ")");
    }

    //---------------------------------------------------------------------------
    // This function sends turn command to the server
    public void turn(double moment) {
        send("(turn " + moment + ")");
    }

    public void turn_neck(double moment) {
        send("(turn_neck " + moment + ")");
    }

    //---------------------------------------------------------------------------
    // This function sends dash command to the server
    public void dash(double power) {
        send("(dash " + power + ")");
    }

    //---------------------------------------------------------------------------
    // This function sends kick command to the server
    public void kick(double power, double direction) {
        send("(kick " + power + " " + direction + ")");
    }

    //---------------------------------------------------------------------------
    // This function sends say command to the server
    public void say(String message) {
        send("(say " + message + ")");
    }

    //---------------------------------------------------------------------------
    // This function sends change_view command to the server
    public void changeView(String angle, String quality) {
        send("(change_view " + angle + " " + quality + ")");
    }

    //---------------------------------------------------------------------------
    // This function sends bye command to the server
    public void bye() {
        playing = false;
        send("(bye)");
    }

    //---------------------------------------------------------------------------
    // This function parses initial message from the server
    protected void parseInitCommand(String message)
            throws IOException {
        Matcher m = Pattern.compile("^\\(init\\s(\\w)\\s(\\d{1,2})\\s(\\w+?)\\).*$").matcher(message);
        if (!m.matches()) {
            throw new IOException(message);
        }

        // initialize player's brain
        brain = new Brain(this,
                m.group(1).charAt(0),
                Integer.parseInt(m.group(2)),
                m.group(3),
                startingCoordinate);
    }

    //===========================================================================
    // Here comes collection of communication function
    //---------------------------------------------------------------------------
    // This function sends initialization command to the server
    private void init() {
        send("(init " + teamName + " (version 9))");
    }

    //---------------------------------------------------------------------------
    // This function parses sensor information
    private void parseSensorInformation(String message)
            throws IOException {
        // First check kind of information
        Matcher m = message_pattern.matcher(message);
        if (!m.matches()) {
            throw new IOException(message);
        }
        if (m.group(1).compareTo("see") == 0) {
            if(message.startsWith("(see 0)")){ //Temporary fix //Do not know why this happens.
                return;
            }
            VisualInfo info = new VisualInfo(message);
            info.parse();
            brain.see(info);
        } else if (m.group(1).compareTo("hear") == 0) {
            parseHear(message);
        } else if(m.group(1).compareTo("sense_body") == 0){
            parseSenseBody(message);
        }
    }

    private void parseSenseBody(String message){
        headAngle = parseHeadAngle(message);
    }

    private double parseHeadAngle(String message){
        String[] splitString = message.split(" ");
        for(int i = 0; i < splitString.length; i++){
            if(splitString[i].compareTo("(head_angle") == 0){
                String headAngleValue = splitString[i+1];
                return Double.parseDouble(headAngleValue.substring(0, headAngleValue.length() - 1));
            }
        }

        throw new IllegalArgumentException();
    }

    //---------------------------------------------------------------------------
    // This function parses hear information
    private void parseHear(String message)
            throws IOException {
        // get hear information
        Matcher m = hear_pattern.matcher(message);
        int time;
        String sender;
        String uttered;
        if (!m.matches()) {
            throw new IOException(message);
        }
        time = Integer.parseInt(m.group(1));
        sender = m.group(2);
        uttered = m.group(3);
        if (sender.compareTo("referee") == 0) {
            brain.hear(time, uttered);
        }
        //else if( coach_pattern.matcher(sender).find()){
        //    m_brain.hear(time,sender,uttered);}
        else if (sender.compareTo("self") != 0) {
            brain.hear(time, Integer.parseInt(sender), uttered);
        }
    }

    //---------------------------------------------------------------------------
    // This function sends via socket message to the server
    public void send(String message) {
        byte[] buffer = Arrays.copyOf(message.getBytes(), MSG_SIZE);
        try {
            DatagramPacket packet = new DatagramPacket(buffer, MSG_SIZE, host, port);
            socket.send(packet);
        } catch (IOException e) {
            System.err.println("socket sending error " + e);
        }
    }

    //---------------------------------------------------------------------------

    // This function waits for new message from server
    private String receive() {
        byte[] buffer = new byte[MSG_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, MSG_SIZE);
        try {
            socket.receive(packet);
        } catch (SocketException e) {
            System.out.println("shutting down...");
        } catch (IOException e) {
            System.err.println("socket receiving error " + e);
        }
        return new String(buffer);
    }
}
