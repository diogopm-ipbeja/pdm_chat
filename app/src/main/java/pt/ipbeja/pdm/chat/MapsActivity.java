package pt.ipbeja.pdm.chat;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import pt.ipbeja.pdm.chat.data.ChatDatabase;
import pt.ipbeja.pdm.chat.data.Contact;
import pt.ipbeja.pdm.chat.data.Position;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<Contact> contacts = ChatDatabase.getInstance(getApplicationContext())
                .contactDao()
                .getAll();

        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();

        for (Contact contact : contacts) {
            Position p = contact.getPosition();
            LatLng latLng = new LatLng(p.getLat(), p.getLng());
            latLngBuilder.include(latLng);
            Marker marker = mMap.addMarker(
                    new MarkerOptions()
                            .position(latLng)
                            .title(contact.getName())
            );
            marker.setTag(contact);
        }
        LatLngBounds bounds = latLngBuilder.build();

        CameraUpdate camera = CameraUpdateFactory.newLatLngBounds(bounds, 50);
        mMap.animateCamera(camera);

        mMap.setOnInfoWindowClickListener(marker -> {
            Contact contact = (Contact) marker.getTag();
            ChatActivity.start(MapsActivity.this, contact.getId());

        });
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, MapsActivity.class);

        context.startActivity(starter);
    }
}