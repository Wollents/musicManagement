package team.tzjw.music.pojo;

import java.util.List;

public class Result {
    private Integer code;
    private String msg;
    private Long count;
    private List data;
    private Integer total = 100;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }


    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
