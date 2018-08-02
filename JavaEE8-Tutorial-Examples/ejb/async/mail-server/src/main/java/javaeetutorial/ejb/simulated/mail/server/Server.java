package javaeetutorial.ejb.simulated.mail.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A simulated email server
 */
public class Server implements Runnable {
    
    private final static int PORT = 3025;
    private final Socket client;
    
    public static void main(String[] args) throws IOException {
        
        ServerSocket server = new ServerSocket(PORT);
        System.out.format("[Test SMTP server listening on port %d]", PORT);
        while (true) {
            Socket client = server.accept();
            new Thread(new Server(client)).start();
        }
    }
    
    public Server(Socket client) {
        this.client = client;
    }
    
    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            
            System.out.println("[Client connected]");
            out.println("220 javaeetutorial.asyncsmtpd");
            String inline = in.readLine();
            if (inline.split(" ")[0].compareTo("HELO") != 0 &&
                inline.split(" ")[0].compareTo("EHLO") != 0) {
                client.close(); 
                return;
            }
            out.println("250 +OK SMTP server Ready");
            if (in.readLine().split(":")[0].compareTo("MAIL FROM") != 0) {
                client.close(); 
                return;
            }
            out.println("250 +OK Sender OK");
            if (in.readLine().split(":")[0].compareTo("RCPT TO") != 0) {
                client.close(); 
                return;
            }
            out.println("250 +OK Recipient OK");
            
            if (!in.readLine().contains("DATA")) {
                client.close(); 
                return;
            }
            out.println("354 +OK Start mail input.");
            
            String msg = "";
            while ((inline = in.readLine()) != null) {
                if (inline.compareTo(".") == 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) { 
                    }
                    out.println("250 +OK Requested action completed.");
                    in.readLine();
                    client.close();
                    System.out.println("[Delivering message...]");
                    System.out.println(msg);
                    System.out.println("\n");
                } else {
                    msg = msg + inline + "\n";
                }
            }
        } catch (IOException e) { 
        }
    }
}
