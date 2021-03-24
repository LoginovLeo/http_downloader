import java.io.IOException;



public class Launcher {

    private static final String CONFIG_FILE = "Config.txt";



    public static void main(String[] args) throws IOException, InterruptedException {
        ConfigReader readerConfig = new ConfigReader();
        readerConfig.readConfig(CONFIG_FILE);
        CreateThreads threadsDownload = new CreateThreads();
        threadsDownload.runThreads(readerConfig.getLinksToDownload(),Integer.parseInt(readerConfig.getNumberOfThreads()));



    }
}
