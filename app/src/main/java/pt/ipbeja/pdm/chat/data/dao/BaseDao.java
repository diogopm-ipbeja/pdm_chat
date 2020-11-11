package pt.ipbeja.pdm.chat.data.dao;


import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

public interface BaseDao<T> {

    @Insert
    long insert(T t);

    @Delete
    int delete(T t);

    @Update
    int update(T t);

}
