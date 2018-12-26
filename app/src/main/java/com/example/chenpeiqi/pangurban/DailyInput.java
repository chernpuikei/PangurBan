package com.example.chenpeiqi.pangurban;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import static com.example.chenpeiqi.pangurban.Helper.MyLog.i;

public class DailyInput extends AppCompatActivity {

    EditText editText_DailyInput;
    ImageView insertImage, submitRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_input);
        i("DailyInput created>>");
        editText_DailyInput = findViewById(R.id.editText_daily_input);
        insertImage = findViewById(R.id.insert_image);
        submitRecord = findViewById(R.id.submit_record);

        submitRecord.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                i("onClick>>");
                String inputContent = editText_DailyInput.getText().toString();
                Intent intent = new Intent("daily input");
                intent.putExtra("text",inputContent);
                NetworkTransaction.enqueueWork(DailyInput.this,NetworkTransaction.class,
                        NetworkTransaction.jobId_input_daily_content,intent);
                DailyInput.this.finishActivity(BattleField.requestCode_getContent);
            }
        });
    }

}
