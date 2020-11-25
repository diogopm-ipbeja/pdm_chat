package pt.ipbeja.pdm.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import pt.ipbeja.pdm.chat.data.ChatDatabase;
import pt.ipbeja.pdm.chat.data.Contact;
import pt.ipbeja.pdm.chat.data.Position;

public class CreateContactActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().
                        findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        EditText nameInput = findViewById(R.id.contact_name_input);
        Button createBtn = findViewById(R.id.create_contact_btn);

        createBtn.setOnClickListener(v -> {
            String input = nameInput.getText().toString();
            if(input.isEmpty() || marker == null) {
                Snackbar.make(nameInput, R.string.create_contact_missing_name, Snackbar.LENGTH_SHORT).show();
            }
            else {
                LatLng latLng = marker.getPosition();
                Position position = new Position(latLng.latitude, latLng.longitude);
                Contact contact = new Contact(input, position);
                ChatDatabase.getInstance(getApplicationContext())
                        .contactDao()
                        .insert(contact);
                finish();
            }
        });

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













