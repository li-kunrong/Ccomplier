package File;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadFile {
    public   static Map<String, Integer> map = new HashMap<>();

    private int lineCount = 1;
    static {
        map.put("main", 1);
        map.put("int", 2);
        map.put("char", 3);
        map.put("if", 4);
        map.put("else", 5);
        map.put("for", 6);
        map.put("while",7);
        map.put("=", 21);
        map.put("+", 22);
        map.put("-", 23);
        map.put("*", 24);
        map.put("/", 25);
        map.put("(", 26);
        map.put(")", 27);
        map.put("[", 28);
        map.put("]", 29);
        map.put("{", 30);
        map.put("}", 31);
        map.put(",", 32);
        map.put(":", 33);
        map.put(";", 34);
        map.put(">", 35);
        map.put("<", 36);
        map.put(">=", 37);
        map.put("<=", 38);
        map.put("==", 39);
        map.put("!=", 40);
        map.put("#", 1000);
        map.put("ERROR", -1);
    }
    public String readData(File file) {
        String[] tokens = null;
        String letter = "";
        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();

            try {
                while ((letter = reader.readLine()) != null) {
                    boolean p=true;
                   // String string=letter.trim();
                   // System.out.println(string);

                    if(p){
                        stringBuilder.append(letter + "\n");
                    }


                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            letter = stringBuilder.toString();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return letter;
    }

    public ArrayList<Word> readToList(String string) {
        ArrayList<Word> list = new ArrayList();
        char[] buf = string.toCharArray();
        for (int i = 0; i< buf.length; i++) {
            if (buf[i] == '#'){
                Word word = new Word(-1,String.valueOf(buf[i]),lineCount);
                list.add(word);
                break;
            }
            if (buf[i] == '\n'){
                lineCount++;
                continue;
            }
            if (buf[i] == ' ') {
                continue;
            }

            //判断单词
            if ( buf[i]>='A'&& buf[i]<='Z' || buf[i]>='a'&&buf[i]<='z'){
               String temp = String.valueOf(buf[i]);
               for (i++; i<buf.length; i++) {
                   if (buf[i]>='0'&&buf[i]<='9' || buf[i]>='a'&&buf[i]<='z' || buf[i]>= 'A'&&buf[i]<='Z') {
                       temp+=String.valueOf(buf[i]);
                   }else {
                       i--;
                       break;
                   }
               }
               //先判断有没有关键字
               if (map.containsKey(temp)) {
                   Word word = new Word(map.get(temp),temp,lineCount);
                   list.add(word);
               }else {
                   Word word = new Word(10,temp,lineCount);
                   list.add(word);
               }
               continue;
            }

            //判断数字
            if(buf[i]<='9' && buf[i]>='0') {
                String temp = String.valueOf(buf[i]);
                for (i++; i<buf.length; i++) {
                    if (buf[i] <='9' && buf[i] >='0') {
                        temp+=buf[i];
                    }
                    else {
                        i--;
                        break;
                    }
                }
                Word word = new Word(11,temp,lineCount) ;
                list.add(word);
                continue;
            }
            switch (buf[i]) {
                case '=':
                    if (buf[i+1] == '='){
                        Word word = new Word(39,"==",lineCount);
                        list.add(word);
                        i++;
                    }else{
                        Word word = new Word(21,"=",lineCount);
                        list.add(word);
                    }
                    break;
                case '<':
                    if (buf[i+1] == '='){
                        Word word = new Word(38,"<=",lineCount);
                        list.add(word);
                        i++;
                    }else{
                        Word word = new Word(36,"<",lineCount);
                        list.add(word);
                    }
                    break;
                case '>':
                    if (buf[i+1] == '='){
                        Word word = new Word(37,">=",lineCount);
                        list.add(word);
                        i++;
                    }else{
                        Word word = new Word(35,">",lineCount);
                        list.add(word);
                    }
                    break;
                case '!':
                    if (buf[i+1] == '='){
                        Word word = new Word(40,"!=",lineCount);
                        list.add(word);
                        i++;
                    }else{
                        Word word = new Word(21,"!",lineCount);
                        list.add(word);
                    }
                    break;
                    default:
                        String temp = String.valueOf(buf[i]);
                        if (map.containsKey(temp)){

                         //   System.err.println(temp);
                            Word word = new Word(map.get(temp),temp,lineCount);
                            list.add(word);
                        }

            }

        }
        return list;
    }

    public static Map<String, Integer> getMap() {
        return map;
    }

    public static void setMap(Map<String, Integer> map) {
        ReadFile.map = map;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }
}
