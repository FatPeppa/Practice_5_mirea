package com.example.practice_5_mirea.data.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.practice_5_mirea.data.dataSource.OrderKeeper;
import com.example.practice_5_mirea.data.dataSource.Room.CoreAppDatabase;
import com.example.practice_5_mirea.data.dataSource.Room.DAO.ProductDAO;
import com.example.practice_5_mirea.data.dataSource.Room.Entities.ProductEntity;
import com.example.practice_5_mirea.data.models.Product;
import com.example.practice_5_mirea.domain.ProductEntityToProductConverter;
import com.example.practice_5_mirea.domain.ProductToProductEntityConverter;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderRepositoryImpl implements OrderRepository {
    private CoreAppDatabase db;

    public OrderRepositoryImpl() {}

    @Override
    public void createDatabase(Context context, ArrayList<Product> values) {
        if (db != null) return;

        db = Room.databaseBuilder(context,
                CoreAppDatabase.class, "products").allowMainThreadQueries().build();
        ProductDAO productDAO = db.productDAO();

        if (values != null && values.size() > 0)
            productDAO.insertAll((ArrayList<ProductEntity>) ProductToProductEntityConverter.convertList(values));
    }

    @Override
    public ArrayList<Product> getOrderedPositions() {
        if (db == null) return null;

        ProductDAO productDAO = db.productDAO();
        if (productDAO.countRecords() > 0)
            return (ArrayList<Product>) ProductEntityToProductConverter
                .convertList(productDAO.getAll());

        return null;
    }
    @Override
    public void setOrderedPositions(ArrayList<Product> orderedPositions) {
        if (db == null) return;

        ProductDAO productDAO = db.productDAO();
        if (orderedPositions == null || orderedPositions.size() == 0) return;

        if (productDAO.countRecords() > 0) productDAO.cleanTable();

        productDAO.insertAll((ArrayList<ProductEntity>) ProductToProductEntityConverter.convertList(orderedPositions));
    }
    @Override
    public void putGood(Product product) {
        if (db == null) return;
        if (product == null) return;

        ProductDAO productDAO = db.productDAO();
        productDAO.insert(ProductToProductEntityConverter.convert(product));
    }
    @Override
    public Product getGood(int id) {
        if (db == null) return null;

        ProductDAO productDAO = db.productDAO();
        return ProductEntityToProductConverter.convert(productDAO.findById(id));
    }

    @Override
    public void cleanDatabase() {
        if (db == null) return;

        ProductDAO productDAO = db.productDAO();
        productDAO.cleanTable();
    }
}