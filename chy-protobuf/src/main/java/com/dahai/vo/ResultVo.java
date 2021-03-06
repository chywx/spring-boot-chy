package com.dahai.vo;/**
 * @author chy
 * @date 2021/3/6 0006 上午 10:16
 * Description：
 */

/**
 * 功能描述
 *
 * @author chy
 * @date 2021/3/6 0006
 */
public class ResultVo {

    private int code = 200;

    private Object data;

    public ResultVo() {
    }

    public ResultVo(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
