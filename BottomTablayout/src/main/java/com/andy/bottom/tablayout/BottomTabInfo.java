package com.andy.bottom.tablayout;

/**
 * Created by luofan on 2018/10/10.
 */

public class BottomTabInfo {

    private String mFragmentName;

    private String mName;

    private int IconRes;

    public String getFragmentName() {
        return mFragmentName;
    }

    public void setFragmentName(String fragmentName) {
        mFragmentName = fragmentName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getIconRes() {
        return IconRes;
    }

    public void setIconRes(int iconRes) {
        IconRes = iconRes;
    }
}
