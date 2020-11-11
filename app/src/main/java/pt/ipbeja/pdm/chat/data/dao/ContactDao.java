package pt.ipbeja.pdm.chat.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import pt.ipbeja.pdm.chat.data.Contact;

@Dao
public abstract class ContactDao implements BaseDao<Contact> {

    @Query("select * from contacts order by name asc")
    public abstract List<Contact> getAll();

    @Query("select * from contacts where id = :id")
    public abstract Contact getById(long id);

}
