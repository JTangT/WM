package com.jtangt.wm.po;

public class ShopBean {
    private int id;//编号
    private String shopName;//名称
    private int saleNum;//出售数量
    private int offerprice;//配送费
    private int distributioncost;//
    private String adNotice;
    private String welfare1;
    private String welfare2;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public int getOfferprice() {
        return offerprice;
    }

    public void setOfferprice(int offerprice) {
        this.offerprice = offerprice;
    }

    public int getDistributioncost() {
        return distributioncost;
    }

    public void setDistributioncost(int distributioncost) {
        this.distributioncost = distributioncost;
    }

    public String getAdNotice() {
        return adNotice;
    }

    public void setAdNotice(String adNotice) {
        this.adNotice = adNotice;
    }

    public String getWelfare1() {
        return welfare1;
    }

    public void setWelfare1(String welfare1) {
        this.welfare1 = welfare1;
    }

    public String getWelfare2() {
        return welfare2;
    }

    public void setWelfare2(String welfare2) {
        this.welfare2 = welfare2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}