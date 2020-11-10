package pt.ipbeja.pdm.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        EditText messageInput = findViewById(R.id.text_message_input);
        FloatingActionButton sendMsgFab = findViewById(R.id.send_message_btn);

        sendMsgFab.setOnClickListener(v -> {
            String message = messageInput.getText().toString();
            // TODO adicionar a mensagem à BD e à RecyclerView
        });

    }

    // TODO Layouts para mensagens de entrada e de saída

    // TODO MessageViewHolder ...

    // TODO MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> ...
}