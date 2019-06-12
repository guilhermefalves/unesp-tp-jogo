import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Player implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String color;
    private String[] colors = { "blue", "green", "orange", "red", "yellow" };
    private ArrayList<String> usedColors;
    private int x, y;
    private Dimension gameBoardDimension;
    Player(ArrayList<String> usedColors, Dimension gbDimension)
    {
        this.usedColors = usedColors;
        this.color = this.randomColor();
        this.gameBoardDimension = gbDimension;
        this.x = this.randomX();
        this.y = this.randomY();
    }
    private int randomX()
    {
        Random r = new Random();
        int max = this.gameBoardDimension.width / Square.getWidth();
        return (r.nextInt(max - 1) + 1) * Square.getWidth();
    }

    private int randomY()
    {
        Random r = new Random();
        int max = this.gameBoardDimension.height / Square.getHeight();
        return (r.nextInt(max - 1) + 1) * Square.getHeight();
    }

    private String randomColor()
    {
        Random r = new Random();
        String color;
        do {
            color = colors[r.nextInt(colors.length - 1)];
        } while (this.usedColors.contains(color));

        return color;
    }

    public String getColor()
    {
        return this.color;
    }

    public static void main(String args[])
    {
        ArrayList<String> u = new ArrayList<String>();
        Dimension d = new Dimension(1000, 600);
        Player p = new Player(u, d);

    }
}