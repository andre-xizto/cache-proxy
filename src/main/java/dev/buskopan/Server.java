package dev.buskopan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private ServerSocket socket;
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private final int port;
    private final HashMap<String, Object> cacheMap = new HashMap<>();

    public Server(int port) {
        this.port = port;
    }

    public Server() {
        this.port = 3000;
    }

    public void start(String url) {
        System.out.println("Rodando server na porta " + port);
        try {
            socket = new ServerSocket(port);
            client = socket.accept();
            System.out.println("Conexão ok!" + " proxy para: " + url);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String s = in.readLine();

            System.out.println(s);
            String response = "OK!";

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: plain/text");
            out.println("Content-Length: " + response.length());
            out.println();
            out.println(response);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        try {
            out.close();
            in.close();
            socket.close();
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
