import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;


/**
 * Created by Feraijo on 24.10.2016.
 */
class ButtonPanel extends JPanel {//класс отвечающий за фрейм
    private JTextField textField = new JTextField(6);
    private List<String> words;
    private int i = 0;
    private int speed=300;

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private int getDelay(){
        return 60000/speed;
    }

    public ButtonPanel(){ //конструктор панели
        Font bigFont = new Font("SansSerif", Font.BOLD, 40);
        textField.setFont(bigFont);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setPreferredSize(new Dimension(400,100));
        add(textField);
        try {
            File file = new File("try.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1251"));
            List<String> text = new ArrayList<>();
            words = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                text.add(line);
                for (String s : text){
                    words.addAll(Arrays.asList(s.split(" ")));
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        ActionListener actionRevoltWords = (e)->{
            if (i > words.size()-1){
                return;
            }

            textField.setText(words.get(i));
            i++;
        };
        Timer timer = new Timer(getDelay(), actionRevoltWords);
        JButton goButton=new JButton("GO!");//создаем кнопку
        //add(goButton);//добавляем кнопки на панель
        timer.start();
        goButton.addActionListener((e) -> {

            });

    }
}
