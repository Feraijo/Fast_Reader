import javax.swing.*;
import java.awt.*;

/**
 * Класс, отвечающий за окно
 * Created by Feraijo on 24.10.2016.
 */

class TextPanel extends JPanel {

    TextPanel(){
        setLayout(new BorderLayout());

        TextField textField = new TextField();
        add(textField, BorderLayout.CENTER);

        GoButton goButton = new GoButton("GO!", textField);
        add(goButton, BorderLayout.SOUTH);
    }
}
