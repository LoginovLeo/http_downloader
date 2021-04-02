import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Download implements Runnable {


    private final String URL;
    private final String PATH;


    public Download(String url, String path) {
        this.URL = url;
        this.PATH = path;

    }


    @Override
    public void run() {

        try {
            String threadDescription = Thread.currentThread().getName();
            URL url = new URL(this.URL);
            String fullFileName = url.getFile();
            String fileName = fullFileName.substring(fullFileName.lastIndexOf("/") + 1);


            int countRepeats = 0;
            File fileToDownload;
            do {
                fileToDownload = new File(PATH + fileName);
                String tempFileName = fileName;

                if (fileToDownload.exists() && !fileToDownload.isDirectory()) {
                    int indexOfDot = fileName.lastIndexOf(".");
                    int indexOfB = tempFileName.lastIndexOf("B");

                    tempFileName = fileName.substring(0, indexOfB + 1) + "(" + countRepeats++ + ")" + fileName.substring(indexOfDot);
                    fileName = tempFileName;
                }
            } while (fileToDownload.exists() && !fileToDownload.isDirectory());

            BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream());
            FileOutputStream stream = new FileOutputStream(PATH + fileName);
            // System.out.println("Download started " + threadDescription + " file: " + fileName);

            byte[] bytes = new byte[100];
            int count;

            long timeStartDownload = System.currentTimeMillis() / 1000;
            ScheduledExecutorService downloadingProcess = Executors.newScheduledThreadPool(1);
            if (bufferedInputStream.read(bytes) != -1) {
                String finalFileName = fileName;
                downloadingProcess.scheduleWithFixedDelay(() ->
                        System.out.println("Downloading file: " + finalFileName + " in thread: " + threadDescription), 3, 3, TimeUnit.SECONDS);


            }

            while ((count = bufferedInputStream.read(bytes)) != -1) {
                stream.write(bytes, 0, count);

            }

            downloadingProcess.shutdown();


            long timeEndDownload = System.currentTimeMillis() / 1000;
            stream.close();


            FinishedDownload finishedDownload = new FinishedDownload(fileName);
            finishedDownload.run();

            //System.out.println("Download finished. Time of download " + fileName + " " + (timeEndDownload - timeStartDownload) + "sec.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
