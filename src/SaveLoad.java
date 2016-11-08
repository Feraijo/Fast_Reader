import java.io.*;
import java.util.List;

/**
 * Created by Feraijo on 06.11.2016.
 */

class SaveLoad implements Serializable {
    private List<String> textToSave = TextReader.getInstance().getWholeBookText();
    //private String fileName = TextReader.getInstance().getFile().getName();
    private int loadedWordNumber;


    void save(int wordNumberToSave) {
        try {
            FileOutputStream fileOutput = new FileOutputStream("resource/saves/text.saved");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutput);
            outputStream.writeObject(textToSave);
            outputStream.writeInt(wordNumberToSave);
            outputStream.close();
            fileOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int load() {
        try {
            FileInputStream fin = new FileInputStream("resource/saves/text.saved");
            ObjectInputStream inputStream = new ObjectInputStream(fin);
            try {
                TextReader.getInstance().setWords((List<String>) inputStream.readObject());
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
