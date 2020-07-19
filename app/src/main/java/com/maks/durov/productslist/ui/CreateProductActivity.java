package com.maks.durov.productslist.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;
import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maks.durov.productslist.App;
import com.maks.durov.productslist.R;
import com.maks.durov.productslist.entity.Product;
import com.maks.durov.productslist.mvp.ProductPresenter;
import java.time.LocalDateTime;

public class CreateProductActivity extends AppCompatActivity {
    private static final boolean DEFAULT_ITEM_DONE = false;
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 100;
    @Inject
    public ProductPresenter productPresenter;
    private EditText editText;
    private FloatingActionButton floatingActionButton;
    private ImageView imageView;
    private String imagePath;
    private EasyImage easyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((App) getApplication()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        imageView = findViewById(R.id.create_item_image_view);
        editText = findViewById(R.id.create_item_edit_text);
        floatingActionButton = findViewById(R.id.add_photo_fab);
        floatingActionButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_CAMERA_REQUEST_CODE);
            } else {
                makePhoto();
            }
        });
        easyImage = new EasyImage.Builder(this)
                .setFolderName("productImages").allowMultiple(false).setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        easyImage.handleActivityResult(requestCode, resultCode, imageReturnedIntent,
                this, new DefaultCallback() {
                    @Override
                    public void onMediaFilesPicked(@NotNull MediaFile[] mediaFiles,
                                                   @NotNull MediaSource mediaSource) {
                        imagePath = mediaFiles[0].getFile().getPath();
                        Glide.with(CreateProductActivity.this).load(imagePath).into(imageView);
                        imageView.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menu) {
        if (menu.getItemId() == R.id.action_done) {
            addProduct();
        }
        return super.onOptionsItemSelected(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_item_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        productPresenter.onDetachView();
        super.onDestroy();
    }

    private void addProduct() {
        String title = editText.getText().toString();
        if (imagePath != null || title.length() > 0) {
            Product product = new Product();
            product.setDone(DEFAULT_ITEM_DONE);
            product.setImagePath(imagePath);
            product.setTitle(title);
            product.setDateTime(LocalDateTime.now());
            productPresenter.getUncheckedProducts().add(product);
            productPresenter.onDataChanged();
        }
        finish();
    }

    private void makePhoto() {
        easyImage.openChooser(this);
    }
}
