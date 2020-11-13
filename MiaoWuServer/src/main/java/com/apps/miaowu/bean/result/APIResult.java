package com.apps.miaowu.bean.result;

public class APIResult<T> extends ResultSupport {
    protected T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * @param code
     * @param message
     * @param data
     * @param <U>
     * @return
     */
    public static <U> APIResult<U> newResult(int code, String message, U data) {
        APIResult<U> apiResult = new APIResult<U>();
        apiResult.setCode(code);
        apiResult.setMessage(message);
        apiResult.setData(data);
        return apiResult;
    }

    public static <U> APIResult<U> newResult(ResultEnum resultEnum, U data){
        return newResult(resultEnum.getCode(), resultEnum.getMsg(), data);
    }

    @Override
    public String toString() {
        return "APIResult{" +
                "code=" + getCode() +
                ", message=" + getMessage() +
                ", data=" + data +
                '}';
    }
}
