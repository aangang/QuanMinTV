package com.leavessilent.quanmintv.live.model;

import com.leavessilent.quanmintv.home.model.LinkObject;

import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public class AllLiveModel {
    private int total;
    private int pageCount;
    private int page;
    private int size;
    private String icon;
    private List<LinkObject> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<LinkObject> getData() {
        return data;
    }

    public void setData(List<LinkObject> data) {
        this.data = data;
    }
}
