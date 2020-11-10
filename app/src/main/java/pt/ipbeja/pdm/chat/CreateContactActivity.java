package pt.ipbeja.pdm.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

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
                Snackbar.make(nameInput, "Escreva o nome.", Snackbar.LENGTH_SHORT).show();
            }
            else {
                // TODO criar e guardar o contacto na BD
            }
        });

    }
}