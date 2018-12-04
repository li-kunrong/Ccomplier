package complier;

import File.ReadFile;
import File.Word;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kunrong
 * @date 2018/12/1 15:21
 */
public class GrammaticalAnalysis {
    private List<Word> words  = null;
    private List<Expression> expressions = new ArrayList<>();
    private int index = 0;//记录到哪个word
    private int syn = 0; //种别码
    private boolean isSuccess = true;
    private static int expCount = 0;
    private static int count = 0;
    private static int while_line =0;
    private static int if_line = 0;


    public GrammaticalAnalysis(List<Word> words) {
        this.words = words;
    }

    public void begin() {
            syn = words.get(index).getTypenum();
            match(1);//main
            match(26);//(
            match(27);//)
            statement_Block();
            if (syn!=-1){
                System.out.println("源程序非正常结束");
            }else{
                if (isSuccess)
                    System.out.println("success");
                else {
                    System.out.println("fail");
                }
            }


    }
    /**
        语句块分析函数
     */
    void statement_Block() {
        match(30);
        statement_Sequence();
        match(31);
    }

    /**
     * 语句串分析函数
     */
    public void statement_Sequence() {
        statement();
        while (syn == 2|| syn == 3||syn==10 || syn== 4|| syn == 7) {// int, char, if , while, 变量
            statement();
        }
    }

    /**
     * 语句分析函数
     */
    public void statement() {
        String arg = "";
        String result = "";
        switch (syn) {
            case 2://int
                index++;
                syn= words.get(index).getTypenum();
                result = words.get(index).getWorld();
                index++;
                syn= words.get(index).getTypenum();
                if (syn == 34){//;
                    index++;
                    syn = words.get(index).getTypenum();
                    return;
                }
                match(21);// =
                arg = expression();

                Expression exp = new Expression(++expCount,result,arg,"","");
                expressions.add(exp);
                match(34);// ;

                break;
            case 3://char
                index++;
                syn= words.get(index).getTypenum();
                result = words.get(index).getWorld();
                index++;
                syn= words.get(index).getTypenum();
                if (syn == 34){//;
                    return;
                }
                match(21);// =
                arg = expression();
                Expression exp1 = new Expression(++expCount,result,arg,"","");
                expressions.add(exp1);
                match(34);// ;
                break;
            case 10://word
                result = words.get(index).getWorld();
                index++;
                syn= words.get(index).getTypenum();
                match(21);// =
                arg = expression();
                Expression exp2 = new Expression(++expCount,result,arg,"","");
                expressions.add(exp2);
                match(34);// ;
                break;
            case 4://if
                index++;
                syn = words.get(index).getTypenum();
                match(26);//(
                if_line = expCount+1;
                condition();
                match(27);//)
                statement_Block();
                recover(if_line);
                break;
            case 7://while
                index++;
                syn = words.get(index).getTypenum();
                match(26);//(
                while_line = expCount+1;
                condition();
                match(27);//)
                statement_Block();
                NewExp exp3 = new NewExp(++expCount,"goto","","",while_line,"");
                expressions.add(exp3);
                recover(while_line);
                break;
                default:break;
        }
    }

    public void condition() {
        String result = "";
        String arg1 = "";
        String op = "";
        String arg2 = "";
        arg1 = expression();

        if (syn <= 40 && syn >= 35){
            result = newTerm();
           op = words.get(index).getWorld();
           index++;
           syn = words.get(index).getTypenum();
           arg2 =expression();
           Expression exp1 = new Expression(++expCount,result,arg1,op,arg2);
           expressions.add(exp1);
        }
        NewExp newExp = new NewExp(++expCount,"if",result,"goto",expCount+2," ");
        expressions.add(newExp);
        NewExp exp2 = new NewExp(++expCount,"","" , "goto",0,"");
        expressions.add(exp2);

    }

    /**
     * 表达式分析函数
     * @return
     */
    public String expression() {
        String result = "";
        String arg1 = "";
        String op = "";
        String arg2 = "";
        arg1 = term();

        while (syn == 22 || syn == 23) {//+-
            result = newTerm();
            op = words.get(index).getWorld();
            index++;
            syn = words.get(index).getTypenum();
            arg2= term();
            Expression exp = new Expression(++expCount,result,arg1,op,arg2);
            expressions.add(exp);
            arg1 = result;
        }
        return arg1;
    }

    public String term() {
        String result = "";
        String arg1 = "";
        String op = "";
        String arg2 = "";
        arg1 = factor();
        while (syn == 24 || syn == 23) {//*/
            result = newTerm();
            op = words.get(index).getWorld();
            index++;
            syn = words.get(index).getTypenum();
            arg2 = factor();
            Expression exp = new Expression(++expCount,result,arg1,op, arg2);
            expressions.add(exp);
            arg1 = result;
        }
        return  arg1;
    }

    /**
     * 因子分析
     * @return
     */
    public String  factor() {
        String arg = "";
        if (syn == 10 || syn == 11) { //变量或者数字
            arg = words.get(index).getWorld();
            index++;
            syn = words.get(index).getTypenum();
        }else {
            match(26);
            arg = expression();
            match(27);
        }
        return arg;
    }


    void match(int stardar) {
        if (syn == stardar) {
            index++;
            syn= words.get(index).getTypenum();
        }else{
            this.isSuccess = false;
            System.out.println("第"+ words.get(index).getLinecount()+"行缺少" + WordOfC.wordMap.get(stardar));
//            index++;
//            syn= words.get(index).getTypenum();
        }
    }

    public static String newTerm() {
        count++;
        return "T"+String.valueOf(count);
    }

    public List<Expression> getExpressions() {
        return expressions;
    }
    public void recover(int line) {
      //  System.err.println(line);
        for (int i  = 0; i < expressions.size(); i++) {
            if (expressions.get(i).getCount()== line+2) {
                NewExp newExp = new NewExp(line+2,"goto","","",expCount+1,"");
                expressions.remove(i);
                expressions.add(i,newExp);
                break;

            }
        }
    }
}
