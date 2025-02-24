package dev.buskopan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ClientProxy {
    private final URL url;

    public ClientProxy(URL url) {
        this.url = url;
    }

    public String call() {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            ) {
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
