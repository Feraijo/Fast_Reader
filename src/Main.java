import javax.swing.*;

/**
 * Created by Feraijo on 24.10.2016.
 */
public class Main {
    public static void main(String []args){
        ButtonFrame frame= new ButtonFrame();//создаем фрейм
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//операция отвечающая за окончание программы после закрытия фрейма
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);//делаем фрейм видимым
    }
}
