package com.cetcme.zytyumin.MyClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cetcme.zytyumin.R;

/**
 * Created by qiuhong on 8/28/16.
 */
public class ListCellWithNumber extends LinearLayout implements View.OnClickListener {

    private TextView textView;
    public TextView numberTextView;
    private LinearLayout linearLayout;

    private ClickCallback callback;

    public ListCellWithNumber(Context context, String text, int backgroundResource) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.list_cell_with_number, this, true);

        linearLayout = (LinearLayout) view.findViewById(R.id.list_cell_with_number);
        linearLayout.setBackgroundResource(backgroundResource);
        linearLayout.setOnClickListener(this);

        textView = (TextView) view.findViewById(R.id.textView_in_List_Cell_with_number);
        textView.setText(text);

        numberTextView = (TextView) view.findViewById(R.id.numberTextView_in_List_Cell_with_number);

    }

    /**
     * 设置按钮点击回调接口
     * @param callback
     */
    public void setClickCallback(ClickCallback callback) {
        this.callback = callback;
    }

    public interface ClickCallback{
        /**
         * 点击返回按钮回调
         */
        void onClick();
    }

    @Override
    public void onClick(View v) {
        callback.onClick();
    }
}
