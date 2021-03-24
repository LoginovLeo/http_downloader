import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class Download implements Runnable {


    private String url;


    public Download(String url) {
        this.url = url;


    }

    @Override
    public void run() {


        try {
            String threadDescription = Thread.currentThread().getName();
            URL url = new URL(this.url);
            String fullFileName = url.getFile();
            String fileName = fullFileName.substring(fullFileName.lastIndexOf("/") + 1);
            System.out.println("Download started " + threadDescription + " file: " +  fileName);
            int countRepeats = 0;
            File fileToDownload;

            do {
                    fileToDownload = new File(fileName);
                    String tempFileName = fileName;

                    if (fileToDownload.exists() && !fileToDownload.isDirectory()) {
                        int indexOf = fileName.lastIndexOf(".");
                        int indexOfB = tempFileName.lastIndexOf("B");
                        tempFileName = fileName.substring(0, indexOfB + 1) + "(" + countRepeats++ + ")" + fileName.substring(indexOf);
                        fileName = tempFileName;
                    }
                } while (fileToDownload.exists());

                BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream());
                FileOutputStream stream = new FileOutputStream(fileName);
//som
            int count = 0;
            byte[] b1 = new byte[100];
            long timeStart = System.currentTimeMillis()/1000;

            while ((count = bufferedInputStream.read(b1)) != -1) {
                stream.write(b1, 0, count);

            }



            long timeEnd = System.currentTimeMillis()/1000;
            System.out.println("Time of download " + fileName + " " + (timeEnd - timeStart)  + "sec.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
