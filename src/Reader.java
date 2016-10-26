import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для чтения текстового файла
 * Created by Feraijo on 25.10.2016.
 */

public class Reader {
    private List<String> words;
    private int speed = 250;
    private File file;

    public Reader(){
        setFile("try.txt");
        try (BufferedReader reader = new BufferedReader
                (new InputStreamReader(new FileInputStream(file), "Cp1251"))){
            words = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                for (String word : line.split(" ")){
                    writeWords(word);
                }

                //объединить самые маленькие слова по одной букве по два или по три в одну ячейку
                //отдельностоящие знаки препинания туда же
                //добавить времени задержки при наличии определённого знака препинания в слове
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

    private void writeWords(String word) {
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