package org.sample;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1575287 on 29/04/2017.
 */
public final class FileMaker {
    private FileMaker (){
        //myStaticMember = 1;
    }
    //private int myStaticMember;
    public static void createFile(String filename){
        try {
            File file = new File(filename);

            if (file.createNewFile()){
                System.out.println("File is created!");
            }else{
                System.out.println("File already exists.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String filename) {
        Path file = Paths.get(filename);
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // populate
    public static void populate(String filename, int recordSize, int records){

        Path file = Paths.get(filename);

        List<String> content = new ArrayList<>();
        String currentLine = "";

        for(int i = 0; i < recordSize; i++){
            for(int j = 0; j < records; j++) {
                currentLine += (char) (65 + getRandom(25)); // cast ascii byte code into char
                if (currentLine.length() == recordSize) {
                    //System.out.println("adding");
                    content.add(currentLine);
                    currentLine = "";
                }
            }
        }

        try {
            Files.write(file, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandom(int n){
        return (int) Math.round(Math.random() * n);
    }
}
