package team.tzjw.music.pojo;

import java.io.Serializable;

public class Type implements Serializable {
    private String tid;

    private String tname;

    private String tdesc;

    private static final long serialVersionUID = 1L;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname == null ? null : tname.trim();
    }

    public String getTdesc() {
        return tdesc;
    }

    public void setTdesc(String tdesc) {
        this.tdesc = tdesc == null ? null : tdesc.trim();
    }
}