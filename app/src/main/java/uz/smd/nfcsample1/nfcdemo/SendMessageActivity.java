package uz.smd.nfcsample1.nfcdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import uz.smd.nfcsample1.R;


public class SendMessageActivity extends Activity implements NfcAdapter.CreateNdefMessageCallback, NfcAdapter.OnNdefPushCompleteCallback {

    NfcAdapter nfcAdapter;
    TextView textInfo;
    TextView textInfoText;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        textInfo = (TextView)findViewById(R.id.receivedMessage);
        textInfoText = (TextView)findViewById(R.id.receivedMessageText);
        editText = (EditText) findViewById(R.id.editText);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "nfcAdapter==null, no NFC adapter exists", Toast.LENGTH_LONG).show();
        } else {
            nfcAdapter.setNdefPushMessageCallback(this, this);
            nfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }
        onNewIntent(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String action = intent.getAction();
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){
            Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage inNdefMessage = (NdefMessage)parcelables[0];
            NdefRecord[] inNdefRecords = inNdefMessage.getRecords();
            NdefRecord NdefRecord_0 = inNdefRecords[0];
            String inMsg = new String(NdefRecord_0.getPayload());
            textInfo.setText(inMsg);
            textInfo.setVisibility(View.VISIBLE);
            textInfoText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {
        final String eventString = "Message sent successfully";
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
            Toast.makeText(getApplicationContext(), eventString, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String stringOut = editText.getText().toString();
        byte[] bytesOut = stringOut.getBytes();

        NdefRecord appRecord = NdefRecord.createApplicationRecord("uz.smd.nfcsample1");
        NdefRecord ndefRecordOut = NdefRecord.createMime("text/plain",bytesOut);
        NdefMessage ndefMessageout = new NdefMessage(ndefRecordOut, appRecord);
        return ndefMessageout;
    }

public void sendMessageGIF()
{

    setContentView(R.layout.sendmessagegif);
}

public void fnHome(View view)
{
    final Context context = this;
    Intent intent = new Intent(context, MainDemoActivity.class);
    startActivity(intent);
}
}
