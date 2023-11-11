package github.com.xszhagxiaocuo.entity;

public class PageInfo {
    private int totalPages;//总页数
    private int totalRecords;//总记录数
    private int prevPage;//上一页
    private int nextPage;//下一页
    private int currentPage;//当前页

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
