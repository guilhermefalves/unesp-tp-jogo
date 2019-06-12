import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class MazeBomb extends JFrame
{
    Client client;
    GameBoard gameboard;
    Player player;

    MazeBomb()
    {
        super("MazeBomb");

        this.client = new Client();

        this.gameboard = new GameBoard(this.client);

        // Requisito ao server para que crie um novo jogador
        this.client.requireData("newPlayer");

        // E crio o player no client, de acordo com o que veio do server
        Player player = new Player(gameboard, this.client.request.player);

        // Defino em que posição o player irá começar
        this.gameboard.setLayout(new FlowLayout(FlowLayout.LEFT, player.getX(), player.getY()));

        gameboard.add(player);
        add(gameboard);

        setPreferredSize(new Dimension(1000,600));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);

        // Coloco o foco no jogador para pegar os eventos do KeyListener
        player.requestFocus();
    }

    public static void main(String[] args)
    {
        new MazeBomb();
    }
}