package pt.ipbeja.pdm.chat.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "messages")
public class ChatMessage {

    public static final int INBOUND = 0;
    public static final int OUTBOUND = 1;

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long contactId;
    private String text;
    private int direction;

    public ChatMessage(long id, long contactId, String text, int direction) {
        this.id = id;
        this.contactId = contactId;
        this.text = text;
        this.direction = direction;
    }

    @Ignore
    public ChatMessage(long contactId, String text, int direction) {
        this(0, contactId, text, direction);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return id == that.id &&
                contactId == that.contactId &&
                direction == that.direction &&
                text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contactId, text, direction);
    }
}
