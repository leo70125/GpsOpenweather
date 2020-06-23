package com.example.gpsopenweather;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {
    private TextView tv;
    private DataMeanger dataMeanger;
    private String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_2);
        int pos = getIntent().getIntExtra("detail",0);
        dataMeanger = DataMeanger.getInstance();
        Detail detail = dataMeanger.getDetails().get(pos);


        tv = findViewById(R.id.tv_total);
        tv.setText(detail.getDt() + "\n" + detail.getTemp()+ " °C"  +  "\n" + detail.getTemp_F()  +"\n"+ " 紫外線  "+detail.getUV() + "\n"  +" 濕度  "+ detail.getavghumidity()+ "\n" +detail.getDescription());

    }
}
