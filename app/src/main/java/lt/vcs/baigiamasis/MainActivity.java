package lt.vcs.baigiamasis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runFirstScreen();
    }

    private void runFirstScreen(){
        Intent intent = new Intent(MainActivity.this, FirstScreenActivity.class);
        startActivity(intent);
    }
}