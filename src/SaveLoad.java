import java.io.*;
import java.util.ArrayList;

/**
 * Created by Feraijo on 06.11.2016.
 */

class SaveLoad implements Serializable {

    private String fileName;
    private int loadedWordNumber;


    synchronized void save(int wordNumberToSave) {
        try {
            ArrayList<String> textToSave = TextReader.getInstance().getWholeBookText();
            //fileName = TextReader.getInstance().getFile().getName();
            FileOutputStream fileOutput = new FileOutputStream("resource/saves/text.saved");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutput);
            outputStream.writeObject(textToSave);
            outputStream.writeInt(wordNumberToSave);
            //outputStream.flush();
            outputStream.close();
            fileOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized int load() {
        try {
            FileInputStream fin = new FileInputStream("resource/saves/text.saved");
            ObjectInputStream inputStream = new ObjectInputStream(fin);
            try {
                TextReader.getInstance().setWholeBookText((ArrayList<String>) inputStream.readObject());
                loadedWordNumber = inputStream.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            inputStream.close();
            fin.close();
            return loadedWordNumber;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
