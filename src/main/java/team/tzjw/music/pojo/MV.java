package team.tzjw.music.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class MV implements Serializable {
    private String id;

    private Integer mvwidth;

    private Integer mvheight;

    private String mvdesc;

    private Float mvtime;

    private String singerid;

    private String singer;

    private String userid;

    private String mvpath;

    private Double size;

    private Long playnum;
    @JsonFormat(pattern = "yyyy-MM-dd hh:ss:mm",timezone = "GMT+8")
    private Date createtime;

    private Long likecounts;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getMvwidth() {
        return mvwidth;
    }

    public void setMvwidth(Integer mvwidth) {
        this.mvwidth = mvwidth;
    }

    public Integer getMvheight() {
        return mvheight;
    }

    public void setMvheight(Integer mvheight) {
        this.mvheight = mvheight;
    }

    public String getMvdesc() {
        return mvdesc;
    }

    public void setMvdesc(String mvdesc) {
        this.mvdesc = mvdesc == null ? null : mvdesc.trim();
    }

    public Float getMvtime() {
        return mvtime;
    }

    public void setMvtime(Float mvtime) {
        this.mvtime = mvtime;
    }

    public String getSingerid() {
        return singerid;
    }

    public void setSingerid(String singerid) {
        this.singerid = singerid == null ? null : singerid.trim();
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer == null ? null : singer.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getMvpath() {
        return mvpath;
    }

    public void setMvpath(String mvpath) {
        this.mvpath = mvpath == null ? null : mvpath.trim();
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Long getPlaynum() {
        return playnum;
    }

    public void setPlaynum(Long playnum) {
        this.playnum = playnum;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getLikecounts() {
        return likecounts;
    }

    public void setLikecounts(Long likecounts) {
        this.likecounts = likecounts;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}