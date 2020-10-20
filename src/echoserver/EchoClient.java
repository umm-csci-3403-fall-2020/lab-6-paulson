package echoserver;
import java.net.*;
import java.io.*;

public class EchoClient {

    public static final int portNumber = 6013;

    public static void main(String[] args) throws IOException {
        String server;
        if (args.length == 0) {
            server = "127.0.0.1";
        } else {
            server = args[0];
        }

        try {
            Socket socket = new Socket(server, portNumber);
            OutputStream socketOut = socket.getOutputStream();
            InputStream socketIn = socket.getInputStream();

            Thread outputWriter = new Writer(socket, socketIn);
            Thread inputReader = new Reader(socket, socketOut);

            outputWriter.start();
            inputReader.start();

        } catch (ConnectException ce) {
            System.out.println("We were unable to connect to " + server);
            System.out.println("You should make sure the server is running.");
        } catch (IOException ioe) {
            System.out.println("We caught an unexpected exception");
            System.err.println(ioe);
        }
    }

    public static class Reader extends Thread implements Runnable{
        //Only for reading from stdin and writing to socket
        private final Socket socket;
        private OutputStream out;

        public Reader(Socket s, OutputStream o){
            this.socket = s;
            this.out = o;
        }

        public void run() {
            try{
                int data;
                while((data = System.in.read()) != -1){
                    out.write(data);
                }
                socket.shutdownOutput();
            }
            catch (IOException e){ e.printStackTrace(); }
        }
    }

    public static class Writer extends Thread implements Runnable{
        //Only for reading from socket and writing to stdout
        private final Socket socket;
        private InputStream in;

        public Writer(Socket s, InputStream i) {
            this.socket = s;
            this.in = i;
        }

        public void run(){
            try{
                int data;
                while((data = in.read()) != -1) {
                System.out.write(data);
                }
                System.out.flush();
            }
            catch (IOException e) { e.printStackTrace(); }
        }
    }
}
