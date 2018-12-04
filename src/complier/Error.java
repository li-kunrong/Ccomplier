package complier;

/**
 * @author kunrong
 * @date 2018/12/4 23:26
 */
public class Error {
    private String error ;

    @Override
    public String toString() {
        return "error='" + error + '\'' +
                '}';
    }

    public Error(String error) {
        this.error = error;
    }

    public Error() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
