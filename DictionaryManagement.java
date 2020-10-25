import javax.print.DocFlavor;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class DictionaryManagement extends Dictionary{
    protected List<String> myE_VWords = new ArrayList<>();
    protected List<String> myV_EWords = new ArrayList<>();
    public void insertMyWords(String fileName) throws IOException {
        BufferedReader bRead = new BufferedReader(new FileReader(fileName));
        String line;
        if (fileName.equals("MyE_VWords.txt")) {
            while ((line = bRead.readLine()) != null) {
                myE_VWords.add(line);
            }
        }
        else {
            while ((line = bRead.readLine()) != null) {
                myV_EWords.add(line);
            }
        }

        bRead.close();
    }
    public DefaultListModel<String> dictionaryMyWords(String key,String fileNameDictionary) {
        DefaultListModel<String> list = new DefaultListModel<>();
        if (fileNameDictionary.equals("E_V.txt")) {
            for (int i = 0; i < myE_VWords.size(); i++)
                if (myE_VWords.get(i).startsWith(key))
                    list.addElement(myE_VWords.get(i));
        }
        else {
            for (int i = 0; i < myV_EWords.size(); i++)
                if (myV_EWords.get(i).startsWith(key))
                    list.addElement(myV_EWords.get(i));
        }
            return list;
    }
    public void exportToMyFile() throws IOException {
        File file = new File("MyE_VWords.txt");
        File file1 = new File("MyV_EWords.txt");
        file.delete();
        file1.delete();
        BufferedWriter bWriter = new BufferedWriter(new FileWriter("MyE_VWords.txt"));
        BufferedWriter bWriter1 = new BufferedWriter(new FileWriter("MyV_EWords.txt"));
        for (int i=0; i<myE_VWords.size(); i++)
            bWriter.write(myE_VWords.get(i) + "\n");
        for (int i=0; i<myV_EWords.size(); i++)
            bWriter1.write(myV_EWords.get(i) + "\n");
        bWriter.close();
        bWriter1.close();
    }
    public void insertFromFile( String fileName) throws  Exception {
        BufferedReader bReader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = bReader.readLine()) != null) {
            String[] data;
            data = line.split("\t");
            String value1 = data[0];
            StringBuilder value2 = new StringBuilder( data[1] + "\n");
            while((line = bReader.readLine()) != null){
                if(line.equals("")){
                    break;
                }
                value2.append(line).append("\n");
            }
            addWord(value1, value2.toString());
        }
        bReader.close();
    }
    public void insertFromHTMLFile(String fileName) throws IOException {
        words.clear();

        BufferedReader bReader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = bReader.readLine()) != null) {
            String[] data;
            data = line.split("<html>");
            String target = data[0];
            String explain = "<html>";
            explain = explain + data[1];
            addWord(target, explain);
        }
        bReader.close();
    }
    public void showAllWords() {
        System.out.println("NO" + "\t" + "|English" + "\t\t\t\t" + "|Vietnamese");
        int i = 0;
        for (String key : words.keySet()) {
            System.out.println((i + 1) + "\t|" + key+ "\t\t\t\t\t|" + words.get(key));
        }
    }

    public String dictionaryLookup(String keyword) {
       return words.getOrDefault(keyword,"Word not found !!!");
    }
    public void removeWord(String word) {
      words.remove(word);
    }

    public void modifyWord( String key, String newExplainWord) {
        words.replace(key, newExplainWord);
        System.out.println(words.get(newExplainWord));
    }

    public DefaultListModel<String> dictionarySearch(String word) {
        DefaultListModel<String> list = new DefaultListModel<String>();
        boolean check = false;
        for (String key : words.keySet()) {
            if (key.startsWith(word)) {
                list.addElement(key);
                check = true;
            } else if (check) {
                break;
            }
        }
        return list;
    }

    public void dictionaryExportToFile(String fileName) throws Exception {
        BufferedWriter bWriter = new BufferedWriter(new FileWriter(fileName, true));
        for (String key : words.keySet()) {
            bWriter.write(key + "\t" + words.get(key) + "\n");
        }
        bWriter.close();
    }
}