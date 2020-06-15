package com.example.lcapitulino.nunavikhomeservices;

import android.content.Context;
import android.widget.Toast;

public class MyToast {
    static void display(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
