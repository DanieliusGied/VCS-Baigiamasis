package lt.vcs.baigiamasis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class CreateCharacterScreenActivity extends AppCompatActivity {

    MaterialButton materialButton1;
    MaterialButton materialButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character_screen);
        Log.i("TEST_TAG", "Create Char - On Create");
        setUpCancelButton();
    }

    private void setUpCancelButton(){
        materialButton2 = findViewById(R.id.materialButtonCreateCharacterScreenCancel);
        materialButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateCharacterScreenActivity.this, FirstScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setUpCreateButton(){
        materialButton1 = findViewById(R.id.materialButtonCreateCharacterScreenConfirm);
        materialButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateCharacterScreenActivity.this, FirstScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // TODO: 4/11/2022 Import the character class from Zaidimukas project and create a new object here
    // TODO: 4/11/2022 Save created character into the database
}