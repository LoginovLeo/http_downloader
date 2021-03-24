import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ConfigReader {

    private String path;
    private String numberOfThreads;
    private final List<String> linksToDownload = new ArrayList<>();

    public void readConfig(String name) throws IOException {
        try (InputStream in = Files.newInputStream(Paths.get(name))) {
            BufferedReader readerConfigFile = new BufferedReader(new InputStreamReader(in));
            String searchDownloadParameters;
            while ((searchDownloadParameters = readerConfigFile.readLine()) != null) {
                if (searchDownloadParameters.equals("[download]")) {
                    path = readerConfigFile.readLine();
                }
                if (searchDownloadParameters.equals("[threads]")) {
                    numberOfThreads = readerConfigFile.readLine();
                }
                if (searchDownloadParameters.equals("[links]")) {
                    while ((searchDownloadParameters = readerConfigFile.readLine()) != null) {
                        linksToDownload.add(searchDownloadParameters);
                    }
                }
            }
        }
    }

    public String getPath() {
        return path;
    }

    public String getNumberOfThreads() {
        return numberOfThreads;
    }

    public List<String> getLinksToDownload() {
        return linksToDownload;
    }
}
