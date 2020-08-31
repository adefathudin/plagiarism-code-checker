package Proses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author zero
 */
public class sort {

    public static void sortFile(String namaFile) throws IOException {
        FileReader fileReader = new FileReader(namaFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        Collections.sort(lines, Collator.getInstance());
        FileWriter writer = new FileWriter(namaFile +".sort");
        int no = 0;
        for (String str : lines) {
            writer.write(str + "\r\n");
        }
        writer.close();
    }
}
