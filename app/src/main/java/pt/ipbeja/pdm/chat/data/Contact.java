package pt.ipbeja.pdm.chat.data;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.Objects;

@Entity(tableName = "contacts")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    @Embedded
    private Position position;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] photoThumbnail;

    private String photoPath;


    public Contact(long id, String name, Position position, byte[] photoThumbnail, String photoPath) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.photoThumbnail = photoThumbnail;
        this.photoPath = photoPath;
    }

    @Ignore
    public Contact(String name, Position position, byte[] photoThumbnail, String photoPath) {
        this(0, name, position, photoThumbnail, photoPath);
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

    public byte[] getPhotoThumbnail() {
        return photoThumbnail;
    }

    public void setPhotoThumbnail(byte[] photoThumbnail) {
        this.photoThumbnail = photoThumbnail;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id == contact.id &&
                name.equals(contact.name) &&
                Objects.equals(position, contact.position) &&
                Arrays.equals(photoThumbnail, contact.photoThumbnail) &&
                Objects.equals(photoPath, contact.photoPath);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, position);
        result = 31 * result + Arrays.hashCode(photoThumbnail);
        return result;
    }


}
