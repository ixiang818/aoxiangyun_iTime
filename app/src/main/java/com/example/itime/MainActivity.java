package com.example.itime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itime.model.Date;
import com.example.itime.model.SmallDate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_NEW_EVENT = 201;
    private static final int REQUEST_CODE_DETAILS = 203;

    ListView listViewDates;
    private ArrayList<Date> Dates = new ArrayList<>();
    private DateAdapter dateAdapter;
    private FloatingActionButton fabAdd;

    private int deleteItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateAdapter=new DateAdapter(this, R.layout.list_view_item_date,Dates);
        listViewDates=this.findViewById(R.id.list_view_date);
        listViewDates.setAdapter(dateAdapter);

        Init();

        fabAdd=findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, NewActivity.class);
                intent.putExtra("edit_code",0);
                startActivityForResult(intent,REQUEST_CODE_NEW_EVENT);
            }
        });

        listViewDates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Date date=Dates.get(position);
                Intent intent=new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("position",position);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("data2.txt",date);
                intent.putExtras(mBundle);
                startActivityForResult(intent, REQUEST_CODE_DETAILS);

                Toast.makeText(MainActivity.this, date.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Init()
    {
        SmallDate smallDate=new SmallDate(2000,3,22,0,0,0);
        Dates.add(new Date("Birthday","date of birth",smallDate,R.drawable.birthday));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case REQUEST_CODE_NEW_EVENT:
                if (resultCode == RESULT_OK) {

                    Date event1= (Date) data.getSerializableExtra("data.txt");
                    Dates.add(event1);
                    dateAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "新建成功", Toast.LENGTH_SHORT).show();

                }
                break;
            case REQUEST_CODE_DETAILS:
                if (resultCode == RESULT_OK) {
                    int position = data.getIntExtra("edit_position", 0);

                    Date event3= (Date) data.getSerializableExtra("data3.txt");
                    Dates.set(position,event3);
                    dateAdapter.notifyDataSetChanged();
                }
                else if (resultCode == RESULT_CANCELED){
                    deleteItemPosition=data.getIntExtra("delete_position",0);
                    Dates.remove(deleteItemPosition);
                    dateAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();

                }
                break;
        }

    }


    public class DateAdapter extends ArrayAdapter<Date> {

        private int resourceId;
        public DateAdapter(Context context, int resource, List<Date> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Date date = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ((ImageView) view.findViewById(R.id.date_image)).setImageResource(date.getImageId());
            ((TextView) view.findViewById(R.id.date_time_difference)).setText(date.time_difference());
            ((TextView) view.findViewById(R.id.date_title)).setText(date.getTitle());
            ((TextView) view.findViewById(R.id.date_time)).setText(date.getTime().display_time());
            ((TextView) view.findViewById(R.id.date_description)).setText(date.getDescription());

            return view;
        }
    }
}
