package com.virtualoctopus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main2 {
    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        for (int i = 0; i < 100000000; i++) {
            executorService.submit(() -> {
                try {
                    String endpointUrl = "http://localhost:9090/test-get-mapping";

                    // Create URL object
                    URL url = new URL(endpointUrl);

                    // Open a connection using HttpURLConnection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    // Set request method to GET
                    connection.setRequestMethod("GET");

                    // Read response
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

//            // Print response
//            System.out.println("Response from server:");
//            System.out.println(response.toString());

                    // Disconnect the connection
                    connection.disconnect();
                } catch (IOException e) {}
            });
        }
    }
}
