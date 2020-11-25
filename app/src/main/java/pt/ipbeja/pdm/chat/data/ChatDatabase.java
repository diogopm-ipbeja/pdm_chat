package pt.ipbeja.pdm.chat.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import pt.ipbeja.pdm.chat.data.dao.ContactDao;
import pt.ipbeja.pdm.chat.data.dao.MessageDao;

@Database(entities = {Contact.class, ChatMessage.class}, version = 1, exportSchema = false)
public abstract class ChatDatabase extends RoomDatabase {

    private static ChatDatabase instance;

    public static ChatDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, ChatDatabase.class, "chat_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }



    public abstract ContactDao contactDao();

    public abstract MessageDao messageDao();
}
