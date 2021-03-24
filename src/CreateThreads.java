
import java.util.List;
import java.util.concurrent.*;

public class CreateThreads {

    public void runThreads(List<String> links, int numberOfThreads) throws InterruptedException {


        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        for (String linksToDownload : links) {
            executorService.execute(new Download(linksToDownload));
            Thread.sleep(200);
        }

        executorService.shutdown();


    }
}
