package echoserver;
import java.net.*;
import java.io.*;

public class EchoServer {
    public static final int portNumber = 6013;

    public static void main(String[] args) throws IOException, InterruptedException {
        EchoServer server = new EchoServer();
        server.start();
    }

    private void start() throws IOException, InterruptedException {
            ServerSocket sock = new ServerSocket(portNumber);

            while (true) {

                Socket client = sock.accept();
                System.out.println("Connected");

                Thread ClientHandler = new ClientHandler(client);
                ClientHandler.start();

            }
    }

    public static class ClientHandler extends Thread {
        Socket client;

        public ClientHandler(Socket c) {
            this.client = c;
        }

        public void run() {
            try {
                while (true) {
                    OutputStream output = client.getOutputStream();
                    InputStream input = client.getInputStream();

                    int stuff;
                    while((stuff = input.read()) != -1) {
                        output.write(stuff);

                        this.client.close();
                        System.out.println("Disconnected");
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
