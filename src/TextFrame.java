import javax.swing.*;


/**
 * Класс отвечающий за фрейм
 * Created by Feraijo on 24.10.2016.
 */

class TextFrame extends JFrame {
    private static final int DEFAULT_WIDTH=500;
    private static final int DEFAULT_HEIGHT=180;

    private TextFrame(){
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setTitle("FastReader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new TextPanel();
        add(panel);
    }

    public static void main(String []args){
        JFrame frame = new TextFrame();
        frame.setVisible(true);
    }
    private void getStripes(){

    }
}