package lt.vcs.baigiamasis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

import lt.vcs.baigiamasis.zaidimukasclasses.Character;

public class CreateCharacterScreenActivity extends AppCompatActivity {

    MaterialButton materialButton1;
    MaterialButton materialButton2;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character_screen);
        Log.i("TEST_TAG", "Create Char - On Create");
        setUpCancelButton();
        setUpCreateButton();
    }

    private void setUpCreateButton(){
        materialButton1 = findViewById(R.id.materialButtonCreateCharacterScreenConfirm);
        editText = findViewById(R.id.editTextCreateCharacter);
        materialButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MainActivity.character = new Character(editText.getText().toString());
                MainActivity.character.writeCharacterToDatabase();

                Intent intent = new Intent(CreateCharacterScreenActivity.this, SecondScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
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

    // TODO: 4/11/2022 Import the character class from Zaidimukas project and create a new object here
    // TODO: 4/11/2022 Save created character into the database
}