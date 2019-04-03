package com.andy.bottom.tablayout;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by luofan on 2018/10/10.
 */

public class BottomTabItem extends FrameLayout {

    private TextView mTvTab;

    private ImageView mTabIcon;

    private TextView mMessageCount;

    public BottomTabItem(@NonNull Context context) {
        this(context, null);
    }

    public BottomTabItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_bottom_tab, this, true);
        mTvTab = findViewById(R.id.tv_tab_name);
        mMessageCount = findViewById(R.id.tv_message_count);
        mTabIcon = findViewById(R.id.img_tab_icon);
    }

    public void setTabName(String name) {
        mTvTab.setText(name);
    }

    public void setTabIconRes(int tabIconSelector) {
        mTabIcon.setImageResource(tabIconSelector);
    }

    public void setTabMessageCount(int count) {
        initUnReadView(mMessageCount, count);
    }

    public TextView getTabTextView() {
        return mTvTab;
    }

    /**
     * 添加未读提示
     *
     * @param textView 所在textView右上角
     * @param count    数字（最大99+）
     */
    private static void initUnReadView(TextView textView, int count) {
        Resources resources = textView.getResources();
        if (count > 0) {
            textView.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
            int width;
            if (count > 9) {
                width = resources.getDimensionPixelOffset(R.dimen.dimen_unread_count_width_2);
            } else {
                width = resources.getDimensionPixelOffset(R.dimen.dimen_unread_count_width);
            }
            layoutParams.width = width;
            textView.setLayoutParams(layoutParams);
        } else {
            textView.setVisibility(View.GONE);
        }
        if (count > 99) {
            textView.setText("99+");
        } else {
            textView.setText("" + count);
        }
        textView.setGravity(Gravity.CENTER);
    }

}
