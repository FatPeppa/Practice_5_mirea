package com.example.practice_5_mirea.data.dataSource;

import com.example.practice_5_mirea.data.models.Product;

public class CurrentGoodKeeper {
    private final Product[] currentProduct = new Product[1];
    public CurrentGoodKeeper (Product product) {this.currentProduct[0] = product;}

    public Product getCurrentGood() {
        return currentProduct[0];
    }

    public void SetCurrentGood(Product product) {
        this.currentProduct[0] = product;
    }
}
