import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

/**
 * Класс, отвечающий за главное окно
 * Created by Feraijo on 24.10.2016.
 */

class TextPanel extends JPanel implements ActionListener {
    private JFileChooser fc;
    private JButton[] buttons;
    private Icon[] icons;
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

    private TextPanel(LayoutManager layout) {
        super(layout);

        /*rdr.setFile(new File("try.txt")); // Заглушка для тестов
        rdr.readFile(); // Заглушка для тестов
        words = rdr.getWholeBookText(); // Заглушка для тестов*/

        fc = new JFileChooser();
        JLabel speedLabel = new JLabel("Speed, wpm : ");

        setSpeed(speedLabel);

        setTextFields();
        setIcons();

        JPanel buttonPanel = new JPanel();
        JPanel textPanel = new JPanel();
        JPanel textControl = new JPanel();

        textPanel.setLayout(new GridBagLayout());
        buttonPanel.setLayout(new GridLayout());

        buttons = new JButton[6];
        for (int i = 0; i<buttons.length; i++ ){
            buttons[i] = new JButton();
            buttons[i].addActionListener(this);
            buttons[i].setIcon(icons[i]);
            textControl.add(buttons[i]);
        }
        selectFile = new JButton("Select file:");
        selectFile.addActionListener(this);


        for (JTextField tf : textFields)
            textPanel.add(tf);

        buttonPanel.add(speedLabel);
        buttonPanel.add(speedList);
        buttonPanel.add(selectFile);


        add(textPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);
        add(textControl, BorderLayout.SOUTH);
    }

    private void setIcons(){
        icons = new Icon[6];
        icons[0] = createImageIcon("icons/fr.png", "Fast rewind");
        icons[1] = createImageIcon("icons/r.png", "Rewind");
        icons[2] = createImageIcon("icons/stop.png", "Stop");
        icons[3] = createImageIcon("icons/p-p.png", "Play/Pause");
        icons[4] = createImageIcon("icons/f.png", "Forward");
        icons[5] = createImageIcon("icons/ff.png", "Fast forward");
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

    private void setTextFields(){
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
                resetAll();
                rdr.setFile(fc.getSelectedFile());
                rdr.readFile();
                words = rdr.getWholeBookText();
            }

        } else if (e.getSource() == speedList) {
            rdr.setSpeed(speedList.getItemAt(speedList.getSelectedIndex()));

        } else if (e.getSource() == buttons[3]) { //Начало чтения выбранного файла при нажатии кнопки Play/Pause
            if (rdr.getFile() == null) {
                JOptionPane.showMessageDialog(this, "You should choose the file to read from!"
                        , "Oops!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            //Таймер, выполняющий действие через определённую задержку
            if (!isPauseFlag) {
                timer.setDelay(650);
                timer.start();
                isPauseFlag = true;
            } else {
                timer.stop();
                isPauseFlag = false;
            }
        } else if (e.getSource() == buttons[0]){ //10 слов назад
            if (mainCount>10)
                mainCount -= 10;
        } else if (e.getSource() == buttons[1]){ // 5 слов назад
            if (mainCount>5)
                mainCount -= 5;
        } else if (e.getSource() == buttons[2]){ // Полная остановка, сброс текущего файла
            resetAll();
        } else if (e.getSource() == buttons[4]){ // 5 слов вперёд
            if (mainCount + 5 < words.size())
                mainCount += 5;
        } else if (e.getSource() == buttons[5]){ //10 слов вперёд
            if (mainCount + 10 < words.size())
                mainCount += 10;
        }
    }

    private void resetAll() {
        timer.stop();
        isPauseFlag = false;
        rdr.setFile(null);
        words = null;
        mainCount = 0;
        for (JTextField tf : textFields){
            tf.setText("");
        }
    }

    private ActionListener getTimerAction() {
        return (event) -> {
            timer.setDelay(rdr.getDelay());
            if (mainCount > words.size() - 1)//Проверка на конец читаемого файла
                return;
            if (mainCount + 1 < words.size())
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
        if (s.length()>0) {
            int wordCenterIndex = (int) (s.length() * 0.35);
            result[0] = s.substring(0, wordCenterIndex);
            result[1] = s.substring(wordCenterIndex, wordCenterIndex + 1);
            result[2] = s.substring(wordCenterIndex + 1, s.length());
        }
        return result;
    }

    private void setCurrentDelay(String word) { //метод для динамического регулирования задержки при знаках препинания
        if (word.isEmpty())
            return;
        if (word.contains(",") || word.contains(":"))
            timer.setDelay((int) (rdr.getDelay() * 2.1));
        if (word.contains(".") || word.contains("!") || word.contains("?") || word.contains(";"))
            timer.setDelay((int) (rdr.getDelay() * 3.1));
    }

    private static void createAndShowGUI() {
        int width = 600, height = 200;
        JFrame frame = new JFrame("FastReader");
        frame.setSize(new Dimension(width, height));
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
