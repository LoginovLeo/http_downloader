
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class CreateThreads {

    public void runThreads(List<String> links, int numberOfThreads, String path) throws InterruptedException {


        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        for (String linksToDownload : links) {
            executorService.execute(new Download(linksToDownload,path));
            Thread.sleep(200);

        }


        executorService.shutdown();



    }
}
