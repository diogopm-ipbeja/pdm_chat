package pt.ipbeja.pdm.chat;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import pt.ipbeja.pdm.chat.data.ChatDatabase;
import pt.ipbeja.pdm.chat.data.Contact;

public class MainActivity extends AppCompatActivity {

    private ContactAdapter adapter;

    private View emptyContactsHint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView list = findViewById(R.id.contact_list);
        FloatingActionButton createContactFab = findViewById(R.id.create_contact_fab);
        this.emptyContactsHint = findViewById(R.id.no_contacts_hint);


        this.adapter = new ContactAdapter();
        list.setAdapter(this.adapter);
        // LinearLayoutManager já está definido no XML

        createContactFab.setOnClickListener(v -> CreateContactActivity.start(MainActivity.this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.view_contacts_map) {

            MapsActivity.start(this);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshContacts();
    }

    private void refreshContacts() {
        List<Contact> contacts = ChatDatabase.getInstance(getApplicationContext())
                .contactDao()
                .getAll();

        String contactCount = getResources().getQuantityString(R.plurals.contact_count, contacts.size(), contacts.size());
        getSupportActionBar().setSubtitle(contactCount);

        this.emptyContactsHint.setVisibility(contacts.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        this.adapter.setData(contacts);
    }

    private class ContactViewHolder extends RecyclerView.ViewHolder {

        private final TextView name = itemView.findViewById(R.id.contact_item_name);
        private final ImageView photo = itemView.findViewById(R.id.contact_item_avatar);
        private Contact contact;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> ChatActivity.start(MainActivity.this, contact.getId()));

            itemView.setOnLongClickListener(v -> {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete contact")
                        .setMessage("Are you sure you want to delete " + contact.getName() + "?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            ChatDatabase.getInstance(getApplicationContext())
                                    .contactDao()
                                    .delete(contact);
                            refreshContacts();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                return true;
            });
        }

        public void bind(Contact contact) {
            this.contact = contact;
            this.name.setText(contact.getName());

            String photoPath = contact.getPhotoPath();

            // https://github.com/bumptech/glide
            // Biblioteca para carregar imagens (ficheiros, web, bytes...)

            if(photoPath != null && !photoPath.isEmpty()) {
                File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                File file = new File(externalFilesDir, photoPath);
                try {
                    byte[] photoBytes = Files.readAllBytes(file.toPath());
                    Bitmap bitmap = BitmapUtils.toBitmap(photoBytes);
                    this.photo.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                this.photo.setImageResource(R.drawable.ic_baseline_person_pin_24);
            }

            /*byte[] photoThumbnail = contact.getPhotoThumbnail();


            if(photoThumbnail != null) {
                Bitmap bm = BitmapUtils.toBitmap(photoThumbnail);
                this.photo.setImageBitmap(bm);
            }
            else {
                this.photo.setImageResource(R.drawable.ic_baseline_person_pin_24);
            }*/
        }
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {

        private List<Contact> data = new ArrayList<>();

        public void setData(List<Contact> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
            return new ContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
            holder.bind(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

}