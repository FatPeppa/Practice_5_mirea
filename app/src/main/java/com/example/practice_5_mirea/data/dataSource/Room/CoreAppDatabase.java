package com.example.practice_5_mirea.data.dataSource.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.practice_5_mirea.data.dataSource.Room.DAO.ProductDAO;
import com.example.practice_5_mirea.data.dataSource.Room.Entities.ProductEntity;

@Database(entities = {ProductEntity.class}, version = 1)
public abstract class CoreAppDatabase extends RoomDatabase {
    public abstract ProductDAO productDAO();
}
