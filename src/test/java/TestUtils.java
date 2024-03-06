import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtils {

    HttpClient client;

    public TestUtils() {
        client = HttpClient.newHttpClient();
    }

    public void saveListToFile(String fileName, List<String> list) {
        try {
            Path file = Paths.get("build/" + fileName + ".txt");
            Files.write(file, list, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveScreenshot(WebDriver driver, String fileName) {
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("build/" + fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void assertFileContents(String fileName, List<String> list) {
        Path path = Paths.get("build/" + fileName + ".txt");
        try {
            String fileContent = Files.readString(path);
            for (String listItem : list) {
                assertTrue(fileContent.contains(listItem));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void assertFileExists(String fileName) {
        Path path = Paths.get("build/" + fileName + ".png");
        assertTrue(Files.exists(path));
    }

    public String getDate(String timeZone) {
        String date = "";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://timeapi.io/api/Time/current/zone?timeZone=" + timeZone))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());
            date = jsonNode.get("date").asText();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.parse(date, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        return localDate.format(outputFormatter);
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}