package uz.smd.nfcsample1.nfcdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import java.io.File;

import uz.smd.nfcsample1.R;

public class MainDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_main);
    }

    public void callMessageActivity(View view) {
        final Context context = this;
        Intent intent = new Intent(context, SendMessageActivity.class);
        startActivity(intent);
    }

    public void callImageActivity(View view) {
        final Context context = this;
        Intent intent = new Intent(context, SendImageActivity.class);
        startActivity(intent);
    }

    public void transferDoc(View view) {
        final Context context = this;
        Intent intent = new Intent(context, SentDocActivity.class);
        startActivity(intent);

    }

    public void readingnfcTag(View view)
    {
        final Context context = this;
        Intent intent = new Intent(context, readTag.class);
        startActivity(intent);
    }

}