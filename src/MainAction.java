import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Feraijo on 07.11.2016.
 */


public class MainAction implements ActionListener {
    private TopButtons topButtons;
    private TextPanel textPanel;
    private TextControlButtons textControlButtons;
    private SaveLoad saveLoad;


    private TextReader rdr = TextReader.getInstance();
    private boolean isPauseFlag = false;
    private final Timer timer = new Timer(rdr.getDelay(), getTimerAction());;
    private static int mainCount = 0;
    private List<String> words;

    MainAction(TopButtons tb, TextPanel tp, TextControlButtons tcb) {
        topButtons = tb;
        textPanel = tp;
        textControlButtons = tcb;
        saveLoad = new SaveLoad();

        topButtons.getSelectFileButton().addActionListener(this);
        topButtons.getSaveButton().addActionListener(this);
        topButtons.getSpeedList().addActionListener(this);
        topButtons.getLoadButton().addActionListener(this);

        for (JButton b : textControlButtons.getButtons())
            b.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == topButtons.getSelectFileButton()) {
            JFileChooser fc = topButtons.getFileSelector();
            int returnVal = fc.showOpenDialog(MainPanel.getInstance());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                resetAll();
                rdr.setFile(fc.getSelectedFile());
                rdr.readFile();
                words = rdr.getWholeBookText();
            }
        }

        if (e.getSource() == topButtons.getSpeedList()){
            rdr.setSpeed(topButtons.getSpeedList().getItemAt(topButtons.getSpeedList().getSelectedIndex()));
        }

        if (e.getSource() == topButtons.getSaveButton()){
            saveLoad.save(mainCount);
        }

        if (e.getSource() == topButtons.getLoadButton()){
            mainCount = saveLoad.load();
            words = TextReader.getInstance().getWholeBookText();
        }

        if (e.getSource() == textControlButtons.getButtons()[3]) { //Начало чтения выбранного файла при нажатии кнопки Play/Pause

            if (rdr.getFile() == null && words == null) {
                JOptionPane.showMessageDialog(textPanel, "You should choose the file to read from!"
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

        } else if (e.getSource() == textControlButtons.getButtons()[0]){ //10 слов назад
            if (mainCount>10)
                mainCount -= 10;
        } else if (e.getSource() == textControlButtons.getButtons()[1]){ // 5 слов назад
            if (mainCount>5)
                mainCount -= 5;
        } else if (e.getSource() == textControlButtons.getButtons()[2]){ // Полная остановка, сброс текущего файла
            resetAll();
        } else if (e.getSource() == textControlButtons.getButtons()[4]){ // 5 слов вперёд
            if (mainCount + 5 < words.size())
                mainCount += 5;
        } else if (e.getSource() == textControlButtons.getButtons()[5]){ //10 слов вперёд
            if (mainCount + 10 < words.size())
                mainCount += 10;
        }
    }

    private void setCurrentDelay(String word) { //метод для динамического регулирования задержки при знаках препинания
        if (word.isEmpty())
            return;
        if (word.contains(",") || word.contains(":"))
            timer.setDelay((int) (rdr.getDelay() * 2.1));
        if (word.contains(".") || word.contains("!") || word.contains("?") || word.contains(";"))
            timer.setDelay((int) (rdr.getDelay() * 3.1));
    }

    private ActionListener getTimerAction() {
        return (event) -> {
            timer.setDelay(rdr.getDelay());
            if (mainCount > words.size() - 1)//Проверка на конец читаемого файла
                return;
            if (mainCount + 1 < words.size())
                setCurrentDelay(words.get(mainCount + 1));
            String[] word = getWordParts(words.get(mainCount++));
            for (int i = 0; i<textPanel.getTextFields().length; i++){
                textPanel.getTextFields()[i].setText(word[i]);
            }
            textPanel.revalidate();
        };
    }

    private void resetAll() {
        timer.stop();
        isPauseFlag = false;
        rdr.setFile(null);
        words = null;
        mainCount = 0;
        for (JTextField tf : textPanel.getTextFields()){
            tf.setText("");
        }
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
}
