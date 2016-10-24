import javax.swing.*;
import javax.swing.Timer;
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
    private int speed=60;

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private int getDelay(){
        return (60/speed)*1000;
    }
    public ButtonPanel(){ //конструктор панели
        add(textField);
        try {
            File file = new File("1.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
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
        add(goButton);//добавляем кнопки на панель
        goButton.addActionListener((e) -> {
                timer.start();
            });

    }
}

/*


try {
                for (String s : words){

                    textField.setText(s);
                    //this.update(textField.getGraphics());
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }


new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    for (String s : words){
                        textField.setText(s);
                        add(textField);
                        Thread.sleep(600);
                    }
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }

 */