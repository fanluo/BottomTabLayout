package com.andy.bottom.tablayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luofan on 2018/10/9.
 */

public class BottomTabLayout extends LinearLayout {

    private Context mContext;

    private OnTabClickListener mOnTabClickListener;

    private OnTabSwitchListener mOnTabSwitchListener;

    private int mFragmentContainerId;

    private Fragment mCurrentFragment;

    private List<BottomTabInfo> mTabInfoList = new ArrayList<>();

    private List<BottomTabItem> mTabItemList = new ArrayList<>();

    private List<Fragment> mFragmentList = new ArrayList<>();//存储的fragment

    private int mItemTextSize = 15;

    private ColorStateList mItemTextColor;

    public BottomTabLayout(Context context) {
        this(context, null);
    }

    public BottomTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        boolean result = mContext instanceof AppCompatActivity;
        if (!result) {
            throw new IllegalArgumentException("only support AppCompatActivity");
        }
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typeArray = getContext().obtainStyledAttributes(attrs, R.styleable.BottomTabLayout, 0, 0);
            mItemTextSize = typeArray.getDimensionPixelOffset(R.styleable.BottomTabLayout_item_text_size, 12);
            mItemTextColor = typeArray.getColorStateList(R.styleable.BottomTabLayout_item_text_color);
            typeArray.recycle();//回收内存
        }
        setBackgroundColor(Color.parseColor("#00BF89"));
    }

    public void initTab(@NonNull List<BottomTabInfo> list, int fragmentContainerId, int selectPosition) {
        mTabInfoList = list;
        int size = list.size();
        mFragmentContainerId = fragmentContainerId;
        for (int i = 0; i < size; i++) {
            BottomTabInfo tabInfo = list.get(i);
            BottomTabItem item = new BottomTabItem(mContext);
//            item.getTabTextView().setTextColor(mItemTextColor);
//            item.getTabTextView().setTextSize(mItemTextSize);
            item.setTag(i);
            item.setTabName(tabInfo.getName());
            item.setTabIconRes(tabInfo.getIconRes());
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    if (mOnTabClickListener == null) {
                        switchTab(position);
                    } else {
                        if (!mOnTabClickListener.OnTabClick(position, view)) {
                            switchTab(position);
                        }
                    }
                }
            });
            addView(item, genItemLayoutParams());
            mTabItemList.add(item);
            //这里需要将所有的fragment构造出来
            AppCompatActivity activity = (AppCompatActivity) mContext;
            Fragment fragment = Fragment.instantiate(activity, tabInfo.getFragmentName());
            mFragmentList.add(fragment);
        }
        switchTab(selectPosition);
    }

    public Fragment getFragment(int position) {
        if (position < mFragmentList.size()) {
            return mFragmentList.get(position);
        }
        return null;
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public BottomTabItem getTab(int position) {
        return mTabItemList.get(position);
    }

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        mOnTabClickListener = onTabClickListener;
    }

    public void setOnTabSwitchListener(OnTabSwitchListener onTabSwitchListener) {
        mOnTabSwitchListener = onTabSwitchListener;
    }

    public void switchTab(int position) {
        if (position < mTabInfoList.size()) {
            AppCompatActivity activity = (AppCompatActivity) mContext;
            Fragment fragment = mFragmentList.get(position);//获取到当前选择的fragment
            //点击了相同的position
            if (fragment == mCurrentFragment) {
                return;
            }
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            if (mCurrentFragment != null) {
                transaction.hide(mCurrentFragment);
            }
            if (!fragment.isAdded()) {
                transaction.add(mFragmentContainerId, fragment);
            }
            transaction.show(fragment).commit();
            mCurrentFragment = fragment;
            int size = mTabItemList.size();
            for (int i = 0; i < size; i++) {
                mTabItemList.get(i).setSelected(i == position);
            }
            if (mOnTabSwitchListener != null) {
                mOnTabSwitchListener.switchTabFinish(position);
            }
        }
    }


    public void initTab(@NonNull List<BottomTabInfo> list, int fragmentContainerId) {
        initTab(list, fragmentContainerId, 0);
    }

    public void setTabMessageCount(int tabPosition, int messageCount) {
        int size = mTabItemList.size();
        if (tabPosition < size) {
            BottomTabItem item = mTabItemList.get(tabPosition);
            item.setTabMessageCount(messageCount);
        }
    }

    private LayoutParams genItemLayoutParams() {
        LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        return lp;
    }

    /**
     * 当tab点击时触发
     */
    public interface OnTabClickListener {
        /**
         * @param position
         * @param view
         * @return true 表示只处理外部OnTabClick的事件, 不跳转tab, false表示处理完外部事件后，跳转到指定的tab
         */
        boolean OnTabClick(int position, View view);
    }

    /**
     * tab切换完成后的监听
     */
    public interface OnTabSwitchListener {
        /**
         * @param position 切换完成position表示切换到的位置
         */
        void switchTabFinish(int position);
    }
}
