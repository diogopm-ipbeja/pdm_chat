package pt.ipbeja.pdm.chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import pt.ipbeja.pdm.chat.data.ChatDatabase;
import pt.ipbeja.pdm.chat.data.Contact;
import pt.ipbeja.pdm.chat.data.Position;

public class CreateContactActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int PHOTO_REQUEST_CODE = 1001;

    private Marker marker;
    private ImageView photoImageView;
    private Bitmap photoBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        EditText nameInput = findViewById(R.id.contact_name_input);
        Button createBtn = findViewById(R.id.create_contact_btn);
        this.photoImageView = findViewById(R.id.contact_photo);

        createBtn.setOnClickListener(v -> {
            String input = nameInput.getText().toString();
            if(input.isEmpty() || marker == null) {
                Snackbar.make(nameInput, R.string.create_contact_missing_name, Snackbar.LENGTH_SHORT).show();
            }
            else {
                LatLng latLng = marker.getPosition();
                Position position = new Position(latLng.latitude, latLng.longitude);

                String filename = null;

                // Vamos converter um bitmap para bytes
                byte[] photoBytes = BitmapUtils.toBytes(photoBitmap);

                if(photoBytes != null) {
                    // https://developer.android.com/training/data-storage
                    File folder = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    filename = UUID.randomUUID().toString() + ".jpg"; // Um filename aleatório (podiamos usar um timestamp)
                    File file = new File(folder, filename);
                    try {
                        Files.write(file.toPath(), photoBytes); // gravar os bytes no ficheiro
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // Atenção que este exemplo mostra 2 formas de guardar os bytes do bitmap
                // Por um lado guarda os bytes na BD (menos correcto, mas ok para 'poucos' bytes, ver BLOB) - photoBytes
                // Por outro guarda o caminho para onde o ficheiro está guardado (mais correcto)  - filename
                Contact contact = new Contact(input, position, photoBytes, filename);
                ChatDatabase.getInstance(getApplicationContext())
                        .contactDao()
                        .insert(contact);
                finish();
            }
        });

        this.photoImageView.setOnClickListener(v -> takePhoto());


    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PHOTO_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PHOTO_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            this.photoBitmap = data.getParcelableExtra("data");
            photoImageView.setImageBitmap(photoBitmap);
        }
        else super.onActivityResult(requestCode, resultCode, data);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, CreateContactActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setOnMapClickListener(latLng -> {

            if(this.marker == null) {
                this.marker = googleMap.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                );
            }
            else {
                this.marker.setPosition(latLng);
            }
        });

    }
}













