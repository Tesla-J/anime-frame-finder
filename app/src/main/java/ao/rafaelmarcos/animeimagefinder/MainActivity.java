package ao.rafaelmarcos.animeimagefinder;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton mFab;
    ImageView mToSearchImage;


    ActivityResultLauncher<String> mImageSelector = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri o) {
                    mToSearchImage.setImageURI(o);
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFab = findViewById(R.id.fab);
        mToSearchImage = findViewById(R.id.to_search_image);

        // searching image
        mFab.setOnClickListener(this::imageSelector);
    }

    private void imageSelector(View v){
        mImageSelector.launch("image/*");
    }
}