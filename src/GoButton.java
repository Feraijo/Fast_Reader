import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Класс, отвечающий за кнопку и её действие
 * Created by Feraijo 25.10.2016.
 */

class GoButton extends JButton {
    private Reader rdr = new Reader();
    private int mainCount = 0;
    private boolean isPause = false;
    private Timer timer;
    private List<String> words = rdr.getWords();
    private int wordsSize = rdr.getWords().size();

    GoButton(TextField textField) {

        setText("Play");
        ActionListener readTextAction = (e) -> {
            timer.setDelay(rdr.getDelay());
            if (mainCount > wordsSize - 1)
                return;
            if (!(mainCount+1>=words.size())) //Проверка на конец читаемого файла
                setCurrentDelay(words.get(mainCount+1));
            textField.setText(words.get(mainCount++));
        };

        //Таймер, выполняющий действие через определённую задержку
        timer = new Timer(rdr.getDelay(), readTextAction);
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

    //Метод для динамического изменения задержки при наличии знаков препинания.
    private void setCurrentDelay (String word){
        if (word.isEmpty())
            return;
        if (word.contains(",") || word.contains(":"))
            timer.setDelay((int)(rdr.getDelay()*1.45));
        if (word.contains(".") ||word.contains("!") || word.contains("?") || word.contains(";"))
            timer.setDelay((int)(rdr.getDelay()*1.8));
    }
}

/*



 */