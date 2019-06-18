import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

class MazeBomb extends JFrame implements Runnable
{
    Client client;
    GameBoard gameboard;
    Player player;

    ServerSocket listener;
    Request request;

    ArrayList<Player> players;

    MazeBomb()
    {
        super("MazeBomb");

        this.client = new Client();

        this.gameboard = new GameBoard(this.client);

        // Requisito ao server para que crie um novo jogador
        this.client.requireData("newPlayer");

        // E crio o player no client, de acordo com o que veio do server
        Player player = new Player(gameboard, this.client.request.player, this.client);

        // Defino em que posição o player irá começar

        this.gameboard.add(player);
        add(gameboard);

        setPreferredSize(new Dimension(1000,600));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);

        // Coloco o foco no jogador para pegar os eventos do KeyListener
        player.requestFocus();

        // Por último vou iniciar a thread da classe cliente que será responsável
        // por receber dados do servidor que irão alterar coisas como a posição
        // do outro jogador
        this.startListener(player.getId());

        // E crio um array para armazenar os players que estão no tabuleiro
        players = new ArrayList<Player>();
    }

    public void run()
    {
        Socket listened;
        try {
            while (true) {
                // Espero até que seja feita uma requisição
                listened = this.listener.accept();

                // Recebo um objeto Request
                ObjectInputStream in = new ObjectInputStream(listened.getInputStream());
                Request request = (Request) in.readObject();

                // Realizo o tratamento nesse objeto
                this.processRequest(request);
            }
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Processa as requisições recebidas do servidor
     */
    public void processRequest(Request request)
    {
        switch(request.method) {
            case "movePlayers":
                if (!players.contains(request.player)) {
                    System.out.println("Não contêm");
                    Player play = new Player(request.player);
                    players.add(play);
                    this.gameboard.add(play);
                    this.pack();
                }

                // Percorro os players e movimento os que forem necessários
                for (Player p: players) {
                    if (request.player.equals(p)) {
                        p.setPosition(request.player.getX(), request.player.getY());
                        break;
                    }
                }
                break;
        }
    }

    public void startListener(int id)
    {
        int port = 11000 + id;
        try {
            this.listener  = new ServerSocket(port);

            Thread t = new Thread(this);
            t.start();
        } catch(Exception e) {
            System.out.println("Problemas ao iniciar listener (ERRO: " + e.getMessage() + ")");
        }
    }

    public static void main(String[] args)
    {
        new MazeBomb();
    }
}