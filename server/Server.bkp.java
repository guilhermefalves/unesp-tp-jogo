import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Scanner;


public class Server implements Runnable
{
    ServerSocket server;
    Socket client;
    String in, out;
    public Server()
    {
        try {
            this.server = new ServerSocket(11000);

        } catch(Exception e) {

        }

    }
    public void run()
    {
        String in, out;
        try {
            while (true) {
                this.client = this.server.accept();
                Scanner scanner = new Scanner(this.client.getInputStream());

                in  = scanner.nextLine();
                out = this.processRequest(in);
                this.sendOutput(out);
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public String processRequest(String in)
    {
        return in;
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