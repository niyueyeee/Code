package entity;

import java.io.Serializable;

/**
 * @author niyueyeee
 * @create 2019-04-25 21:52
 */
public class Result implements Serializable {
    private boolean flag;
    private String message;

    public Result(boolean flag, String message) {
        super();
        this.flag = flag;
        this.message = message;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
