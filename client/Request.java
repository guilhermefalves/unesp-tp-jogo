import java.io.Serializable;
import java.util.ArrayList;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    // O que será feito (getSquares, movePlayer, etc)
    String method;

    // Os objetos que podem ser enviados/recebidos
    ArrayList<Square> squares;
    Player player;

    @Override
    public String toString()
    {
        return this.method;
    }

    Request(String method)
    {
        this.method = method;
    }

    Request() {}
}