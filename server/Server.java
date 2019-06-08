import java.net.*;
import java.io.*;

public class Server implements Runnable
{
    ServerSocket server;
    Socket client;
    String in, out;

    public Server()
    {
        try {
            this.server = new ServerSocket(11000);
        } catch(Exception e) {}
    }

    public void run()
    {
        try {
            while (true) {
                this.client = this.server.accept();

                // Recebo um objeto Request
                ObjectInputStream in = new ObjectInputStream(this.client.getInputStream());
                Request request = (Request) in.readObject();
                System.out.println(request.method);

                // Realizo o tratamento nesse objeto
                request.processRequest();

                // E o devolvo para o client
                ObjectOutputStream objOut = new ObjectOutputStream(this.client.getOutputStream());
                objOut.writeObject(request);
            }
        } catch (Exception e) {}
    }

    public void sendOutput(String out)
    {
        try {
            PrintStream ps = new PrintStream(this.client.getOutputStream());
            ps.println(out);
        } catch (Exception e) {

        }
    }

    public static void main(String args[])
    {
        Server server = new Server();
        Thread t = new Thread(server);
        t.start();
    }
}