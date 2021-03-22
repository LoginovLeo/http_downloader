import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    List<String> list = new ArrayList();
    public void nioReadWithsStream(String name) throws IOException {
        try (InputStream in = Files.newInputStream(Paths.get(name))) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String s;
            while ((s = reader.readLine())!= null) {
                    if(s.equals("[download]")){
                        s = reader.readLine();
                        System.out.println(s);
                    }
            }
            }
        }
    }
