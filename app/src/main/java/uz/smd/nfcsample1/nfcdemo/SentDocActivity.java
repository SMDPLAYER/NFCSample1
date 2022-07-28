package uz.smd.nfcsample1.nfcdemo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import pl.droidsonroids.gif.GifTextView;
import uz.smd.nfcsample1.R;


public class SentDocActivity extends AppCompatActivity {

    NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_doc);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    public void SelectDocToSend(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file*//*");
        startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();


                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                    cursor.moveToFirst();


                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();


                    GifTextView gifTextView = (GifTextView) findViewById(R.id.gidLoadingId);
                    gifTextView.setVisibility(View.VISIBLE);
                    Button button = (Button) findViewById(R.id.button);
                    button.setVisibility(View.INVISIBLE);
                    gifTextView.animate().alpha(0f).setDuration(11000);

                    TextView editText2=(TextView) findViewById(R.id.editText2);
                    editText2.setVisibility(View.VISIBLE);
                    editText2.animate().alpha(0f).setDuration(11000);


                    File fileToTransfer = new File(imgDecodableString);
                    fileToTransfer.setReadable(true, false);

                    nfcAdapter.setBeamPushUris(new Uri[]{Uri.fromFile(fileToTransfer)}, this);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void fnHome(View view)
    {
        final Context context = this;
        Intent intent = new Intent(context, MainDemoActivity.class);
        startActivity(intent);
    }
}
