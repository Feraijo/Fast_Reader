import javax.swing.*;

/**
 * Created by Feraijo on 24.10.2016.
 */
class ButtonFrame extends JFrame {//создаем класс отвечающий за фрейм
    public ButtonFrame(){//конструктор данного класа
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);//размеры фрейма
        setTitle("FastReader");//название фрейма
        ButtonPanel panel=new ButtonPanel();//создаем панель
        add(panel);//добавляем панель на фрейм
    }
    public static final  int DEFAULT_WIDTH=300;
    public static final  int DEFAULT_HEIGHT=200;
}