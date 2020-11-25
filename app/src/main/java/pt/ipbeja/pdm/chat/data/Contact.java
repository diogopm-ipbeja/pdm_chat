package pt.ipbeja.pdm.chat.data;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "contacts")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @Embedded
    private Position position;

    private String name;

    public Contact(long id, String name, Position position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    @Ignore
    public Contact(String name, Position position) {
        this(0, name, position);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id == contact.id &&
                position.equals(contact.position) &&
                name.equals(contact.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, position, name);
    }
}
