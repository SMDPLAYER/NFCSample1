package uz.smd.nfcsample1.nfcdemo;

import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import uz.smd.nfcsample1.R;

/**
 * Created by Swapnil on 4/25/2017.
 */

public class readTag extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }
}
