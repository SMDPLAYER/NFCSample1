package uz.smd.nfcsample1

import android.content.Context
import android.media.RingtoneManager
import android.nfc.*
import android.nfc.tech.Ndef
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException


class BeamActivity : AppCompatActivity(), NfcAdapter.ReaderCallback {

    private lateinit var mNfcAdapter: NfcAdapter
    private lateinit var mUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beam)
        val mEtWeb = findViewById<EditText>(R.id.etAmount)
        val mBtnSend = findViewById<Button>(R.id.btnSend)

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)

        //We check that the device has NFC technology
        if (mNfcAdapter == null) {
            Toast.makeText(this, "This device does not have NFC technology.", Toast.LENGTH_LONG).show()
        } else {
            //We check that the NFC antenna is on
            if (!mNfcAdapter.isEnabled) {
                Toast.makeText(this, "Nfc antenna deactivated.", Toast.LENGTH_LONG).show()
            }
        }

        mBtnSend.setOnClickListener { v ->
            mUrl = "${"https://www."}${mEtWeb.text}${".com"}"
            runOnUiThread {
                Toast.makeText(this, "Created url: $mUrl . Bring the device close to the NFC tag"  , Toast.LENGTH_LONG).show()
            }
            v.hideKeyboard()
        }

    }

    override fun onTagDiscovered(tag: Tag) {
        // NFC Tags can support different technologies. In this case we will use
        // Ndef technology
        var mNdef = Ndef.get(tag);

        if (mNdef != null) {

            // We create a Ndef Record from a Uri with our Url
            var mRecord = NdefRecord.createUri(mUrl);

            // We add the NdefRecord to our NdefMessage
            var mNdefMsg = NdefMessage(mRecord)

            //We try to open a connection and write the Tag with our NdefMessage
            try {
                mNdef.connect();
                mNdef.writeNdefMessage(mNdefMsg);

                // If the Tag was written successfully we show the Toast
                runOnUiThread {
                    Toast.makeText(this, "Successfully written tag", Toast.LENGTH_SHORT).show()
                }

                // Make a Sound
                try {
                    var notification =
                        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    var ringtone = RingtoneManager.getRingtone(
                        applicationContext,
                        notification
                    );
                    ringtone.play();
                } catch (e: Exception) {
                    // Some error playing sound
                    runOnUiThread {
                        Toast.makeText(this, "Error trying to write", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            } catch (e: Exception) {
                // Here we enter if the Tag is invalid
            } finally {
                // We close the connection with the Tag (Let's avoid errors and misuse of resources)
                try {
                    mNdef.close();
                } catch (e: IOException) {
                    // We show a message in case the operation has been interrupted
                    runOnUiThread {
                        Toast.makeText(this, "Error trying to write", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            runOnUiThread {
                Toast.makeText(this, "Tag Invalid", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (mNfcAdapter != null) {
            val options = Bundle()
            // We add a few extra milliseconds for the Tag to be read correctly
            options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250)

            // We enable the reading mode so that it detects the Tag
            mNfcAdapter.enableReaderMode(
                this,
                this,
                NfcAdapter.FLAG_READER_NFC_A or
                        NfcAdapter.FLAG_READER_NFC_B or
                        NfcAdapter.FLAG_READER_NFC_F or
                        NfcAdapter.FLAG_READER_NFC_V,
                options
            )
        }
    }

    override fun onPause() {
        super.onPause()
        // We disable the reading mode so that we only detect Tags with the App in the foreground
        mNfcAdapter.disableReaderMode(this);
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}