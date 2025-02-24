package dev.buskopan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable{

    private final Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private final String url;

    public ServerThread(Socket client, String url) {
        this.url = url;
        this.client = client;
    }

    @Override
    public void run() {
        System.out.println("Requisição para a thread: " + Thread.currentThread().getName());
        System.out.println("Conexão ok!" + " proxy para: " + url);
        try {
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
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
