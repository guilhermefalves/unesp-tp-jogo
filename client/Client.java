import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client implements Runnable
{
    Thread thread;
    Request request;

    Client()
    {
        this.thread  = new Thread(this);
        this.request = new Request();
    }

    public void run() {}

    /**
     * Função para enviar um objeto Request para o servidor, também é responsável
     * por esperar uma resposta (Request).
     * A função usa um wait para que seja sincrona, ou seja, o programa só continua
     * depois que os dados do servidor voltarem
     */
    public synchronized Request requireData(String method)
    {
        this.request.method = method;

        synchronized(this.thread) {
            try {
                Socket server = new Socket("localhost", 11000);
                ObjectOutputStream objOut = new ObjectOutputStream(server.getOutputStream());
                objOut.writeObject(this.request);
    
                ObjectInputStream objIn = new ObjectInputStream(server.getInputStream());
                this.request = (Request) objIn.readObject();
                server.close();
            } catch (Exception e) {
                System.out.println("EXCEPTION IN CLIENT " + e.getMessage());
                e.printStackTrace();
                System.exit(0);
            }
        }

        return this.request;
    }

    public void sendData(String method)
    {
        this.request.method = method;
        try {
            Socket server = new Socket("localhost", 11000);
            ObjectOutputStream objOut = new ObjectOutputStream(server.getOutputStream());
            objOut.writeObject(this.request);
        } catch (Exception e) {
            System.out.println("EXCEPTION IN CLIENT " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
}