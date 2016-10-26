import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс, отвечающий за кнопку и её действие
 * Created by Feraijo isPause 25.10.2016.
 */

class GoButton extends JButton {
    private Reader rdr = new Reader();
    private int mainCount = 0, bigWordSize = 0, bwCount = 1;
    private boolean wordIsBig = false, isPause = false;

    private List<String> words = rdr.getWords(), bigWord;
    private int wordsSize = rdr.getWords().size();

    GoButton(TextField textField) {
        setText("Play");
        ActionListener readTextAction = (e) -> {
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
        };
        Timer timer = new Timer(rdr.getDelay(), readTextAction);
        addActionListener((e) -> {
            if (!isPause) {
                timer.start();
                setText("Pause");
                isPause = true;
            } else {
                timer.stop();
                setText("Play");
                isPause = false;
            }
        });
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
}

/*

 */