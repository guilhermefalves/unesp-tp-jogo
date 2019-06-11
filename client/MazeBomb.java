import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class MazeBomb extends JFrame
{
    Client client;
    GameBoard gameboard;
    // Player player;

    MazeBomb()
    {
        super("MazeBomb");

        this.client = new Client();

        gameboard = new GameBoard(this.client);
        // player = new Player("orange", gameboard);
        // gameboard.add(player);
        add(gameboard);

        setPreferredSize(new Dimension(1000,600));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);

        // // TODO: deve setar o foco para o jogador correto...
        // // Exemplo: no pc do player1, player1.requestFocus();
        // player.requestFocus();

    }

    public static void main(String[] args)
    {
        new MazeBomb();
    }
}