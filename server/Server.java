import java.net.*;
import java.io.*;
import java.util.ArrayList;
public class Server implements Runnable
{
    ServerSocket server;
    Socket client;
    String in, out;
    Request request;

    GameBoard gameBoard;
    ArrayList<Player> players;
    ArrayList<String> playersColors;

    public Server()
    {
        try {
            this.request = new Request();
            this.server  = new ServerSocket(11000);

            this.gameBoard = new GameBoard();

            players = new ArrayList<Player>();
            playersColors = new ArrayList<String>();
        } catch(Exception e) {}
    }

    public void run()
    {
        try {
            while (true) {
                // Espero até que seja feita uma requisição
                this.client = this.server.accept();

                // Recebo um objeto Request
                ObjectInputStream in = new ObjectInputStream(this.client.getInputStream());
                this.request = (Request) in.readObject();
                System.out.println(this.request.method);

                // Realizo o tratamento nesse objeto
                this.processRequest();

                // E o devolvo para o client
                ObjectOutputStream objOut = new ObjectOutputStream(this.client.getOutputStream());
                objOut.writeObject(this.request);
            }
        } catch (Exception e) {}
    }

    public void processRequest()
    {
        switch(this.request.method) {
            case "getSquares":
                this.request.squares = this.gameBoard.getSquares();
                break;
            case "newPlayer":
                Player p = new Player(this.playersColors, this.gameBoard.getDimension());
                this.request.player = p;
                this.players.add(p);
                this.playersColors.add(p.getColor());
                break;
        }
    }

    public void sendOutput(String out)
    {
        try {
            PrintStream ps = new PrintStream(this.client.getOutputStream());
            ps.println(out);
        } catch (Exception e) {

        }
    }

    public static void main(String args[])
    {
        Server server = new Server();
        Thread t = new Thread(server);
        t.start();
        System.out.println("Server Started");
    }
}