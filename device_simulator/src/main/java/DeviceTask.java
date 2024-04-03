import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class DeviceTask implements Runnable{

    private final String csvFile;
    private final int deviceId;
    private final static String QUEUE_NAME = "device_queue";
    private final AtomicInteger lineCounter;

    DeviceTask(String csvFile, int deviceId, AtomicInteger lineCounter) {
        this.csvFile = csvFile;
        this.deviceId = deviceId;
        this.lineCounter = lineCounter;
    }

    @Override
    public void run() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5673);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            LocalDateTime currentTime = LocalDateTime.now();
            String line;
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                int currentLine;
                while ((currentLine = lineCounter.getAndIncrement()) < Integer.MAX_VALUE) {
                    line = readLine(br, currentLine);
                    if (line == null) {
                        break; // No more lines to read
                    }

                    try {
                        float floatValue = Float.parseFloat(line);
                        currentTime = currentTime.plusMinutes(10);
                        String message = "{\n" +
                                "  \"timestamp\": \"" + getCurrentTime(currentTime) + "\",\n" +
                                "  \"device_id\": " + deviceId + ",\n" +
                                "  \"measurement_value\": " + floatValue + "\n" +
                                "}";
                        channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                        System.out.println(" [Device " + deviceId + "] Sent '" + message + "'");
                        Thread.sleep(2000);
                    } catch (NumberFormatException | InterruptedException e) {
                        System.err.println("Error in Device " + deviceId + ": " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    private String readLine(BufferedReader reader, int lineNumber) throws IOException {
        synchronized (reader) {
            for (int i = 0; i < lineNumber; i++) {
                if (reader.readLine() == null) {
                    return null;
                }
            }
            return reader.readLine();
        }
    }

    private String getCurrentTime(LocalDateTime currentTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentTime.format(formatter);
    }
}
