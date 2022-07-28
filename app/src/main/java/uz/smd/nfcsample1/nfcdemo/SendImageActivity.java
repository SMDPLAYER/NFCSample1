package uz.smd.nfcsample1.nfcdemo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.provider.MediaStore;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.io.File;

import pl.droidsonroids.gif.GifTextView;
import uz.smd.nfcsample1.R;

public class SendImageActivity extends AppCompatActivity {

    NfcAdapter nfcAdapter;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image);
    }

    public void selectFile(View view) {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        }
        // Check whether Android Beam feature is enabled on device
        else if(!nfcAdapter.isNdefPushEnabled()) {
            Toast.makeText(this, "Please enable Android Beam.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFCSHARING_SETTINGS));
        }
        else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, 1);
        }
    }

    public void sendFile(View view) {

        File fileToTransfer = new File(imgDecodableString);
        fileToTransfer.setReadable(true, false);
        //setContentView(R.layout.sendmessagegif);
        nfcAdapter.setBeamPushUris(new Uri[]{Uri.fromFile(fileToTransfer)}, this);

        GifTextView gifTextView = (GifTextView) findViewById(R.id.gidLoadingId);
            gifTextView.setVisibility(View.VISIBLE);
         Button button4 = (Button) findViewById(R.id.button4);
        button4.setVisibility(View.INVISIBLE);
        gifTextView.animate().alpha(0f).setDuration(11000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
               // ImageView imgView = (ImageView) findViewById(R.id.imgView);
                //imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "Please select an Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

        TextView editText2=(TextView) findViewById(R.id.editText2);
        editText2.setVisibility(View.VISIBLE);

        Button button4 = (Button) findViewById(R.id.button4);
        button4.setVisibility(View.VISIBLE);

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setVisibility(View.INVISIBLE);


    }
    public void fnHome(View view)
    {
        final Context context = this;
        Intent intent = new Intent(context, MainDemoActivity.class);
        startActivity(intent);
    }

    public void sendMessageGIF()
    {

        setContentView(R.layout.sendmessagegif);
    }
}
