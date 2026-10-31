package practice;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class SolarEntropyGenerator {

    private static final String NOAA_API_URL = "https://services.swpc.noaa.gov/json/goes/primary/xrays-1-day.json";

    public String fetchSolarData() throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(NOAA_API_URL))
                .header("User-Agent", "SolarEntropyClient/1.0")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP error status: " + response.statusCode());
        }

        return response.body();
    }

    public byte[] generateSHA256Bytes(String input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(input.getBytes(StandardCharsets.UTF_8));
    }
}