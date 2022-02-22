package team.tzjw.music.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Song implements Serializable {
    private String id;

    private String stitle;

    private Float songtime;

    private String singer;

    private String singerid;

    private String albumid;

    private String albumname;

    private String tid;

    private String userid;

    private String songpath;

    private Double size;

    private String albumpic;

    private String lyricpath;

    private Long playnum;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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

    public String getStitle() {
        return stitle;
    }

    public void setStitle(String stitle) {
        this.stitle = stitle == null ? null : stitle.trim();
    }

    public Float getSongtime() {
        return songtime;
    }

    public void setSongtime(Float songtime) {
        this.songtime = songtime;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer == null ? null : singer.trim();
    }

    public String getSingerid() {
        return singerid;
    }

    public void setSingerid(String singerid) {
        this.singerid = singerid == null ? null : singerid.trim();
    }

    public String getAlbumid() {
        return albumid;
    }

    public void setAlbumid(String albumid) {
        this.albumid = albumid == null ? null : albumid.trim();
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname == null ? null : albumname.trim();
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getSongpath() {
        return songpath;
    }

    public void setSongpath(String songpath) {
        this.songpath = songpath == null ? null : songpath.trim();
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getAlbumpic() {
        return albumpic;
    }

    public void setAlbumpic(String albumpic) {
        this.albumpic = albumpic == null ? null : albumpic.trim();
    }

    public String getLyricpath() {
        return lyricpath;
    }

    public void setLyricpath(String lyricpath) {
        this.lyricpath = lyricpath == null ? null : lyricpath.trim();
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