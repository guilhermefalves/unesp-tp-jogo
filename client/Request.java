import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    
    String method;

    Request(String method)
    {
        this.method = method;
    }
}