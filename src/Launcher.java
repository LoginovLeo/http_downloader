import java.io.IOException;
import java.util.List;


public class Launcher {

    private static final String CONFIG_FILE = "Config.txt";



    public static void main(String[] args) throws IOException, InterruptedException {
        ConfigReader readerConfig = new ConfigReader();
        readerConfig.readConfig(CONFIG_FILE);
        CreateThreads test = new CreateThreads();
        test.runThreads(readerConfig.getLinksToDownload(),Integer.parseInt(readerConfig.getNumberOfThreads()));



    }
}
