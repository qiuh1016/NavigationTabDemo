package com.cetcme.rcldandroidZhejiang.MyClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cetcme.rcldandroidZhejiang.R;

/**
 * Created by qiuhong on 9/6/16.
 */
public class TodoListCell extends LinearLayout implements View.OnClickListener {

    private TextView nameTextView;
    private TextView timeTextView;
    private LinearLayout linearLayout;

    private ClickCallback callback;

    public TodoListCell(Context context, String name, String time, int backgroundResource) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.todo_list_cell, this, true);

        linearLayout = (LinearLayout) view.findViewById(R.id.list_cell);
        linearLayout.setBackgroundResource(backgroundResource);
        linearLayout.setOnClickListener(this);

        nameTextView = (TextView) view.findViewById(R.id.name_text_in_todo_list_cell);
        timeTextView = (TextView) view.findViewById(R.id.time_text_in_todo_list_cell);
        nameTextView.setText(name);
        timeTextView.setText(time);

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
