package com.example.gerenciadorhabitos.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.gerenciadorhabitos.model.Habit;
import java.util.List;

@Dao
public interface HabitDao {
    @Query("SELECT * FROM habits WHERE profileId = :profileId ORDER BY id DESC")
    List<Habit> getHabitsForProfile(int profileId);

    @Insert
    void insert(Habit habit);

    @Update
    void update(Habit habit);

    @Delete
    void delete(Habit habit);
}
