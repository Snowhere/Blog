package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class FileTest {
    public static void main(String[] args) throws Exception {
        readLog();


    }

    public static void readLog() throws Exception {
        Set<String> urlSet = new HashSet<>();

        File log = new File("F:\\p\\protocol.txt");
        BufferedReader reader = new BufferedReader(new FileReader(log));
        do {
            String line = reader.readLine();
            String url = line.split(" ")[6];
            urlSet.add(url.split("\\?")[0]);
        } while (reader.read() != -1);

        for (String s : urlSet) {
            System.out.println(s);
        }
        System.out.println("one file ok");

    }

    public static void readLogs() throws Exception {
        Set<String> url = new HashSet<>();

        File file = new File("F:\\log");
        for (File log : file.listFiles()) {
            System.out.println(log.getName());
            BufferedReader reader = new BufferedReader(new FileReader(log));
            do {
                String line = reader.readLine();
                for (String s : line.split(" ")) {
                    for (String word : s.split("\\?")) {
                        if (word.contains("protocol")) {
                            url.add(word);
                        }
                    }
                }
            } while (reader.read() != -1);
            System.out.println("one file ok");
        }
        for (String s : url) {
            System.out.println(s);
        }
    }
}
