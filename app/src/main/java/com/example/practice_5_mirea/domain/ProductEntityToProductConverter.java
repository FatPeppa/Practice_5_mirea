package com.example.practice_5_mirea.domain;

import com.example.practice_5_mirea.data.dataSource.Room.Entities.ProductEntity;
import com.example.practice_5_mirea.data.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductEntityToProductConverter {
    public static Product convert(ProductEntity productEntity) {
        Product result = new Product(productEntity.productName, productEntity.productAmount);
        result.setId(productEntity.id);

        return result;
    }

    public static List<Product> convertList(List<ProductEntity> productEntities) {
        List<Product> resultList = new ArrayList<>();

        for (ProductEntity productEntity : productEntities)
            resultList.add(convert(productEntity));

        return resultList;
    }
}
