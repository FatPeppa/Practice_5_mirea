package com.example.practice_5_mirea.data.dataSource;

import com.example.practice_5_mirea.data.models.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OrderKeeper {
    private Map<Integer, Product> order;
    public OrderKeeper(Map<Integer, Product> order) {
        this.order = order;
    }

    public ArrayList<Product> getOrder() {
        return new ArrayList<Product>(order.values());
    }

    public int putGoodToOrder(Product product) {
        if (order != null) {
            Set<Integer> set = order.keySet();
            if (set.size() > 0) {
                int maxId = Collections.max(set);
                order.put(maxId + 1, product);
                return maxId + 1;
            } else {
                int index = 0;
                order.put(index, product);
                return index;
            }
        } else {
            order = new HashMap<Integer, Product>();
            int index = 0;
            order.put(index, product);
            return index;
        }
    }

    public Product getGoodById(int id) {return order != null ? order.get(id) : null;}

    public boolean updateGoodById(int id, Product product) {
        if (order == null || order.get(id) == null) return false;
        order.put(id, product);
        return true;
    }

    public boolean deleteGoodById(int id) {
        if (order == null || order.get(id) == null) return false;
        order.remove(id);
        return true;
    }
}
