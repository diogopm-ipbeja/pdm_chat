package pt.ipbeja.pdm.chat.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import pt.ipbeja.pdm.chat.data.ChatMessage;

@Dao
public abstract class MessageDao implements BaseDao<ChatMessage> {

    @Query("select * from messages where contactId = :contactId")
    public abstract List<ChatMessage> getAllForContact(long contactId);

    @Query("delete from messages where contactId = :contactId")
    public abstract void deleteAllMessages(long contactId);

}
