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

    public String getFileName() {
        return fileName;
    }

    public Download(String url, String path) {
        this.URL = url;
        this.PATH = path;
    }

    @Override
    public void run() {

        try {
            URL url = new URL(this.URL);
            fileName = url.getFile().substring(url.getFile().lastIndexOf("/") + 1);
            int countRepeats = 0;
            File fileToDownload;
            String constFilename = fileName.substring(0, fileName.lastIndexOf("."));
            do {
                fileToDownload = new File(PATH + fileName);
                if (fileToDownload.exists() && !fileToDownload.isDirectory()) {
                    fileName = constFilename + "(" + countRepeats++ + ")" + fileName.substring(fileName.lastIndexOf("."));
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
