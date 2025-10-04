package com.example.gerenciadorhabitos.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "habits",
        foreignKeys = @ForeignKey(entity = Profile.class,
                parentColumns = "id",
                childColumns = "profileId",
                onDelete = ForeignKey.CASCADE))
public class Habit {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int profileId;
    public String name;
    public String frequency;
    public String goal;
    public boolean notify;
    public boolean completedToday;

    public Habit(int profileId, String name, String frequency, String goal, boolean notify) {
        this.profileId = profileId;
        this.name = name;
        this.frequency = frequency;
        this.goal = goal;
        this.notify = notify;
        this.completedToday = false;
    }
}