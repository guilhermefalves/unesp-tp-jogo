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
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void processRequest()
    {
        switch(this.request.method) {
            case "getSquares":
                this.request.squares = this.gameBoard.getSquares();
                break;
            case "newPlayer":
                Player p = new Player(this.players.size() + 1, this.playersColors, this.gameBoard.getDimension());
                this.request.player = p;
                this.players.add(p);
                this.playersColors.add(p.getColor());
                break;
            case "movePlayer":
                // Percorro os players
                for (Player player: this.players) {
                    // e ao encontrar o que fez a requisição
                    if (player.equals(this.request.player)) {
                        // altero sua posição
                        player.setPosition(this.request.player.getX(), this.request.player.getY());
                        // e por fim envio essa ação a todos os outros players
                        this.sendTo(this.players, player, "movePlayers");
                        break;
                    }
                }
                break;

        }
    }

    /**
     * Função para enviar ações geradas por um player aos outros
     * @param players 
     * @param exclude
     * @param method
     */
    public void sendTo(ArrayList<Player> players, Player exclude, String method)
    {
        this.request.method = method;
        Socket server;
        ObjectOutputStream objOut;
        try {
            for(Player p: players) {
                // não envio ao player que gerou essa ação
                if (p.equals(exclude)) {
                    continue;
                }
                server = new Socket("localhost", 11000 + p.getId());
                objOut = new ObjectOutputStream(server.getOutputStream());
                objOut.writeObject(this.request);
                server.close();
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION IN CLIENT " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
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