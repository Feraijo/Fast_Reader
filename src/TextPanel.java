import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

/**
 * Класс, отвечающий за главное окно
 * Created by Feraijo on 24.10.2016.
 */

class TextPanel extends JPanel implements ActionListener {
    private JFileChooser fc;
    private JButton goButton;
    private JButton selectFile;
    private JComboBox<Integer> speedList;
    private JTextField[] textFields;

    private TextReader rdr = TextReader.getInstance();
    private boolean isPauseFlag = false;
    private final Timer timer = new Timer(rdr.getDelay(), getTimerAction());
    private int mainCount = 0;
    private List<String> words;
    private Font bigFont = new Font("SansSerif", Font.BOLD, 40);
    private Border nothing = BorderFactory.createEmptyBorder();
    //private Border lineBorder = BorderFactory.createLineBorder(Color.black); //emptyborder

    TextPanel(LayoutManager layout) {
        super(layout);
        rdr.setFile(new File("try.txt"));
        words = rdr.getWords(); // Заглушка для тестов

        fc = new JFileChooser();
        JLabel speedLabel = new JLabel("Speed, wpm : ");

        setSpeed(speedLabel);

        setTextFields();

        goButton = new JButton("Play");
        goButton.addActionListener(this);
        selectFile = new JButton("Select file");
        selectFile.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        JPanel textPanel = new JPanel();

        textPanel.setLayout(new GridBagLayout());
        buttonPanel.setLayout(new GridLayout());

        for (int i = 0; i<textFields.length; i++){
            textPanel.add(textFields[i]);
        }

        buttonPanel.add(speedLabel);
        buttonPanel.add(speedList);
        buttonPanel.add(selectFile);
        buttonPanel.add(goButton);

        add(textPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setTextFields(){
        textFields = new JTextField[3];
        for (int i = 0; i<textFields.length; i++){
            textFields[i] = new JTextField(7);
            textFields[i].setFont(bigFont);
            textFields[i].setEditable(false);
            textFields[i].setSize(new Dimension(100, 100));
            textFields[i].setBorder(nothing);
        }
        textFields[0].setHorizontalAlignment(SwingConstants.RIGHT);
        textFields[1].setHorizontalAlignment(SwingConstants.CENTER);
        textFields[1].setForeground(new Color(110, 160, 0));
        textFields[1].setColumns(0);
        textFields[2].setHorizontalAlignment(SwingConstants.LEFT);
    }

    private void setSpeed(JLabel speedLabel) {
        Integer[] speedVariants = {150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700};
        speedList = new JComboBox<>(speedVariants);
        speedList.setSelectedIndex(3);
        speedList.addActionListener(this);
        speedLabel.setHorizontalAlignment(SwingConstants.RIGHT);
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
            for (int i = 0; i<textFields.length; i++){
                textFields[i].setText(word[i]);
            }
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
        frame.setPreferredSize(new Dimension(800, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(new TextPanel(new BorderLayout()));
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
