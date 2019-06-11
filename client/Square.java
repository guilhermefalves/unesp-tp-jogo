import java.io.Serializable;
import java.awt.*;
import javax.swing.*;

public class Square implements Serializable
{
    private static final long serialVersionUID = 1L;

    int x, y;
    static int width = 40, height = 40;

    Square() {}

    Square(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    static int getWidth()
    {
        return width;
    }

    static int getHeight()
    {
        return height;
    }

    static Dimension getDimension()
    {
        return (new Dimension(width, height));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Square)) {
            return false;
        }
        Square sq = (Square) obj;
        return this.x == sq.x && this.y == sq.y;
    }
}