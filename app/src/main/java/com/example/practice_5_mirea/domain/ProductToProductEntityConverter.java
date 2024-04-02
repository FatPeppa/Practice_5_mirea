package com.example.practice_5_mirea.domain;

import com.example.practice_5_mirea.data.dataSource.Room.Entities.ProductEntity;
import com.example.practice_5_mirea.data.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductToProductEntityConverter {
    public static ProductEntity convert(Product product) {
        ProductEntity result = new ProductEntity();
        result.id = product.getId();
        result.productName = product.getGoodName();
        result.productAmount = product.getGoodAmount();
        return result;
    }

    public static List<ProductEntity> convertList(List<Product> products) {
        List<ProductEntity> resultList = new ArrayList<>();

        for (Product product : products) {
            ProductEntity tempPE = new ProductEntity();
            tempPE.id = product.getId();
            tempPE.productName = product.getGoodName();
            tempPE.productAmount = product.getGoodAmount();
            resultList.add(tempPE);
        }

        return resultList;
    }
}
