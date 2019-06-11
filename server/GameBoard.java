import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard
{
    private Dimension panelSize;
    private ArrayList<Square> squares;

    GameBoard()
    {
        this.panelSize = new Dimension(1000, 600);
        this.squares = this.createSquares();
    }

    private ArrayList<Square> createSquares()
    {
        ArrayList<Square> squares = new ArrayList<Square>();
        
        int totalPositionsX = panelSize.width  / Square.getWidth();
        int totalPositionsY = panelSize.height / Square.getHeight();
        int totalPositions  = totalPositionsX * totalPositionsY;
        int numOfBorders    = totalPositionsX * 2 + totalPositionsY * 2 - 4;
        int totalSquares    = totalPositions / 2 - numOfBorders;

        // criando as bordas (superior e inferior)
        for (int i = 0; i < totalPositionsX; i++) {
            squares.add(new Square(Square.getWidth() * i, 0));
            squares.add(new Square(Square.getWidth() * i, panelSize.height - Square.getHeight()));
        }

        // criando bordas (laterais)
        for (int i = 1; i < totalPositionsX - 1; i++) {
            squares.add(new Square(0, Square.getHeight() * i));
            squares.add(new Square(panelSize.width - Square.getWidth(), Square.getHeight() * i));
        }

        // quadrados no meio
        Random generator = new Random();
        for (int i = 0; i < totalSquares; i++) {
            squares.add(new Square(
                (generator.nextInt(totalPositionsX - 1) + 1) * Square.getWidth(),
                (generator.nextInt(totalPositionsY - 1) + 1) * Square.getHeight()
            ));
        }
        return squares;
    }

    public ArrayList<Square> getSquares()
    {
        return this.squares;
    }
}