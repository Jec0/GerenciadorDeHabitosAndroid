package com.example.gerenciadorhabitos.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Query;
import com.example.gerenciadorhabitos.model.Profile;
import java.util.List;


@Dao
public interface ProfileDao {
    @Insert
    void insert(Profile profile);

    @Delete
    void delete(Profile profile);
    @Query("SELECT * FROM profiles ORDER BY name ASC")
    List<Profile> getAll();
}