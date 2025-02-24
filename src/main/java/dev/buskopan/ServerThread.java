package dev.buskopan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Map;

public class ServerThread implements Runnable {

    private final Socket client;
    private final URL url;
    private final Map<String, String> cacheMap;

    public ServerThread(Socket client, URL url, Map<String,String> cache) {
        this.url = url;
        this.client = client;
        this.cacheMap = cache;
    }

    @Override
    public void run() {
        System.out.println("Requisição para a thread: " + Thread.currentThread().getName());
        System.out.println("Conexão ok!" + " proxy para: " + url);
        try (
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))
        ){

            if(cacheMap.containsKey(url.getHost() + url.getPath())) {
                sendResponse(out,cacheMap.get(url.getHost() + url.getPath()),true);
            }

            String s = in.readLine();
            System.out.println(s);

            String response = cacheMap.computeIfAbsent(url.getHost() + url.getPath(), v -> new ClientProxy(url).call());

            sendResponse(out, response,false);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private static void sendResponse(PrintWriter out, String response, boolean cached) {
        String valueCached = cached ? "HIT" : "MISS";

        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: application/json");
        out.println("Content-Length: " + response.length());
        out.println("X-Cache: " + valueCached);
        out.println();
        out.println(response);
    }
}
