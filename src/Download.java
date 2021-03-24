import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;


public class Download implements Runnable {


    private final String URL;


    public Download(String url) {
        this.URL = url;


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
                fileToDownload = new File(fileName);
                String tempFileName = fileName;

                if (fileToDownload.exists() && !fileToDownload.isDirectory()) {
                    int indexOfDot = fileName.lastIndexOf(".");
                    int indexOfB = tempFileName.lastIndexOf("B");
                    tempFileName = fileName.substring(0, indexOfB + 1) + "(" + countRepeats++ + ")" + fileName.substring(indexOfDot);
                    fileName = tempFileName;
                }
            } while (fileToDownload.exists());

            BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream());
            FileOutputStream stream = new FileOutputStream(fileName);
            System.out.println("Download started " + threadDescription + " file: " + fileName);

            int count;
            byte[] bytes = new byte[100];
            long timeStartDownload = System.currentTimeMillis() / 1000;
            while ((count = bufferedInputStream.read(bytes)) != -1) {
                stream.write(bytes, 0, count);
            }
            long timeEndDownload = System.currentTimeMillis() / 1000;
            stream.close();
            System.out.println("Download finished. Time of download " + fileName + " " + (timeEndDownload - timeStartDownload) + "sec.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
