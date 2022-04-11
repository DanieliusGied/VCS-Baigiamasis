package lt.vcs.baigiamasis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class FirstScreenActivity extends AppCompatActivity {

    MaterialButton materialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        setUpCreateNewCharacterButton();
    }

    private void setUpCreateNewCharacterButton(){
        materialButton = findViewById(R.id.materialButtonFirstScreenCreateNewCharacter);
        materialButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstScreenActivity.this, CreateCharacterScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // TODO: 4/11/2022 Create a database connection that loads a character from the database

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TEST_TAG", "FirstScreen On Destroy");
    }
}