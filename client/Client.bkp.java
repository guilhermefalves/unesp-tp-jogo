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
            Socket cli = new Socket("localhost", 11000);
            PrintWriter pw = new PrintWriter(cli.getOutputStream());
            Scanner is = new Scanner(cli.getInputStream());
            pw.println("TESTE");
            pw.flush();
            System.out.println("Response: " + is.nextLine());

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