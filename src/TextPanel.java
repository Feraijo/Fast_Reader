import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private TextReader rdr = TextReader.getInstance();
    private boolean isPause = false;
    private final Timer timer = new Timer(rdr.getDelay(), getAction());
    private int mainCount = 0;
    private List<String> words;

    TextPanel(){
        rdr.setFile(new File("try.txt"));words = rdr.getWords(); // Заглушка для тестов
        setLayout(new BorderLayout());
        fc = new JFileChooser();

        Integer[] speedVariants = {150,200,250,300,350,400,450,500,550,600,650,700};
        speedList = new JComboBox<>(speedVariants);
        speedList.setSelectedIndex(3);
        speedList.addActionListener(this);

        textField = new JTextField();
        Font bigFont = new Font("SansSerif", Font.BOLD, 40);
        textField.setFont(bigFont);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setSize(new Dimension(100, 20));

        goButton = new JButton("Play");
        goButton.addActionListener(this);
        selectFile = new JButton("Select file");
        selectFile.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(speedList);
        buttonPanel.add(selectFile);
        buttonPanel.add(goButton);

        add(textField, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
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
            if (!isPause) {
                timer.start();
                goButton.setText("Pause");
                isPause = true;
            } else {
                timer.stop();
                goButton.setText("Play");
                isPause = false;
            }
        } else if (e.getSource() == speedList){
            rdr.setSpeed(speedList.getItemAt(speedList.getSelectedIndex()));
        }
    }
    private void setCurrentDelay (String word){ //метод для динамического регулирования задержки при знаках препинания
        if (word.isEmpty())
            return;
        if (word.contains(",") || word.contains(":"))
            timer.setDelay((int)(rdr.getDelay()*1.45));
        if (word.contains(".") ||word.contains("!") || word.contains("?") || word.contains(";"))
            timer.setDelay((int)(rdr.getDelay()*1.8));
    }
    private ActionListener getAction(){
        return (event) -> {
            timer.setDelay(rdr.getDelay());
            if (mainCount > words.size() - 1)//Проверка на конец читаемого файла
                return;
            if (!(mainCount+1>=words.size()))
                setCurrentDelay(words.get(mainCount+1));
            textField.setText(words.get(mainCount++));
        };
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("FastReader");
        frame.setSize(800, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(new TextPanel());

        //frame.pack();
        frame.setVisible(true);
    }

    public static void main(String []args){
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
