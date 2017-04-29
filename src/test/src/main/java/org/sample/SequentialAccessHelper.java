package test.src.main.java.org.sample;

/**
 * Created by c1575287 on 29/04/2017.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;



/**
 * Created by Slime on 25/04/2017.
 */
public class SequentialAccessHelper {
    private String fileName;
    private Path file;
    private int recordSize;

    SequentialAccessHelper(String fileName, int recordSize){
        this.fileName = fileName;
        this.file = Paths.get(this.fileName);
        this.recordSize = recordSize;
    }

    public String read(int recordLine){
        Charset charset = Charset.forName("UTF-8");
        String line = null;
        // System.out.println(this.file.getI);
        try (BufferedReader reader = Files.newBufferedReader(this.file, charset)) {
            int currentRecordLine = 0;
            while ((line = reader.readLine()) != null) {
                //System.out.println("current line" + currentRecordLine);
                if(currentRecordLine == recordLine){
                    break;
                }
                currentRecordLine++;
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return line;
    }

    public void write(int record, String content) throws IOException {
        Charset charset = Charset.forName("UTF-8");
        BufferedWriter writer = null;

        try {
            writer = Files.newBufferedWriter(this.file, charset);
            writer.write(content, record * this.recordSize, (record * this.recordSize) + content.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        } finally {
            writer.close();
        }
    }
}
