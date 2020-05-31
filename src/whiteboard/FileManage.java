package whiteboard;

import java.io.*;
import java.util.ArrayList;

public class FileManage {
    private String filePath = null;
    private String fileName = null;


    public void save(ArrayList<Object> objects) {
        saveAs(objects,filePath,fileName);
    }

    public void saveAs(ArrayList<Object> objects, String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName.replace(".txt","") + ".txt";
        try {
            //Write Objects array to file.

            FileOutputStream fos = new FileOutputStream(filePath + "/"+  this.fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(objects);
            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Object> open(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
        ArrayList<Object> objectsRead = new ArrayList<>();;

        try {
            //Read Objects array from file.
            FileInputStream fis = new FileInputStream(filePath + "/"+ fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            objectsRead = (ArrayList<Object>) ois.readObject();
            ois.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return objectsRead;
    }

    public boolean hasFilePath() {
        if(this.filePath != null && !this.filePath.isEmpty())
            return true;
        else
            return false;
    }

    public void deleteFilePath() {
        this.filePath = null;
        this.fileName = null;
    }

    public String getFileName() {
        return this.fileName;
    }
}
