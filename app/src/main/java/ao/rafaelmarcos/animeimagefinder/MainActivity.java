package ao.rafaelmarcos.animeimagefinder;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    ImageView toSearchImage;

    // result code
    private int SELECTION_RESULT_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        toSearchImage = findViewById(R.id.to_search_image);

        // searching image
        fab.setOnClickListener(this::imageSelector);
    }

    private void imageSelector(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        // call image selector
        startActivityForResult(intent, SELECTION_RESULT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == SELECTION_RESULT_CODE){
            Uri selectedImageUri = data.getData();

            if(selectedImageUri != null)
                toSearchImage.setImageURI(selectedImageUri);
        }
    }
}