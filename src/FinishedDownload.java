import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class FinishedDownload implements Runnable {


    String fileName;

    public FinishedDownload() {
    }

    public FinishedDownload(String fileName) {
        this.fileName = fileName;
    }

    ThreadFactory factory = new ThreadFactory() {
        @Override
        public Thread newThread(@NotNull Runnable r) {
            Thread tread = new Thread(r);
            tread.setDaemon(true);
            return tread;
        }
    };
    ScheduledExecutorService downloadingProcess = Executors.newScheduledThreadPool(1, factory);


    @Override
    public void run() {


        downloadingProcess.scheduleWithFixedDelay(() -> {
            System.out.println("Finished: " + fileName);


        }, 1, 3, TimeUnit.SECONDS);


    }


}
