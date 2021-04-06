import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;


public class Download implements Runnable {

    private final String URL;
    private final String PATH;
    private String fileName;
    private boolean finished = false;

    public boolean isFinished() {
        return finished;
    }


    public Download(String url, String path) {
        this.URL = url;
        this.PATH = path;

    }

    @Override
    public void run() {

        try {
            URL url = new URL(this.URL);
            String fullFileName = url.getFile();
            fileName = fullFileName.substring(fullFileName.lastIndexOf("/") + 1);
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
            byte[] bytes = new byte[100];
            int count;
            while ((count = bufferedInputStream.read(bytes)) != -1) {
                stream.write(bytes, 0, count);
            }
            stream.close();
            finished = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
