package ao.rafaelmarcos.animeimagefinder;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton mFab;
    private ImageView mSelectedImage;
    private Uri mImageUri;

    // Service
    private SearchService mService;
    private boolean mIsBound;


    ActivityResultLauncher<String> mImageSelector = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri o) {
                    // Shows the selected image
                    mImageUri = o;
                    mSelectedImage.setImageURI(mImageUri);
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFab = findViewById(R.id.fab);
        mSelectedImage = findViewById(R.id.to_search_image);

        // search image button
        mFab.setOnClickListener(this::selectImage);
    }

    @Override
    public void onStart(){
        super.onStart();
        // Bind to SearchService
        Intent intent = new Intent(this, SearchService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop(){
        super.onStop();
        unbindService(connection);
        mIsBound = false;
    }

    private void selectImage(View v){
        // selecting the image
        mImageSelector.launch("image/*");
    }

    private void searchImage(Uri uri){
        // TODO serching on service
        if(mIsBound)
            Toast.makeText(this, "" + mService.getNumber(), Toast.LENGTH_LONG).show();
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SearchService.LocalBinder binder = (SearchService.LocalBinder) service;
            mService = binder.getService();
            mIsBound = true;

            // If an image has been selected
            if(mImageUri != null) {
                searchImage(mImageUri);
                mImageUri = null;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBound = false;
        }
    };
}