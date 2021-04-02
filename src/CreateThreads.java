import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.*;

public class CreateThreads {

    public void runThreads(List<String> links, int numberOfThreads, String path) throws InterruptedException {

        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(links.size());
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(numberOfThreads,numberOfThreads,1,TimeUnit.SECONDS,workQueue);


        for (String linksToDownload : links) {
            executorService.execute(new Download(linksToDownload, path));

            Thread.sleep(250);
        }


        ThreadFactory factory = r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        };

        ScheduledExecutorService downloadingProcess = Executors.newScheduledThreadPool(1, factory);
        downloadingProcess.scheduleWithFixedDelay(() -> {
            System.out.println("Files in queue " + workQueue.size());
            for (Object queueOfDownload : workQueue) {
                try {
                    Field url = queueOfDownload.getClass().getDeclaredField("URL");
                    url.setAccessible(true);
                    System.out.println(url.get(queueOfDownload));


                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            System.out.println();
        }, 0, 3, TimeUnit.SECONDS);


        executorService.shutdown();


    }
}
