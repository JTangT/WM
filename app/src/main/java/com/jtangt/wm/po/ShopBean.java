package com.jtangt.wm.po;

public class ShopBean {
    private int id;//编号
    private String shopName;//名称
    private int saleNum;//出售数量
    private int offerprice;//配送费
    private int startprice;//起送费
    private String welfare1;//消息1 JSON格式
    private String welfare2;//消息2 JSON格式
    public String shopIcon;//店铺图片
    public String adNotice;//广告信息
    private int time;//预计时间

    public String getAdNotice(){return adNotice;}
    public void setAdNotice(String adNotice){this.adNotice=adNotice;}

    public String getShopIcon() {
        return shopIcon;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }



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

    public int getStartprice() {
        return startprice;
    }

    public void setStartprice(int startprice) {
        this.startprice = startprice;
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

    public int getTime(){return time;}
    public void setTime(int time){this.time=time;}

}