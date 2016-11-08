import javax.swing.*;
import java.awt.*;

/**
 * Класс, отвечающий за главное окно
 * Created by Feraijo on 24.10.2016.
 */

class MainPanel extends JPanel {
    private static MainPanel instance;

    private TopButtons topButtons;
    private TextPanel textPanel;
    private TextControlButtons textControls;

    static MainPanel getInstance(){
        if (instance == null)
            instance = new MainPanel(new BorderLayout());
        return instance;
    }

    MainPanel(LayoutManager layout) {
        super(layout);

        topButtons = new TopButtons();
        textPanel = new TextPanel(new GridBagLayout());
        textControls = new TextControlButtons();
        new MainAction(topButtons, textPanel, textControls);
        add(textPanel, BorderLayout.CENTER);
        add(topButtons, BorderLayout.NORTH);
        add(textControls, BorderLayout.SOUTH);
    }

    public TopButtons getTopButtons() {
        return topButtons;
    }

    public TextPanel getTextPanel() {
        return textPanel;
    }

    public TextControlButtons getTextControls() {
        return textControls;
    }
}
