package ro.tekin.disertatie.util.paging;

import ro.tekin.disertatie.util.TConstants;

import java.util.Collection;

/**
 * Created by tekin on 3/8/14.
 */
public class TPager {
    private Integer page; //current Page
    private Integer totalPages; // total number of totalPages
    private Integer records; // total number of records
    private Collection rows; // the data
    private Integer perPage;
    private String sord;
    private String sidx;

    public TPager() {
        this.page = 1;
        this.perPage = TConstants.DEFAULT_RESULTS_PER_PAGE;
    }
    public TPager(Integer page, Integer perPage) {
        this.page = page == null ? 1 : page;
        this.perPage = perPage == null ? TConstants.DEFAULT_RESULTS_PER_PAGE : perPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords(Integer records) {
        this.records = records;
    }

    public Collection getRows() {
        return rows;
    }

    public void setRows(Collection rows) {
        this.rows = rows;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }
}