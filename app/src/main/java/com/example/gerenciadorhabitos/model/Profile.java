package com.example.gerenciadorhabitos.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "profiles")
public class Profile {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public Profile(String name) {
        this.name = name;
    }
}