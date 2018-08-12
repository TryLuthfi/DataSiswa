package com.dycode.edu.datasiswa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddActivity extends AppCompatActivity {
    @BindView(R.id. edt_name )
    EditText edtName;
    @BindView(R.id. edt_address )
    EditText edtAddress;
    @BindView(R.id. img_add_profile )
    ImageView imgAddProfile;
    @BindView(R.id. btn_add )
    Button btnAdd;
    private SiswaModel mSiswaModel;
    private ArrayList<Image> imageLibrary = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout. activity_add );
        ButterKnife. bind (this);
    }
    @OnClick(R.id. img_add_profile )
    public void onViewClickedImage() {
        ImagePicker. with (this)
                .setFolderMode(true)
                .setMaxSize(10)
                .setMultipleMode(false)
                .setCameraOnly(false)
                .setFolderTitle("Albums")
                .setSelectedImages(imageLibrary)
                .setAlwaysShowDoneButton(true)
                .setKeepScreenOn(true)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            imageLibrary = data.getParcelableArrayListExtra(Config. EXTRA_IMAGES );
        Glide. with (this)
        .load(imageLibrary.get(0).getPath()).into(imgAddProfile);
        }
    }
    
    @OnClick(R.id. btn_add )
    public void onViewClicked() {
        if (!edtName.getText().toString().isEmpty()
                && !edtAddress.getText().toString().isEmpty()
                && !imageLibrary.isEmpty()) {
            mSiswaModel = new SiswaModel();
            mSiswaModel.setName(edtName.getText().toString());
            mSiswaModel.setAddress(edtAddress.getText().toString());
            mSiswaModel.setPathPicture(imageLibrary.get(0).getPath().toString());
            MyApp. db .userDao().insertAll(mSiswaModel);
            Intent intent = new Intent(new Intent(this, MainActivity.class));
            intent.addFlags(Intent. FLAG_ACTIVITY_NEW_TASK );
            intent.addFlags(Intent. FLAG_ACTIVITY_CLEAR_TASK );
            startActivity(intent);
        }
    }
}
