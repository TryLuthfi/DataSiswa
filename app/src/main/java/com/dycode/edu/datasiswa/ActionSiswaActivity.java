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

public class ActionSiswaActivity extends AppCompatActivity {

    @BindView(R.id.edt_value_name)
    EditText edtValueName;
    @BindView(R.id.edt_value_address)
    EditText edtValueAddress;
    @BindView(R.id.img_value_profile)
    ImageView imgValueProfile;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    private SiswaModel siswaModel;
    private ArrayList<Image> imageLibrary = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_siswa);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        siswaModel = bundle.getParcelable(MainAdapter.EXTRA_SISWA);

        edtValueName.setText(siswaModel.getName());
        edtValueAddress.setText(siswaModel.getAddress());
        Glide.with(this)
                .load(siswaModel.getPathPicture())
                .into(imgValueProfile);
    }

    @OnClick(R.id.img_value_profile)
    public void onImgValueProfileClicked() {
        ImagePicker.with(this)
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
        if (data != null) {
            imageLibrary = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            siswaModel.setPathPicture(imageLibrary.get(0).getPath());

            Glide.with(this)
                    .load(imageLibrary.get(0).getPath()).into(imgValueProfile);
        }
    }

    @OnClick(R.id.btn_update)
    public void onBtnUpdateClicked() {
        siswaModel.setName(edtValueName.getText().toString());
        siswaModel.setAddress(edtValueAddress.getText().toString());
        siswaModel.setPathPicture(siswaModel.getPathPicture());
        MyApp.db.userDao().update(siswaModel);
        startActivity(new Intent(this, MainActivity.class));
        Intent intent = new Intent(new Intent(this, MainActivity.class));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.btn_delete)
    public void onBtnDeleteClicked(){
        MyApp.db.userDao().deleteUser(siswaModel);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}