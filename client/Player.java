import javax.swing.*;
import java.awt.*;
import javax.imageio.*; // class ImageIO
import java.io.*;       // class File
import java.io.Serializable; // class Serializable
import java.awt.event.*; // KeyListener

public class Player extends JPanel implements KeyListener, Serializable
{
    private static final long serialVersionUID = 1L;

    private String color;
    private String imgDir;
    private transient Image img;
    private transient GameBoard gameBoard;
    private transient Client client;
    private int x, y;
    private int id;

    Player(String color, GameBoard gameBoard)
    {
        this.color     = color;
        this.imgDir    = "imgs/square-" + this.color + ".png";
        this.gameBoard = gameBoard;

        try {
            img = ImageIO.read(new File(this.imgDir));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Imagem não encontrada " + e.getMessage());
            System.exit(1);
        }

        setPreferredSize(Square.getDimension());
        this.addKeyListener(this);
    }

    Player(GameBoard gameBoard, Player player, Client client)
    {
        this.color     = player.color;
        this.imgDir    = "imgs/square-" + this.color + ".png";
        this.gameBoard = gameBoard;
        this.client    = client;
        this.x = player.x;
        this.y = player.y;
        this.id = player.id;

        try {
            img = ImageIO.read(new File(this.imgDir));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Imagem não encontrada " + e.getMessage());
            System.exit(1);
        }

        setPreferredSize(Square.getDimension());
        this.addKeyListener(this);
    }

    /**
     * Construtor dos outros jogadores, por isso não é adicionado o KeyListener
     * @param player
     */
    Player(Player player)
    {
        this.color     = player.color;
        this.imgDir    = "imgs/square-" + this.color + ".png";
        this.x = player.x;
        this.y = player.y;
        this.id = player.id;

        try {
            img = ImageIO.read(new File(this.imgDir));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Imagem não encontrada " + e.getMessage());
            System.exit(1);
        }

        setPreferredSize(Square.getDimension());
    }

    public int getId()
    {
        return this.id;
    }

    public String getColor()
    {
        return this.color;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                this.movePlayer(1, 0);
                break;
            case KeyEvent.VK_LEFT:
                this.movePlayer(-1, 0);
                break;
            case KeyEvent.VK_UP:
                this.movePlayer(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                this.movePlayer(0, 1);
                break;
        }
    }

    private void movePlayer(int toMoveX, int toMoveY)
    {
        int newX = getX() + toMoveX * Square.getWidth();
        int newY = getY() + toMoveY * Square.getHeight();

        if (!isValidMovement(newX, newY)) {
            // TODO: tocar algo??? INVALID MOVEMENT
            return;
        }

        this.x = newX;
        this.y = newY;
        setLocation(newX, newY);

        // Envio o movimento para o server
        this.client.request.player = this;
        this.client.sendData("movePlayer");
    }

    private Boolean isValidMovement(int x, int y)
    {
        // para fora do panel
        if (x < 0 || y < 0) {
            return false;
        }

        // para fora do panel
        if (x + Square.getWidth() > this.gameBoard.getWidth()) {
            return false;
        }

        // para fora do panel
        if (y + Square.getHeight() > this.gameBoard.getHeight()) {
            return false;
        }

        // indo p cima de outro quadrado
        return !this.gameBoard.getSquares().contains(new Square(x, y));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(this.img, 0, 0, Square.getWidth(), Square.getHeight(), this);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }
        Player p = (Player) obj;

        return p.getId() == this.id;
        // return this.color.equals(p.color);
    }
}