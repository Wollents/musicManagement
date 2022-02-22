package team.tzjw.music.pojo;

public class Page {

    private Integer page;

    private Integer pageTotal;

    public Page(Integer page, Integer pageTotal) {
        this.page = (page - 1) * pageTotal;
        this.pageTotal = pageTotal;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }
}