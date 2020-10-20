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
            OutputStream output = client.getOutputStream();
            InputStream input = client.getInputStream();
            Thread ClientHandler = new ClientHandler(client, output, input);
            ClientHandler.start();

        }
    }

    public static class ClientHandler extends Thread {
        Socket client;
        OutputStream output;
        InputStream input;

        public ClientHandler(Socket c, OutputStream o, InputStream i) {
            this.client = c;
            this.output = o;
            this.input = i;
        }

        public void run() {
            try {
                    int stuff;
                    while ((stuff = input.read()) != -1) {
                        output.write(stuff);
                    }

                    output.flush();
                    client.shutdownOutput();
                    System.out.println("Disconnected");

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}


