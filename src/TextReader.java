import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для чтения текстового файла
 * Created by Feraijo on 25.10.2016.
 */

class TextReader {
    private static TextReader instance;
    private List<String> words;
    private int speed = 300;
    private File file;
    private int count;
    private String[] lines;

    static TextReader getInstance(){
        if (instance == null)
            instance = new TextReader();

        return instance;
    }

    List<String> getWords(){
        try (BufferedReader reader = new BufferedReader
                (new InputStreamReader(new FileInputStream(file), "Cp1251"))){
            words = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines = line.split(" ");
                for (count = 0; count < lines.length; count++){
                    writeWords(lines[count]);
                }
            }
            return words;
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    int getDelay(){
        return 60000/speed;
    }

    //Метод для улучшения читаемости слов - длинные слова делит на короткие части,
    // отдельностоящие знаки препинания прикрепляет к следующим за ними словам.
    private void writeWords(String word) {
        Pattern p = Pattern.compile("\\p{Punct}+");
        Matcher m = p.matcher(word);
        if (m.matches() && count+1 < lines.length){
            writeWords(word.concat(" ").concat(lines[count + 1]));
            count++;
            return;
        }
        if ((float) word.length() / 6 >= 2f) {
            words.add(word.substring(0, 7).concat("-"));
            writeWords(("-").concat(word.substring(7)));
        } else {
            words.add(word);
        }
    }

    void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}

/*

 */