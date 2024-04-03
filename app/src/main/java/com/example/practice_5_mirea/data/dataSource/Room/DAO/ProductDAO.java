package com.example.practice_5_mirea.data.dataSource.Room.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.practice_5_mirea.data.dataSource.Room.Entities.ProductEntity;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ProductDAO {
    @Query("SELECT * FROM products")
    List<ProductEntity> getAll();

    @Query("SELECT * FROM products WHERE id= :uid")
    ProductEntity findById(int uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ArrayList<ProductEntity> productEntities);
    @Delete
    void delete(ProductEntity productEntity);

    @Query("SELECT COUNT(*) FROM products")
    Integer countRecords();

    @Query("DELETE FROM products")
    int cleanTable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductEntity product);
}
