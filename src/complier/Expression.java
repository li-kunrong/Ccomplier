package complier;

/**
 * @author kunrong
 * @date 2018/12/1 17:57
 */
public class Expression {
    private int count = 0;
    private String res = "";
    private String arg1="";
    private String op = "";
    private String arg2 = "";

    public Expression() {
    }

    public Expression(int count,String res, String arg1, String op, String arg2) {
        this.count = count;
        this.res = res;
        this.arg1 = arg1;
        this.op = op;
        this.arg2 = arg2;
    }

    @Override
    public String toString() {
        return "count: "+count+" " +  res+" = "+arg1+ " " +op+ " " + arg2;
    }

    public  int getCount() {
        return count;
    }

    public  void setCount(int count) {
        this.count = count;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

}
