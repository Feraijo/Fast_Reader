
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.List;

/**
 * Класс, отвечающий за окно
 * Created by Feraijo on 24.10.2016.
 */

class TextPanel extends JPanel implements ActionListener {
    private JFileChooser fc;
    private JButton goButton;
    private JButton selectFile;
    private JComboBox<Integer> speedList;
    private JTextField centerTextField, leftTextField, rightTextField;
    //private JPanel padding;

    private TextReader rdr = TextReader.getInstance();
    private boolean isPauseFlag = false;
    private final Timer timer = new Timer(rdr.getDelay(), getTimerAction());
    private int mainCount = 0;
    private List<String> words;
    private Font bigFont = new Font("SansSerif", Font.BOLD, 40);
    private AffineTransform affinetransform = new AffineTransform();
    private FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
    private Border nothing = BorderFactory.createEmptyBorder();
    private Border lineBorder = BorderFactory.createLineBorder(Color.black); //emptyborder
    private JPanel MainPanel;

    TextPanel() {
        rdr.setFile(new File("try.txt")); words = rdr.getWords(); // Заглушка для тестов

        setLayout(new BorderLayout());
        fc = new JFileChooser();

        Integer[] speedVariants = {150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700};
        speedList = new JComboBox<>(speedVariants);
        speedList.setSelectedIndex(3);
        speedList.addActionListener(this);
        JLabel speedLabel = new JLabel("Speed, wpm : ");
        speedLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        centerTextField = new JTextField();
        leftTextField = new JTextField();
        rightTextField = new JTextField();
        setTextField(centerTextField);
        setTextField(leftTextField);
        setTextField(rightTextField);

        GridBagConstraints c = new GridBagConstraints();
        centerTextField.setHorizontalAlignment(SwingConstants.LEFT);
        leftTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        rightTextField.setHorizontalAlignment(SwingConstants.LEFT);
        //centerTextField.setAlignmentX(CENTER_ALIGNMENT);
        //centerTextField.setAlignmentY(CENTER_ALIGNMENT);
        //centerTextField.setPreferredSize(new Dimension(20, 20));
        centerTextField.setMaximumSize(new Dimension(60, 200));
        //centerTextField.setBounds(100, 100, 20, 20);
        centerTextField.setForeground(new Color(110, 140, 0));


        goButton = new JButton("Play");
        goButton.addActionListener(this);
        selectFile = new JButton("Select file");
        selectFile.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridBagLayout());
        buttonPanel.setLayout(new GridLayout());

        //c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 0;
        textPanel.add(leftTextField, c);

        //c.weightx = 1.0;
        //c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 2;
        textPanel.add(rightTextField, c);
        //c.weightx = 0.5;
        c.gridx = 1;

        textPanel.add(centerTextField, c);

        buttonPanel.add(speedLabel);
        buttonPanel.add(speedList);
        buttonPanel.add(selectFile);
        buttonPanel.add(goButton);

        //add(padding, BorderLayout.LINE_START);
        add(textPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);


    }

    private void setTextField(JTextField tf) {

        tf.setFont(bigFont);
        tf.setEditable(false);
        tf.setSize(new Dimension(100, 20));
        tf.setBorder(lineBorder);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == selectFile) {//Выбор файла при нажатии кнопки выбора
            int returnVal = fc.showOpenDialog(TextPanel.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                rdr.setFile(fc.getSelectedFile());
            }
            words = rdr.getWords();
        } else if (e.getSource() == goButton) { //Начало чтения выбранного файла при нажатии кнопки Play
            if (rdr.getFile() == null) {
                JOptionPane.showMessageDialog(this, "You should choose the file to read from!"
                        , "Oops!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            //Таймер, выполняющий действие через определённую задержку
            if (!isPauseFlag) {
                timer.setDelay(650);
                timer.start();
                goButton.setText("Pause");
                isPauseFlag = true;
            } else {
                timer.stop();
                goButton.setText("Play");
                isPauseFlag = false;
            }
        } else if (e.getSource() == speedList) {
            rdr.setSpeed(speedList.getItemAt(speedList.getSelectedIndex()));
        }
    }

    private ActionListener getTimerAction() {
        return (event) -> {
            timer.setDelay(rdr.getDelay());
            if (mainCount > words.size() - 1)//Проверка на конец читаемого файла
                return;
            if (!(mainCount + 1 >= words.size()))
                setCurrentDelay(words.get(mainCount + 1));
            String[] word = getWordParts(words.get(mainCount++));

            leftTextField.setText(word[0]);
            centerTextField.setText(word[1]);
            rightTextField.setText(word[2]);
            this.revalidate();
        };
    }

    private String[] getWordParts(String s) {
        String[] result = new String[3];
        int wordCenterIndex = (int) (s.length() * 0.38);
        result[0] = s.substring(0, s.indexOf(s.charAt(wordCenterIndex)));
        result[1] = s.substring(s.indexOf(s.charAt(wordCenterIndex)), s.indexOf(s.charAt(wordCenterIndex)) + 1);
        result[2] = s.substring(s.indexOf(s.charAt(wordCenterIndex)) + 1, s.length());
        return result;
    }

    private int getDelta(String word) {

        String centralLetter, subWord;
        int wordCenterIndex = (int) (word.length() * 0.34);
        int i = word.indexOf(word.charAt(wordCenterIndex));

        centralLetter = word.substring(i, i + 1);
        int centralLetterWidth = (int) bigFont.getStringBounds(centralLetter, frc).getWidth();
        subWord = word.substring(0, i + 1);
        return (int) bigFont.getStringBounds(subWord, frc).getWidth();
    }

    private void setCurrentDelay(String word) { //метод для динамического регулирования задержки при знаках препинания
        if (word.isEmpty())
            return;
        if (word.contains(",") || word.contains(":"))
            timer.setDelay((int) (rdr.getDelay() * 1.7));
        if (word.contains(".") || word.contains("!") || word.contains("?") || word.contains(";"))
            timer.setDelay((int) (rdr.getDelay() * 2.4));
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("FastReader");
        frame.setSize(500, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(new TextPanel());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        MainPanel = new JPanel();
        MainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MainPanel;
    }
}
