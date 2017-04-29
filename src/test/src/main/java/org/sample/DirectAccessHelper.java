package org.sample;

/**
 * Created by c1575287 on 29/04/2017.
 */
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;



/**
 * Created by Slime on 25/04/2017.
 */
public class DirectAccessHelper {
    private Path path;
    private int recordSize;
    private String fileName;

    DirectAccessHelper(String fileName, int recordSize){
        this.fileName = fileName;
        this.path = Paths.get(this.fileName);
        this.recordSize = recordSize;
    }

    public void read(int recordNumber) {
        ByteBuffer buffer = ByteBuffer.allocate(this.recordSize);
        try (FileChannel fc = (FileChannel.open(this.path, StandardOpenOption.READ, StandardOpenOption.WRITE))) {

            String encoding = System.getProperty("file.encoding");

            int nread;
            fc.position(recordNumber * this.recordSize);
            do {// read a byte to the buffer while there is space in the buffer and the file has not ended
                nread = fc.read(buffer);
            } while (nread != -1 && buffer.hasRemaining());
            buffer.rewind();

            System.out.println((String.valueOf(Charset.forName(encoding).decode(buffer))));
        } catch (IOException x) {
            System.out.println("I/O Exception: " + x);
        }
    }

    public void write(String content, int recordNumber){
        byte data[] = content.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(data);

        try (FileChannel fc = (FileChannel.open(this.path, StandardOpenOption.READ, StandardOpenOption.WRITE))) {
            fc.position(recordNumber * this.recordSize);
            while(buffer.hasRemaining()){
                fc.write(buffer);
            }
            buffer.rewind();
        } catch (IOException x) {
            System.out.println("I/O Exception: " + x);
        }
    }

    public Long getNumberOfRecordsInFile(){
        FileChannel fc = null;
        try {
            fc = (FileChannel.open(this.path, StandardOpenOption.READ, StandardOpenOption.WRITE));
            return fc.size() / this.recordSize;
        } catch (IOException x) {
            System.out.println("I/O Exception: " + x);

        }
        return null;
    }


}
