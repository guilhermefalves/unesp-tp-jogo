import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client implements Runnable
{
    Thread thread;
    Request request;

    public void run()
    {
        synchronized(this) {
            try {
                Socket server = new Socket("localhost", 11000);
                ObjectOutputStream objOut = new ObjectOutputStream(server.getOutputStream());
                objOut.writeObject(this.request);
    
                ObjectInputStream objIn = new ObjectInputStream(server.getInputStream());
                this.request = (Request) objIn.readObject();
                server.close();
                notify();
            } catch (Exception e) {
                System.out.println("EXCEPTION IN CLIENT " + e.getMessage());
                System.exit(0);
            }
        }
    }

    Client()
    {
        this.thread  = new Thread(this);
        this.request = new Request();
    }

    public synchronized Request requireData(String method)
    {
        this.request.method = method;
        this.thread.start();
        
        synchronized(this.thread) {
            try {
                wait();
            } catch (Exception e) {}
        }

        return this.request;
    }
}