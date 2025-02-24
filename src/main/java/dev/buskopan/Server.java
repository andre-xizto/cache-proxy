package dev.buskopan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.*;

public class Server {
    private ServerSocket socket;
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private final int port;
    private final ConcurrentHashMap<String, String> cacheMap = new ConcurrentHashMap<>();
    private final ExecutorService threadPool;

    public Server(int port) {
        this.port = port;
        threadPool = Executors.newCachedThreadPool();
    }

    public void start(URL url) {
        System.out.println("Rodando server na porta " + port);
        try {
            socket = new ServerSocket(port);
            while (true) {
                threadPool.submit(new ServerThread(socket.accept(), url, cacheMap));
            }
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
