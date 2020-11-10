package pt.ipbeja.pdm.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import pt.ipbeja.pdm.chat.data.Contact;

public class MainActivity extends AppCompatActivity {

    private ContactAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView list = findViewById(R.id.contact_list);
        FloatingActionButton createContactFab = findViewById(R.id.create_contact_fab);


        this.adapter = new ContactAdapter();
        list.setAdapter(this.adapter);
        // LinearLayoutManager já está definido no XML

        createContactFab.setOnClickListener(v -> {
            // TODO abrir ecrã para criar um contacto (ver starter)
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        List<Contact> contacts = new ArrayList<>(); // TODO pedir a lista de contactos à BD
        this.adapter.setData(contacts);
    }

    private class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView name = itemView.findViewById(R.id.contact_item_name);

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                // TODO abrir chat do contacto (ver starter)
            });
        }

        public void bind(Contact contact) {
            // TODO colocar o nome do contacto em 'name'
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