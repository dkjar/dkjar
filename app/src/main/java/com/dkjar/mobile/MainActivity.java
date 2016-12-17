package com.dkjar.mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dkjar.welcome.WelcomeActivity;
import com.dkjar.welcome.WelcomeHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        WelcomeHelper sampleWelcomeScreen = new WelcomeHelper(this, WelcomeActivity.class);
        sampleWelcomeScreen.setShowTime(3);
        sampleWelcomeScreen.setCallBack(new WelcomeHelper.WelcomeCallBack() {
            @Override
            public void launcher() {
                create();
            }
        });
        sampleWelcomeScreen.show(savedInstanceState);
    }

    public void create(){

        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        Button b = (Button) this.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(TokenActivity.createIntent());
            }
        });

        EditText text = (EditText) this.findViewById(R.id.editText2);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println("a " + editable.toString());

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
