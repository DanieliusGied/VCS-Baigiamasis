package lt.vcs.baigiamasis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SecondScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        TextView textView = findViewById(R.id.textViewSecondScreen);
        textView.setText("Welcome " + MainActivity.character.name);
    }
}