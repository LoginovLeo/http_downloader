import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class CreateThreads {


    private final List<Download> filesToDownload = new ArrayList<>();


    public void runThreads(List<String> links, int numberOfThreads, String path) throws InterruptedException {

        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(links.size());
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 5, TimeUnit.SECONDS, workQueue);

        for (String linksToDownload : links) {
            filesToDownload.add(new Download(linksToDownload, path));
        }

        for (Download download : filesToDownload) {
            executorService.execute(download);
            Thread.sleep(250);
        }


        ThreadFactory factory = r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        };

        ScheduledExecutorService queueInformation = Executors.newScheduledThreadPool(1, factory);
        queueInformation.scheduleWithFixedDelay(() -> {

            System.out.println("Downloading files: ");
            //basic getter
            for (Download filesInProcess : filesToDownload) {
                if (!filesInProcess.isFinished()) {
                    String name = filesInProcess.getFileName();
                    if (name != null) {
                        System.out.println(name);
                    }
                }
            }
            System.out.println();

            System.out.println("Files in queue: " + workQueue.size());

            //Reflection for example
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

            System.out.println("Files finished download: ");
            for (Download filesFinishedDownload : filesToDownload) {
                if (filesFinishedDownload.isFinished()) {
                    try {
                        Field name = filesFinishedDownload.getClass().getDeclaredField("fileName");
                        name.setAccessible(true);
                        System.out.println(name.get(filesFinishedDownload));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
            System.out.println("___________________________________________");

        }, 0, 3, TimeUnit.SECONDS);


        executorService.shutdown();

    }

}
