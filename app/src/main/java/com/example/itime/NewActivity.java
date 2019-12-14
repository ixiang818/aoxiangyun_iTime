package com.example.itime;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.itime.model.Date;
import com.example.itime.model.OtherCondition;
import com.example.itime.model.SmallDate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class NewActivity extends AppCompatActivity {

    private Date thisDate;

    private EditText editTextTitle, editTextDescription;
    private FloatingActionButton fabBack, fabOk;
    private SmallDate mDate;
    private int editCode;
    private Calendar calendar;
    private int editPosition;

    private ArrayList<OtherCondition> otherConditions = new ArrayList<OtherCondition>();
    private ConditionsArrayAdapter theConditionAdapter;
    ListView listViewNew;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    String condition_date_explain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        //mDate初始化为当前时间
        calendar = Calendar.getInstance();
        mDate = new SmallDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));

        editCode=getIntent().getIntExtra("edit_code", 0);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);

        if(editCode==1) {   //若识别为修改则赋初值
            thisDate = (Date) getIntent().getSerializableExtra("data4.txt");
            editPosition = getIntent().getIntExtra("time_position", 0);
            editTextTitle.setText(thisDate.getTitle());
            editTextDescription.setText(thisDate.getDescription());
        }

        fabBack = findViewById(R.id.fab_edit_back);
        fabOk = findViewById(R.id.fab_edit_ok);

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               NewActivity.this.finish();
            }
        });

        fabOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //将编辑的各个属性装入倒计时事件thisDate中
                    thisDate = new Date(editTextTitle.getText().toString(), editTextDescription.getText().toString(), mDate,R.drawable.normal);

                    if (editCode == 0)  //新建事件
                    {
                        Intent intent = new Intent(NewActivity.this,MainActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("data.txt", thisDate);
                        intent.putExtras(mBundle);
                        setResult(RESULT_OK, intent);
                        NewActivity.this.finish();
                    }
                    else if(editCode==1)    //修改事件
                    {
                        Intent intent = new Intent(NewActivity.this,DetailActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("data5.txt", thisDate);
                        intent.putExtra("edit_position", editPosition);
                        intent.putExtras(mBundle);
                        setResult(RESULT_OK, intent);
                        NewActivity.this.finish();

                    }

            }
        });

        InitData();
        theConditionAdapter = new ConditionsArrayAdapter(this, R.layout.list_view_detail, otherConditions);
        listViewNew = this.findViewById(R.id.list_view_new);
        listViewNew.setAdapter(theConditionAdapter);

        listViewNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String m = String.valueOf(i);    //调试使用
                if (i == 0) {       //选择日期
                    showDailog();
                }
            }
        });

    }

    private void InitData() {
        otherConditions.add(new OtherCondition(R.drawable.clock, "Date", "choose date"));
        otherConditions.add(new OtherCondition(R.drawable.repeat, "Repeat", "none"));
        otherConditions.add(new OtherCondition(R.drawable.picture, "Picture", "The default image"));
    }


    private void showDailog() {     //日历弹出选择框
        calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(NewActivity.this, R.style.Theme_AppCompat_Light_Dialog,
                null, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatePicker datePicker = datePickerDialog.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();

                mDate.setYear(year);
                mDate.setMonth(month);
                mDate.setDay(day);

                //修改详情界面Date的描述
                condition_date_explain = mDate.display_time();
                otherConditions.set(0, new OtherCondition(R.drawable.clock, "Date", condition_date_explain));
                theConditionAdapter.notifyDataSetChanged();

                showTime();  //继续设置时间
            }
        });
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        datePickerDialog.show();

    }

    private void showTime() {       //时间弹出选择框
        timePickerDialog = new TimePickerDialog(this, R.style.Theme_AppCompat_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mDate.setHour(hourOfDay);
                mDate.setMinute(minute);

                //修改详情界面Date的描述
                condition_date_explain = mDate.display_small_time();
                otherConditions.set(0, new OtherCondition(R.drawable.clock, "Date", condition_date_explain));
                theConditionAdapter.notifyDataSetChanged();

            }
        },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        timePickerDialog.show();
        timePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    public class ConditionsArrayAdapter extends ArrayAdapter<OtherCondition>
    {
        private  int resourceId;
        public ConditionsArrayAdapter(@NonNull NewActivity context, @LayoutRes int resource, @NonNull ArrayList<OtherCondition> objects) {
            super(context, resource, objects);
            resourceId=resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater mInflater= LayoutInflater.from(this.getContext());
            View item = mInflater.inflate(this.resourceId,null);

            ImageView con_pic=(ImageView)item.findViewById(R.id.image_view_pic);
            TextView con_name = (TextView)item.findViewById(R.id.text_view_name);
            TextView explain = (TextView)item.findViewById(R.id.text_view_explain);


            OtherCondition condition_item = this.getItem(position);
            con_pic.setImageResource(condition_item.getCon_picture());
            con_name.setText(condition_item.getCon_name());
            explain.setText(condition_item.getCon_explain());

            return item;
        }
    }
}
