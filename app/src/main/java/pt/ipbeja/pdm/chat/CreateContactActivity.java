package pt.ipbeja.pdm.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import pt.ipbeja.pdm.chat.data.ChatDatabase;
import pt.ipbeja.pdm.chat.data.Contact;

public class CreateContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        EditText nameInput = findViewById(R.id.contact_name_input);
        Button createBtn = findViewById(R.id.create_contact_btn);

        createBtn.setOnClickListener(v -> {
            String input = nameInput.getText().toString();
            if(input.isEmpty()) {
                Snackbar.make(nameInput, R.string.create_contact_missing_name, Snackbar.LENGTH_SHORT).show();
            }
            else {
                Contact contact = new Contact(input);
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
}