import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс для чтения текстового файла
 * Created by Feraijo on 25.10.2016.
 */

public class Reader {
    private List<String> words;
    private int speed = 250;

    public Reader(){
        File file = new File("try.txt");
        try (BufferedReader reader = new BufferedReader
                (new InputStreamReader(new FileInputStream(file), "Cp1251"))){
            List<String> text = new ArrayList<>();
            words = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                text.add(line);
                for (String s : text) {
                    words.addAll(Arrays.asList(s.split(" ")));
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    List<String> getWords(){
        return words;
    }

    int getDelay(){
        return 60000/speed;
    }

    void setSpeed(int speed) {
        this.speed = speed;
    }
}
