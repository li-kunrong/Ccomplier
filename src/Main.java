import File.ReadFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import File.Word;
import complier.GrammaticalAnalysis;

public class Main {

    public static void main(String []args){

        String path = ".\\temp";
        ReadFile readFile = new ReadFile();


        Map<String ,Integer> map = readFile.map;
        String temp =  readFile.readData(new File(path));
        System.out.println(temp);
        List<Word> words = readFile.readToList(temp);
        GrammaticalAnalysis ga = new GrammaticalAnalysis(words);
        for (int i = 0; i < words.size(); i++) {
            System.out.println(words.get(i));
        }
        ga.begin();
        for (int i =0; i < ga.getExpressions().size(); i++) {
            System.out.println(ga.getExpressions().get(i).toString());
        }




    }
}
