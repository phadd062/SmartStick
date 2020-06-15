package com.google.maps.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Pop extends Activity implements PopupInfo{
    //String danger="init";
    PopupInfo danger;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int height = dm.heightPixels;
        int width = dm.widthPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));
        final Button button = findViewById(R.id.inputButton);
        final EditText mEdit = findViewById(R.id.editText);
        String danger="t";;


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here


                //Intent intent = new Intent();
               // intent.putExtra("message", mEdit.getText().toString());
                //setResult(RESULT_OK, intent);
              // danger=mEdit.getText().toString();

              //  setDangerousAreaInput(danger);
                finish();

//mEdit.getText().toString()

                   // intent.putExtra("message", "tester");
                   // Bundle bundle = getIntent().getExtras();
                   // finish();
                   // startActivity(intent);


                //dangerousAreaInput=(mEdit.getText().toString());


            }
        });

    }
    public PopupInfo getDangerousAreaInput(){
        return danger;
    }


    public void setDangerousAreaInput(PopupInfo dangerr){
         danger=dangerr;
    }

}

