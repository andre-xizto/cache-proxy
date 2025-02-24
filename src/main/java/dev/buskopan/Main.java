package dev.buskopan;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {

        int port;
        URL url;

        try {
            port = Integer.parseInt(args[0]);
            url = new URI(args[1]).toURL();
        } catch (NumberFormatException e) {
            System.out.println("Porta inválida");
            return;
        } catch (MalformedURLException | URISyntaxException | IllegalArgumentException e) {
            System.out.println("URL inválida");
            return;
        }

        Server server = new Server(port);

        server.start(url);
    }
}