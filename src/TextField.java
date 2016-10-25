import javax.swing.*;
import java.awt.*;

/**
 * Класс, отвечающий за текстовое поле
 * Created by Feraijo on 25.10.2016.
 */

class TextField extends JTextField {

    TextField() {
        Font bigFont = new Font("SansSerif", Font.BOLD, 40);
        setFont(bigFont);
        setHorizontalAlignment(JTextField.CENTER);
        setPreferredSize(new Dimension(400, 100));
    }
}
