package pt.ipbeja.pdm.chat.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ContactWithMessages {

    @Embedded
    private Contact contact;

    @Relation(
            parentColumn = "id",
            entityColumn = "contactId"
    )
    private List<ChatMessage> messages;



    public ContactWithMessages(Contact contact, List<ChatMessage> messages) {
        this.contact = contact;
        this.messages = messages;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }
}
