package pt.ipbeja.pdm.chat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import pt.ipbeja.pdm.chat.data.ChatDatabase;
import pt.ipbeja.pdm.chat.data.ChatMessage;
import pt.ipbeja.pdm.chat.data.Contact;

public class ChatActivity extends AppCompatActivity {

    public static final String CONTACT_KEY = "contact_id";
    private long contactId;
    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        this.contactId = getIntent().getLongExtra(CONTACT_KEY, -1);
        if(contactId == -1) {
            finish();
            return;
        }

        Contact contact = ChatDatabase.getInstance(getApplicationContext())
                .contactDao()
                .getById(contactId);

        List<ChatMessage> messages = ChatDatabase.getInstance(getApplicationContext())
                .messageDao()
                .getAllForContact(contactId);

        RecyclerView messageList = findViewById(R.id.message_list);



        this.adapter = new MessageAdapter(messages);
        messageList.setAdapter(adapter);


        getSupportActionBar().setTitle(contact.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        EditText messageInput = findViewById(R.id.text_message_input);
        FloatingActionButton sendMsgFab = findViewById(R.id.send_message_btn);


        messageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                sendMsgFab.setEnabled(s.length() != 0);
            }
        });

        sendMsgFab.setOnClickListener(v -> {
            String message = messageInput.getText().toString().trim();
            messageInput.setText("");
            ChatMessage chatMessage = new ChatMessage(contactId, message, (int) (Math.random() + 0.5));
            ChatDatabase.getInstance(getApplicationContext())
                    .messageDao()
                    .insert(chatMessage);

            adapter.addMessage(chatMessage);
            messageList.smoothScrollToPosition(adapter.getItemCount());
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_messages) {

            new AlertDialog.Builder(this)
                    .setTitle("Delete messages")
                    .setMessage("Are you sure you want to delete all messages?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        ChatDatabase.getInstance(getApplicationContext())
                                .messageDao()
                                .deleteAllMessages(contactId);
                        adapter.clear();
                    })
                    .setCancelable(false)
                    .setNegativeButton("Cancel", null)
                    .show();



            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void start(Context context, long contactId) {
        Intent starter = new Intent(context, ChatActivity.class);
        starter.putExtra(CONTACT_KEY, contactId);
        context.startActivity(starter);
    }


    class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView messageText = itemView.findViewById(R.id.message_text);

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(ChatMessage message) {
            this.messageText.setText(message.getText());
        }
    }


    class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

        private List<ChatMessage> data;

        public MessageAdapter(List<ChatMessage> data) {
            this.data = data;
        }

        public void addMessage(ChatMessage message) {
            data.add(message);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            int layout = viewType == ChatMessage.INBOUND ? R.layout.message_item_inbound : R.layout.message_item_outbound;
            View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
            holder.bind(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public int getItemViewType(int position) {
            ChatMessage chatMessage = data.get(position);
            return chatMessage.getDirection();
        }

        public void clear() {
            this.data.clear();
            notifyDataSetChanged();
        }
    }
}