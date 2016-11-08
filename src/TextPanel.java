import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Feraijo on 07.11.2016.
 */

class TextPanel extends JPanel {
    private JTextField[] textFields;
    private Font bigFont = new Font("SansSerif", Font.BOLD, 40);
    private Border nothing = BorderFactory.createEmptyBorder();

    TextPanel(LayoutManager layoutManager) {
        super(layoutManager);
        setUpTextFields();
        for (JTextField tf : textFields)
            add(tf);
    }

    private void setUpTextFields(){
        textFields = new JTextField[3];
        for (int i = 0; i<textFields.length; i++){
            textFields[i] = new JTextField();
            textFields[i].setFont(bigFont);
            textFields[i].setEditable(false);
            textFields[i].setSize(new Dimension(100, 100));
            textFields[i].setBorder(nothing);
        }
        textFields[0].setHorizontalAlignment(SwingConstants.RIGHT);
        textFields[0].setColumns(4);
        textFields[1].setHorizontalAlignment(SwingConstants.CENTER);
        textFields[1].setForeground(new Color(110, 160, 0));
        textFields[1].setColumns(0);
        textFields[2].setHorizontalAlignment(SwingConstants.LEFT);
        textFields[2].setColumns(6);
    }

    JTextField[] getTextFields() {
        return textFields;
    }
    void setTextFields(String word, int number){
        textFields[number].setText(word);
    }
}
