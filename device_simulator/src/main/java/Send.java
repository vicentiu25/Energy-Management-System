import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Send {
    private static final AtomicInteger lineCounter = new AtomicInteger(0);

    public static void main(String[] argv) throws Exception {
        Integer[] deviceIds = getDeviceIds();
        ExecutorService executor = Executors.newFixedThreadPool(deviceIds.length);

        for (int deviceId : deviceIds) {
            executor.submit(new DeviceTask("sensor.csv", deviceId, lineCounter));
        }

        executor.shutdown();
    }

    public static Integer[] getDeviceIds() {
        Properties properties = new Properties();
        try (InputStream input = Send.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }
        String deviceIdsString = properties.getProperty("device.ids");
        String[] ids = deviceIdsString.split(",");
        return Arrays.stream(ids)
                .map(String::trim)
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
    }

}