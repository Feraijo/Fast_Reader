import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Feraijo on 07.11.2016.
 */

class TopButtons extends JPanel {
    private final Integer[] speedVariants = {150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700};
    private JFileChooser fileSelector;
    private JButton selectFileButton;
    private JButton saveButton;
    private JButton loadButton;
    private JComboBox<Integer> speedList;
    private List<JButton> list;


    TopButtons() {
        fileSelector = new JFileChooser();


        JLabel speedLabel = new JLabel("Speed, wpm : ");
        speedLabel.setHorizontalAlignment(SwingConstants.RIGHT);


        speedList = new JComboBox<>(speedVariants);
        speedList.setSelectedIndex(3);

        selectFileButton = new JButton("Select file:");

        saveButton = new JButton("Save");
        loadButton = new JButton("Load");

        add(speedLabel);
        add(speedList);
        add(selectFileButton);
        add(saveButton);
        add(loadButton);

    }

    public List<JButton> getTopComp() {
        return list;
    }

    JButton getSelectFileButton() {
        return selectFileButton;
    }

    JFileChooser getFileSelector() {
        return fileSelector;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JComboBox<Integer> getSpeedList() {
        return speedList;
    }
}
