import java.awt.*;
import javax.swing.*;
import javax.imageio.*; // class ImageIO
import java.io.*;       // class File
import java.util.ArrayList;
import java.util.Random;

public class GameBoard extends JPanel
{
    private Client client;

    private Image imgBg;
    private Image imgQuad;
    private Dimension panelSize;
    private ArrayList<Square> squares;

    GameBoard(Client client)
    {
        try {
            imgBg = ImageIO.read(new File("imgs/bg.jpg"));
            imgQuad = ImageIO.read(new File("imgs/square-grey.png"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Imagem não encontrada " + e.getMessage());
            System.exit(1);
        }

        this.panelSize = new Dimension(1000, 600);
        setPreferredSize(panelSize);

        // Posição inicial dos elementos inseridos
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 40));
        this.client = client;
        this.squares = this.getSquaresInServer();
    }

    private ArrayList<Square> getSquaresInServer()
    {
        this.client.requireData("getSquares");
        return this.client.request.squares;
    }

    public ArrayList<Square> getSquares()
    {
        return this.squares;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);;
        g.drawImage(imgBg, 0, 0, panelSize.width, panelSize.height, this);
        this.drawSquares(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawSquares(Graphics g)
    {
        for(Square square : squares) {
            g.drawImage(imgQuad, square.x, square.y, Square.getWidth(), Square.getHeight(), this);
        }
    }

}