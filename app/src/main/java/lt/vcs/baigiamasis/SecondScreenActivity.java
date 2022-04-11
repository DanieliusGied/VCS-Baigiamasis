package lt.vcs.baigiamasis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static lt.vcs.baigiamasis.MainActivity.character;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class SecondScreenActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton1;
    FloatingActionButton floatingActionButton2;

    // TODO: 4/11/2022 DELETE THE WHOLE ACTIVITY AND MOVE CHAR CREATION TO FIRST SCREEN, CHANGE THE NAME 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        TextView textView = (TextView) findViewById(R.id.textViewSecondScreen);
        textView.setText("Welcome " + character.getName());

        setUpCharacterInfoScreenButton();
        setUpInventoryScreenButton();
    }

    private void setUpCharacterInfoScreenButton(){
        floatingActionButton1 = findViewById(R.id.floatingActionButtonCharacterInfoScreen);
        floatingActionButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondScreenActivity.this, CharacterInfoScreenActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpInventoryScreenButton(){
        floatingActionButton2 = findViewById(R.id.floatingActionButtonInventoryScreen);
        floatingActionButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondScreenActivity.this, InventoryScreenActivity.class);
                startActivity(intent);
            }
        });
    }

}