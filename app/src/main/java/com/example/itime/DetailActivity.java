package com.example.itime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itime.R;
import com.example.itime.model.Date;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_UPDATE_EVENT= 202;


    private ImageView imageViewPicture;
    private TextView textViewName, textViewTime,textViewDescription,textViewCount;

    private int position;
    private FloatingActionButton fabBack, fabEdit, fabDelete;
    private Date thisDate;

    private long mYear;
    private long mMonth;
    private long mDay;
    private long mHour;
    private long mMin;
    private long mSecond;
    private boolean isRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        position = getIntent().getIntExtra("position", 0);

        thisDate = (Date) getIntent().getSerializableExtra("data2.txt");
        imageViewPicture = findViewById(R.id.image_view_det_pic);
        textViewName = findViewById(R.id.text_view_det_name);
        textViewTime = findViewById(R.id.text_view_det_time);
        textViewDescription=findViewById(R.id.text_view_det_description);
        textViewCount=findViewById(R.id.text_view_det_count);

        mYear=thisDate.between_year();
        mMonth=thisDate.between_month();
        mDay=thisDate.between_day();
        mHour=thisDate.between_hour();
        mMin=thisDate.between_min();
        mSecond=thisDate.between_sec();

        startRun();

        imageViewPicture.setImageResource(thisDate.getImageId());
        textViewName.setText(thisDate.getTitle());
        textViewTime.setText(thisDate.getTime().display_small_time());
        textViewDescription.setText(thisDate.getDescription());


        fabBack = findViewById(R.id.fab_det_back);    //返回按钮
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this,MainActivity.class);
                //将要传递的值附加到Intent对象
                intent.putExtra("edit_position", position);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("data3.txt", thisDate);
                intent.putExtras(mBundle);

                setResult(RESULT_OK, intent);
                DetailActivity.this.finish();


            }
        });

        fabEdit = findViewById(R.id.fab_det_edit);    //编辑按钮
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, NewActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("data4.txt", thisDate);
                intent.putExtras(mBundle);
                intent.putExtra("edit_code", 1);

                startActivityForResult(intent, REQUEST_CODE_UPDATE_EVENT);
            }
        });

        fabDelete = findViewById(R.id.fab_det_delete);    //删除按钮
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle("Do you want to delete this event?");
                builder.setCancelable(true);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                        intent.putExtra("delete_position", position);
                        setResult(RESULT_CANCELED,intent);
                        DetailActivity.this.finish();

                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();

            }
        });

    }

    private Handler timeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1) {
                computeTime();
                textViewCount.setText(mYear+"year  "+mMonth+"month  "+mDay+"day  "+mHour+":"+mMin+":"+mSecond);
            }
        }
    };

    private void startRun() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (isRun) {
                    try {
                        Thread.sleep(1000);
                        Message message = Message.obtain();
                        message.what = 1;
                        timeHandler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束
                    mHour = 23;
                    mDay--;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPDATE_EVENT) {
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra("edit_position", 0);
                Date event5= (Date) data.getSerializableExtra("data5.txt");

                textViewName.setText(event5.getTitle());
                textViewTime.setText(event5.getTime().display_small_time());
                textViewDescription.setText(event5.getDescription());

                Toast.makeText(DetailActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                thisDate=event5;//更新thisDate为修改后

            }
        }
    }

}
