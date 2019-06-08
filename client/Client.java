import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client implements Runnable
{
    public void run()
    {
        try {
            Socket server = new Socket("localhost", 11000);
            ObjectOutputStream objOut = new ObjectOutputStream(server.getOutputStream());
            Request toSend = new Request("getSquares");
            objOut.writeObject(toSend);

            ObjectInputStream objIn = new ObjectInputStream(server.getInputStream());
            Request received = (Request) objIn.readObject();
            System.out.println("RECEIVED DATA:" + received.method);
            server.close();


        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.getMessage());
            //TODO: handle exception
        }
    }

    public static void main(String args[])
    {
        Client client = new Client();
        Thread t = new Thread(client);
        t.start();
    }
}