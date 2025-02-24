package dev.buskopan;

public class Main {
    public static void main(String[] args) {

        int port = Integer.parseInt(args[0]);
        String url = args[1];

        Server server = new Server(port);

        server.start(url);
    }
}