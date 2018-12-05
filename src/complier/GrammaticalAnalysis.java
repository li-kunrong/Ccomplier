package complier;

import File.ReadFile;
import File.Word;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author kunrong
 * @date 2018/12/1 15:21
 */
public class GrammaticalAnalysis {
    private List<Word> words  = null;
    private List<Expression> expressions = new ArrayList<>();
    private List<Error> errors = new ArrayList<>();
    private int index = 0;//记录到哪个word
    private int syn = 0; //种别码
    private boolean isSuccess = true;
    private static int expCount = 0;
    private static int count = 0;
    private static int while_line =0;
    private static int if_line = 0;
    private Stack<Integer> whileStack = new Stack();
    private Stack<Integer> ifStack = new Stack<>();


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
        dealIrrevelant();
        dealWithError();
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
                ifStack.push(if_line);
                condition();
                match(27);//)
                statement_Block();
                if_line = ifStack.pop();
                recover(if_line);
                if (syn == 5) {
                    NewExp exp3 = new NewExp(++expCount,"goto","","",0,"");
                    expressions.add(exp3);
                    recover(if_line);
                    ifStack.push(expCount-2);
                    index++;
                    syn = words.get(index).getTypenum();
                    statement_Block();
                    if_line = ifStack.pop();
                    recover(if_line);
                }
                break;
            case 7://while
                index++;
                syn = words.get(index).getTypenum();
                match(26);//(
                while_line = expCount+1;
                whileStack.push(while_line);
                condition();
                match(27);//)
                statement_Block();
                while_line = whileStack.pop();
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
            if (syn == 26) {
                match(26);
                arg = expression();
                match(27);
            }else {
                Error error = new Error("第"+ words.get(index).getLinecount() + "行 缺少因子，表达式错误");
                errors.add(error);
                arg = "";
            }

        }
        return arg;
    }

    /**
     * 判断该处是否正确
     * @param stardar
     */
    void match(int stardar) {
        if (syn == stardar) {
            index++;
            syn= words.get(index).getTypenum();
        }else{
            this.isSuccess = false;
            System.out.println("第"+ words.get(index).getLinecount()+"行缺少" + WordOfC.wordMap.get(stardar));
            Error error = new Error("第"+ words.get(index).getLinecount()+"行缺少" + WordOfC.wordMap.get(stardar));
            errors.add(error);
        }
    }

    /**
     * 产生新的临时变量
     * @return
     */
    public static String newTerm() {
        count++;
        return "T"+String.valueOf(count);
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    /**
     * 恢复之前还没写的goto的行数
     * @param line
     */
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

    public List<Error> getErrors() {
        return errors;
    }

    /**
     * 处理无关项
     */
    public void dealIrrevelant() {
        while(index<words.size() && syn == 34) {
            index++;
            syn = words.get(index).getTypenum();
        }
        if (syn == 2|| syn == 3||syn==10 || syn== 4|| syn == 7 ){
            statement_Sequence();
        }

    }

    public void dealWithError() {
        while (index<words.size()&&syn != 2&& syn != 3&&syn!=10 && syn!= 4&& syn != 7 ) {
            if (syn== 31) {
                break;
            }
            Error error = new Error("第"+ words.get(index).getLinecount() + "行语句错误");
            errors.add(error);
            index++;
            syn = words.get(index).getTypenum();
        }
        if (syn == 2|| syn == 3||syn==10 || syn== 4|| syn == 7 ){
            statement_Sequence();
        }
    }
}
