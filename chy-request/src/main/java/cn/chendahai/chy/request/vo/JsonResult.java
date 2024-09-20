package cn.chendahai.chy.request.vo;

/**
 * Created by zhuwenjun on 2017/12/7.
 */
public class JsonResult<T> {

    public static final int FAIL = 0;
    public static final int SUCCESS = 1;

    private int result;
    private String info;
    private T data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T t) {
        this.data = t;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "result=" + result +
                ", info='" + info + '\'' +
                ", data=" + data +
                '}';
    }
}
