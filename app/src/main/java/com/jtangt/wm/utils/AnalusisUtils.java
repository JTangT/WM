package com.jtangt.wm.utils;

import android.util.Log;

import com.jtangt.wm.po.ShopBean;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class AnalusisUtils {

    public static List<ShopBean> getShopInfos(String json) throws JSONException {

        List<ShopBean> shopInfos = null;
        JSONObject j=new JSONObject(json);
        for(int i=0;i< j.getInt("num");i++){
            ShopBean shopBean=new ShopBean();
            JSONObject t=j.getJSONObject(i+"");
            shopBean.setId(t.getInt("id"));
            shopBean.setShopName(t.getString("shopName"));
            shopBean.setSaleNum(t.getInt("saleNum"));
            shopBean.setOfferprice(t.getInt("offerprice"));
            shopBean.setDistributioncost(t.getInt("distributioncost"));
            shopBean.setAdNotice(t.getString("adNotice"));
            shopBean.setWelfare1(t.getString("welfare"));
            shopBean.setWelfare2(t.getString("welfare2"));
            shopBean.setTime(t.getString("time"));
            shopInfos.add(shopBean);
        }


        return shopInfos;
    }
}
