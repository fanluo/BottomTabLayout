package com.andy.bottom.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.andy.bottom.tablayout.BottomTabInfo;
import com.andy.bottom.tablayout.BottomTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int mMessageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BottomTabLayout bottomTabLayout = findViewById(R.id.bottom_tab);
        bottomTabLayout.initTab(getBottomTabInfo(), R.id.layout_content);
        bottomTabLayout.setOnTabClickListener(new BottomTabLayout.OnTabClickListener() {
            @Override
            public boolean OnTabClick(int position, View view) {
                mMessageCount++;
                Toast.makeText(MainActivity.this, "OnTabClick position=" + position, Toast.LENGTH_SHORT).show();
                bottomTabLayout.setTabMessageCount(position, mMessageCount);
//                if (position == 1) {
//                    return true;
//                }
                return false;
            }
        });
        bottomTabLayout.setOnTabSwitchListener(new BottomTabLayout.OnTabSwitchListener() {
            @Override
            public void switchTabFinish(int position) {
                Toast.makeText(MainActivity.this, "switchTabFinish position=" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //获取底部的Tab信息
    private List<BottomTabInfo> getBottomTabInfo() {
        List<BottomTabInfo> mBottomSource = new ArrayList<>();

        //管理
        BottomTabInfo manageInfo = new BottomTabInfo();
        manageInfo.setFragmentName(ManageFragment.class.getName());
        manageInfo.setIconRes(R.drawable.selector_bottom_manage);
        manageInfo.setName("管理");
        mBottomSource.add(manageInfo);
        //地图
        BottomTabInfo mapInfo = new BottomTabInfo();
        mapInfo.setFragmentName(MapFragment.class.getName());
        mapInfo.setIconRes(R.drawable.selector_bottom_map);
        mapInfo.setName("地图");
        mBottomSource.add(mapInfo);

        return mBottomSource;
    }
}
