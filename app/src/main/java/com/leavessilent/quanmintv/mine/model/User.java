package com.leavessilent.quanmintv.mine.model;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class User {
    private String name;
    private String icon;

    public User() {
    }

    public User(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
