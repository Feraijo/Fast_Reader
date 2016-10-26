import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Класс, отвечающий за кнопку и её действие
 * Created by Feraijo isPause 25.10.2016.
 */

class GoButton extends JButton {
    private Reader rdr = new Reader();
    private int mainCount = 0;
    private boolean isPause = false;

    private List<String> words = rdr.getWords();
    private int wordsSize = rdr.getWords().size();

    GoButton(TextField textField) {
        setText("Play");
        ActionListener readTextAction = (e) -> {
            if (mainCount > wordsSize - 1)
                return;
            textField.setText(words.get(mainCount++));
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
}

/*



 */