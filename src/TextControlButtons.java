import javax.swing.*;
import java.net.URL;

/**
 * Created by Feraijo on 07.11.2016.
 */

class TextControlButtons extends JPanel {
    private JButton[] buttons;

    TextControlButtons() {
        buttons = new JButton[6];
        for (int i = 0; i<buttons.length; i++){
            buttons[i] = new JButton();
            //buttons[i].addActionListener(this);
            buttons[i].setIcon(getIcons(i));
            add(buttons[i]);
        }
    }

    private Icon getIcons(int i){
        Icon[] icons = new Icon[6];
        icons[0] = createImageIcon("icons/fr.png", "Fast rewind");
        icons[1] = createImageIcon("icons/r.png", "Rewind");
        icons[2] = createImageIcon("icons/stop.png", "Stop");
        icons[3] = createImageIcon("icons/p-p.png", "Play/Pause");
        icons[4] = createImageIcon("icons/f.png", "Forward");
        icons[5] = createImageIcon("icons/ff.png", "Fast forward");
        return icons[i];
    }

    private ImageIcon createImageIcon(String path,
                                      String description) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public JButton[] getButtons() {
        return buttons;
    }
}
