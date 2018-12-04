package complier;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kunrong
 * @date 2018/12/4 9:14
 */
public class WordOfC {
    public static Map<Integer, String> wordMap = new HashMap<>();
    static {
        wordMap.put(1,"main" );
        wordMap.put(2, "int");
        wordMap.put(3, "char");
        wordMap.put(4, "if");
        wordMap.put(5, "else");
        wordMap.put(6, "for");
        wordMap.put(7,"while");
        wordMap.put(21, "=");
        wordMap.put(22, "+");
        wordMap.put(23, "-");
        wordMap.put(24, "*");
        wordMap.put(25, "/");
        wordMap.put(26, "(");
        wordMap.put(27, ")");
        wordMap.put(28, "[");
        wordMap.put(29, "]");
        wordMap.put(30, "{");
        wordMap.put(31, "}");
        wordMap.put(32, ",");
        wordMap.put(33, ":");
        wordMap.put(34, ";");
        wordMap.put(35, ">");
        wordMap.put(36, "<");
        wordMap.put(37, ">=");
        wordMap.put(38, "<=");
        wordMap.put(39, "<=");
        wordMap.put(40, "!=");
        wordMap.put(1000, "\0");
        wordMap.put(-1, "ERROR");
    }
}
