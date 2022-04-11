package lt.vcs.baigiamasis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static lt.vcs.baigiamasis.MainActivity.character;

import org.w3c.dom.Text;

public class SecondScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        TextView textView = (TextView) findViewById(R.id.textViewSecondScreen);
        textView.setText("Welcome " + character.getName());
    }
}