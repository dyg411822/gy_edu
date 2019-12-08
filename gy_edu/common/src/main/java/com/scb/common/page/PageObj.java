package com.scb.common.page;

import java.io.Serializable;

/**
 * <>
 *
 * @param <T>
 * @author 封装返回给客户端的分页对象
 */
@Deprecated
public class PageObj<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6479259084734311813L;
    private long pageCount;
    private int pageNum;
    private long total;
    private int size;
    private T items;

    public PageObj() {
    }

    public PageObj(long total, int pageNum, int pageSize, T items) {
        if (pageSize <= 0) {
            pageSize = 10;
        }
        this.total = total;
        this.pageNum = pageNum;
        this.size = pageSize;
        this.pageCount = (total + pageSize - 1) / pageSize;
        this.items = items;
    }

    public T getItems() {
        return items;
    }

    public void setItems(T items) {
        this.items = items;
    }

    public PageObj(int total, int size, int pageNum) {
        this.total = total;
        this.size = size;
        this.pageNum = pageNum;
        this.pageCount = (total + size - 1) / size;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static <T> PageObj<T> create(long total, int pageNum, int pageSize, T t) {
        return new PageObj<T>(total, pageNum, pageSize, t);
    }

    public static <T> PageObj<T> create(T t, Page page) {
        return create(page.getTotalRecords(), page.getPageNo(), page.getLength(), t);
    }
}
