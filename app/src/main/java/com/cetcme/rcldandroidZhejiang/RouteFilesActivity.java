package com.cetcme.rcldandroidZhejiang;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cetcme.rcldandroidZhejiang.MyClass.FileUtil;
import com.qiuhong.qhlibrary.Dialog.QHDialog;
import com.qiuhong.qhlibrary.QHTitleView.QHTitleView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RouteFilesActivity extends AppCompatActivity {

    @BindView(R.id.route_files_listView)
    ListView listView;

    @BindView(R.id.no_data_layout)
    LinearLayout noDataLayout;

    private List<Map<String, Object>> dataList = new ArrayList<>();
    private SimpleAdapter simpleAdapter;

    private EditText newFileNameEditText;

    private String TAG = "RouteFilesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_files);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initListView();
        initNavigationView();
    }

    private void initNavigationView() {
        QHTitleView qHTitleView = (QHTitleView) findViewById(R.id.nav_main_in_route_file_activity);
        qHTitleView.setTitle("轨迹文件");
        qHTitleView.setBackView(R.drawable.icon_back_button);
        qHTitleView.setRightView(0);

        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) qHTitleView.getRightView().getLayoutParams();
        qHTitleView.removeView(qHTitleView.getRightView());

        TextView textView_help = new TextView(this);
        textView_help.setText("帮助");
        textView_help.setTextColor(Color.WHITE);
        textView_help.setTextSize(14);
        textView_help.setGravity(Gravity.CENTER);
        textView_help.setLayoutParams(params1);
        textView_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new QHDialog(RouteFilesActivity.this, "帮助", "1、长按可以删除文件；\n2、网络环境下5秒一次定位，有GPS信号的时候1秒一次定位；\n3、轨迹文件存放在sdcard\\routes下，可自行导出或导入。").show();
            }
        });
        qHTitleView.addView(textView_help);

        qHTitleView.setClickCallback(new QHTitleView.ClickCallback() {

            @Override
            public void onRightClick() {
                Log.i("main","点击了右侧按钮!");
            }

            @Override
            public void onBackClick() {
                Log.i("main","点击了左侧按钮!");
                onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private void initListView() {
        simpleAdapter = new SimpleAdapter(
                RouteFilesActivity.this,
                getData(),
                R.layout.route_file_listview_cell,
                new String[] {"fileName", "fileLength"},
                new int[] {R.id.file_name, R.id.file_length});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                String msg = "文件名称: " + dataList.get(position).get("fileName") + "\n" +
                        "文件大小: " + dataList.get(position).get("fileLength") + "\n" +
                        "修改时间: " + dataList.get(position).get("lastModifyTime");
                QHDialog qhDialog = new QHDialog(RouteFilesActivity.this, "文件详情" , msg);
                qhDialog.setNegativeButton("绘图", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        drawRoute(position);
                    }
                });
                qhDialog.setPositiveButton("重命名", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        renameFile(position);
                    }
                });
                qhDialog.show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                deleteFile(position);
                return false;
            }
        });
    }

    private List<Map<String, Object>> getData() {
        dataList = FileUtil.getFilesData();
        if (dataList == null) {
            return null;
        }
        Collections.sort(dataList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> lhs, Map<String, Object> rhs) {
                return Long.valueOf(lhs.get("lastModifyStamp").toString()).compareTo(Long.valueOf(rhs.get("lastModifyStamp").toString()));
            }
        });
        updateView();
        return dataList;
    }

    public void drawRoute(int Position) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("fileName", dataList.get(Position).get("fileName").toString());
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
//        finish();
        onBackPressed();
    }

    private void renameFile(final int Position) {
        Log.i(TAG, "renameFile: " + Position);
        QHDialog renameDialog = new QHDialog(RouteFilesActivity.this);
        renameDialog.setTitle("重命名");
        View view = LayoutInflater.from(RouteFilesActivity.this).inflate(R.layout.rename_edit_text, null);
        newFileNameEditText = (EditText) view.findViewById(R.id.new_fileName_editText);
        final String oldFileName = dataList.get(Position).get("fileName").toString();
        newFileNameEditText.setText(oldFileName.replace(".txt", ""));
        renameDialog.setContextView(view);
        renameDialog.setPositiveButton("重命名", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!newFileNameEditText.getText().toString().isEmpty()) {
                    dialog.dismiss();
                    String newFileName = newFileNameEditText.getText().toString() + ".txt";
                    FileUtil.renameFile(RouteFilesActivity.this, oldFileName, newFileName);
                    dataList.get(Position).put("fileName", newFileName);
                    simpleAdapter.notifyDataSetChanged();
                }
            }
        });
        renameDialog.setNegativeButton("取消", null);
        renameDialog.show();
    }

    private void deleteFile(final int Position) {
        QHDialog deleteDialog = new QHDialog(RouteFilesActivity.this, "提示", "确认删除\"" + dataList.get(Position).get("fileName") +"\"?");
        deleteDialog.setPositiveButton("删除", R.drawable.button_background_alert, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                FileUtil.deleteFile(dataList.get(Position).get("fileName").toString());
                dataList.remove(Position);
                simpleAdapter.notifyDataSetChanged();
                updateView();
            }
        });
        deleteDialog.setNegativeButton("取消", null);
        deleteDialog.show();
    }

    private void updateView() {
        if (dataList.size() == 0) {
            listView.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            noDataLayout.setVisibility(View.GONE);
        }
    }


}
