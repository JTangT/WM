package com.jtangt.wm;

import com.jtangt.wm.po.LeftBean;
import com.jtangt.wm.po.RightBean;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private static String[] leftData = new String[]{"13.9特价套餐","粗粮主食","佐餐小吃","用心营养套餐(不含主食)","三杯鸡双拼尊享套餐","带鱼双拼尊享套餐","红烧肉双拼尊享套餐"};
    private static String[] rightData0 = new String[]{"洋芋粉炒腊肉","土鸡炖香菇","新疆大盘辣子土鸡","清炖土鸡块","农家蒸碗  ","香辣野猪肉","香辣薯条大虾","麻辣猪血"};
    private static String[] rightData1 = new String[]{"商芝扣肉","羊肉萝卜","干烧鱼  ","干煸野猪肉 ","排骨火锅","土鸡火锅","牛肉火锅","狗肉火锅 "};
    private static String[] rightData2 = new String[]{"虎皮辣子炒咸肉","重庆飘香水煮鱼","红烧土鸡块","干煸辣子土鸡","清炖全鸡 "};
    private static String[] rightData3 = new String[]{"洋芋粉炒腊肉","土鸡炖香菇","新疆大盘辣子土鸡","清炖土鸡块","农家蒸碗  ","香辣野猪肉","香辣薯条大虾","麻辣猪血"};
    private static String[] rightData4 = new String[]{"洋芋粉炒腊肉","土鸡炖香菇","新疆大盘辣子土鸡","清炖土鸡块","农家蒸碗  ","香辣野猪肉","香辣薯条大虾","麻辣猪血"};
    private static String[] rightData5 = new String[]{"洋芋粉炒腊肉","土鸡炖香菇","新疆大盘辣子土鸡","清炖土鸡块","农家蒸碗  ","香辣野猪肉","香辣薯条大虾","麻辣猪血"};
    private static String[] rightData6 = new String[]{"洋芋粉炒腊肉","土鸡炖香菇","新疆大盘辣子土鸡","清炖土鸡块","农家蒸碗  ","香辣野猪肉","香辣薯条大虾","麻辣猪血"};

    public static List<LeftBean> getLeftData(){
        List<LeftBean> list = new ArrayList<LeftBean>();
        for (int i = 0; i < leftData.length; i++) {
            LeftBean bean = new LeftBean();
            bean.title = leftData[i];
            bean.type = i;
            list.add(bean);
        }

        return list;

    }

    public static List<RightBean> getRightData(List<LeftBean> list){
        List<RightBean> rightList = new ArrayList<RightBean>();
        for (int i = 0; i < list.size(); i++) {
            LeftBean leftBean = list.get(i);
            int mType = leftBean.type;
            switch (mType) {
                case 0:
                    rightList = getRightList(rightData0, leftBean, mType, rightList);
                    break;
                case 1:
                    rightList = getRightList(rightData1, leftBean, mType, rightList);
                    break;
                case 2:
                    rightList = getRightList(rightData2, leftBean, mType, rightList);
                    break;
                case 3:
                    rightList = getRightList(rightData3, leftBean, mType, rightList);
                    break;
                case 4:
                    rightList = getRightList(rightData4, leftBean, mType, rightList);
                    break;
                case 5:
                    rightList = getRightList(rightData5, leftBean, mType, rightList);
                    break;
                case 6:
                    rightList = getRightList(rightData6, leftBean, mType, rightList);
                    break;
            }


        }


        return rightList;

    }


    private static List<RightBean> getRightList(String[] arr, LeftBean leftBean, int mType, List<RightBean> rightList){
        for (int j = 0; j < arr.length; j++) {
            RightBean bean = new RightBean();
            bean.type = leftBean.title;
            bean.biaoti = arr[j];
            bean.typeId = mType;
            rightList.add(bean);
        }
        return rightList;
    }
}