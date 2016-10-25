import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Класс, отвечающий за кнопку и её действие
 * Created by Feraijo on 25.10.2016.
 */

class GoButton extends JButton{
    private Reader rdr = new Reader();
    private int i = 0;
    boolean on = false;

    GoButton(TextField textField) {
        setText("Play");
        ActionListener readTextAction = (e) -> {
            if (i > rdr.getWords().size() - 1) {
                return;
            }
            textField.setText(rdr.getWords().get(i));
            i++;
        };
        Timer timer = new Timer(rdr.getDelay(), readTextAction);
        addActionListener((e)->{
            if (!on) {
                timer.start();
                setText("Pause");
                on = true;
            } else {
                timer.stop();
                setText("Play");
                on = false;
            }});
    }
}
