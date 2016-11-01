
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
    private JTextField textField;
    private JPanel padding;

    private TextReader rdr = TextReader.getInstance();
    private boolean isPauseFlag = false;
    private final Timer timer = new Timer(rdr.getDelay(), getTimerAction());
    private int mainCount = 0;
    private List<String> words;
    private Font bigFont;
    private AffineTransform affinetransform = new AffineTransform();
    private FontRenderContext frc = new FontRenderContext(affinetransform,true,true);

    TextPanel(){
        rdr.setFile(new File("try.txt")); words = rdr.getWords(); // Заглушка для тестов

        padding = new JPanel();
        Border lineBorder = BorderFactory.createLineBorder(Color.black); //emptyborder
        Border nothing = BorderFactory.createEmptyBorder();
        padding.setBorder(lineBorder);
        padding.setPreferredSize(new Dimension(120,0));

        setLayout(new BorderLayout());
        fc = new JFileChooser();

        Rectangle g = new Rectangle();


        Integer[] speedVariants = {150,200,250,300,350,400,450,500,550,600,650,700};
        speedList = new JComboBox<>(speedVariants);
        speedList.setSelectedIndex(3);
        speedList.addActionListener(this);
        JLabel speedLabel = new JLabel("Speed, wpm : ");
        speedLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        //JPanel textZone = new JPanel();
        textField = new JTextField();
        bigFont = new Font("SansSerif", Font.BOLD, 40);

        textField.setFont(bigFont);
        textField.setEditable(false);
        textField.setHorizontalAlignment(SwingConstants.LEFT);
        textField.setSize(new Dimension(100, 20));
        textField.setBorder(nothing);

        goButton = new JButton("Play");
        goButton.addActionListener(this);
        selectFile = new JButton("Select file");
        selectFile.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout());

        buttonPanel.add(speedLabel);
        buttonPanel.add(speedList);
        buttonPanel.add(selectFile);
        buttonPanel.add(goButton);

        add(padding, BorderLayout.LINE_START);
        add(buttonPanel, BorderLayout.SOUTH);
        add(textField);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == selectFile ) {//Выбор файла при нажатии кнопки выбора
            int returnVal = fc.showOpenDialog(TextPanel.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                rdr.setFile(fc.getSelectedFile());
            }
            words = rdr.getWords();
        } else if(e.getSource() == goButton) { //Начало чтения выбранного файла при нажатии кнопки Play
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
        } else if (e.getSource() == speedList){
            rdr.setSpeed(speedList.getItemAt(speedList.getSelectedIndex()));
        }
    }

    private ActionListener getTimerAction(){
        return (event) -> {
            timer.setDelay(rdr.getDelay());
            if (mainCount > words.size() - 1)//Проверка на конец читаемого файла
                return;
            if (!(mainCount+1>=words.size()))
                setCurrentDelay(words.get(mainCount+1));
            int padLength = 150 - getDelta(words.get(mainCount));
            padding.setPreferredSize(new Dimension(padLength, 0));
            this.revalidate();
            textField.setText(words.get(mainCount++));
        };
    }

    private int getDelta (String word){

        String centralLetter, subWord;
        int wordCenterIndex = (int)(word.length()*0.34);
        int i = word.indexOf(word.charAt(wordCenterIndex));

        centralLetter = word.substring(i, i+1);
        int centralLetterWidth = (int) bigFont.getStringBounds(centralLetter, frc).getWidth();
        subWord = word.substring(0, i+1);
        return (int) bigFont.getStringBounds(subWord, frc).getWidth();
    }

    private void setCurrentDelay (String word){ //метод для динамического регулирования задержки при знаках препинания
        if (word.isEmpty())
            return;
        if (word.contains(",") || word.contains(":"))
            timer.setDelay((int)(rdr.getDelay()*1.7));
        if (word.contains(".") ||word.contains("!") || word.contains("?") || word.contains(";"))
            timer.setDelay((int)(rdr.getDelay()*2.4));
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("FastReader");
        frame.setSize(500, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(new TextPanel());
        frame.setVisible(true);
    }

    public static void main(String []args){
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
