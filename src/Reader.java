import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для чтения текстового файла
 * Created by Feraijo on 25.10.2016.
 */

public class Reader {
    private List<String> words;
    private int speed = 300;
    private File file;
    private int count, length;
    private String[] lines;

    public Reader(){
        setFile("try.txt"); //Заглушка для выбора файла для чтения.
        try (BufferedReader reader = new BufferedReader
                (new InputStreamReader(new FileInputStream(file), "Cp1251"))){
            words = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines = line.split(" ");
                length = lines.length;
                for (count = 0; count < length; count++){
                    writeWords(lines[count]);
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

    //Метод для улучшения читаемости слов - длинные слова делит на короткие части,
    // отдельностоящие знаки препинания прикрепляет к следующим за ними словам.
    private void writeWords(String word) {
        Pattern p = Pattern.compile("\\p{Punct}+");
        Matcher m = p.matcher(word);
        if (m.matches()){
            writeWords(word.concat(" ").concat(lines[count+1]));
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

    public void setFile(String name) {
        this.file = new File(name);
    }
}

/*
переносы в гоБуттон
        if (mainCount > wordsSize - 1)
                return;
            String wordToShow = words.get(mainCount);
            if (wordIsBig) {
                textField.setText(bigWord.get(bwCount++));
                if (bwCount == bigWordSize) {
                    wordIsBig = false;
                    bigWord = new LinkedList<>();
                    bwCount = 1;
                    mainCount++;
                }
            } else {
                if ((float) wordToShow.length() / 6 >= 2f) {
                    bigWord = new LinkedList<>();
                    readWords(wordToShow);
                    textField.setText(bigWord.get(0));
                    wordIsBig = true;
                } else {
                    textField.setText(wordToShow);
                    mainCount++;
                }
            }
private void readWords(String word) {
        if ((float) word.length() / 6 >= 2f) {
            bigWord.add(word.substring(0, 6).concat("-"));
            readWords(("-").concat(word.substring(6)));
        } else {
            bigWord.add(word);
            bigWordSize = bigWord.size();
        }
    }
 */