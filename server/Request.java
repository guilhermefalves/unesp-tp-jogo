import java.io.Serializable;

public class Request implements Serializable
{
    private static final long serialVersionUID = 1L;

    // O que será feito (getSquares, movePlayer, etc)
    String method;

    /**
     * como enviar/receber coisas genéricas, como arraylist<square>?
     * 1) criar uma referencia para TODOS os objetos em request
     * assim o processRequest irá fazer o necessário e dps salvará a variável que
     * precisa ser salva (DESVANTAGEM: tamanho da requisição, escalabilidade)
     * 2) "JSON" ou um Object genérico, que deve ser transformado para então ser usado
     */
    Request(String method)
    {
        this.method = method;
    }

    @Override
    public String toString()
    {
        return this.method;
    }

    public String processRequest()
    {
        switch (this.method) {
            case "getSquares":
                return "i will return the squares positions?";
            default:
                return "asd";
        }
    }
}