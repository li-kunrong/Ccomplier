package complier;

/**
 * @author kunrong
 * @date 2018/12/4 10:42
 */
public class NewExp extends Expression {
    private String syn_if = "";
    private String condition = "";
    private String syn_goto = "";
    private int lineCount = 0;
    private String flag = "";
    public NewExp( int count, String syn_if, String condition, String syn_goto, int lineCount,String flag) {
        super();
        super.setCount(count);
        this.syn_if = syn_if;
        this.condition = condition;
        this.syn_goto = syn_goto;
        this.lineCount = lineCount;
    }

    @Override
    public String toString() {
        return "count: " + super.getCount()+  " " +syn_if + " " + condition + " " + syn_goto + " " + lineCount;
    }

    public NewExp(int count,String res, String arg1, String op, String arg2) {
        super(count,res, arg1, op, arg2);
    }

    public String getSyn_if() {
        return syn_if;
    }

    public void setSyn_if(String syn_if) {
        this.syn_if = syn_if;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSyn_goto() {
        return syn_goto;
    }

    public void setSyn_goto(String syn_goto) {
        this.syn_goto = syn_goto;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }
}
