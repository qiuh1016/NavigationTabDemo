package com.cetcme.rcldandroidZhejiang.MyClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cetcme.rcldandroidZhejiang.R;

/**
 * Created by qiuhong on 8/25/16.
 */
public class ListCell extends LinearLayout implements View.OnClickListener {

    private TextView textView;
    private ImageView imageView;
    private LinearLayout linearLayout;

    private ClickCallback callback;

    public ListCell(Context context, String text, int backgroundResource) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.list_cell, this, true);

        linearLayout = (LinearLayout) view.findViewById(R.id.list_cell);
        linearLayout.setBackgroundResource(backgroundResource);
        linearLayout.setOnClickListener(this);

        textView = (TextView) view.findViewById(R.id.textView_in_List_Cell);
        textView.setText(text);

        imageView = (ImageView) view.findViewById(R.id.imageView_in_List_Cell);

    }

    /**
     * 设置按钮点击回调接口
     * @param callback
     */
    public void setClickCallback(ClickCallback callback) {
        this.callback = callback;
    }

    public interface ClickCallback {
        /**
         * 点击返回按钮回调
         */
        void onClick();
    }

    @Override
    public void onClick(View v) {
        callback.onClick();
    }

    public void setImageViewVisibility (boolean visible) {
        imageView.setVisibility(visible? VISIBLE : INVISIBLE);
    }

    public void setTextSize(int textSize) {
        textView.setTextSize(textSize);
    }

    public void removeImageView(){
        linearLayout.removeView(imageView);
    }

}
