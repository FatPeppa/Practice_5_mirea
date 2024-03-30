package com.example.practice_5_mirea.data.dataSource;

import com.example.practice_5_mirea.data.models.Good;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OrderKeeper {
    private Map<Integer, Good> order;
    public OrderKeeper(Map<Integer, Good> order) {
        this.order = order;
    }

    public ArrayList<Good> getOrder() {
        return new ArrayList<Good>(order.values());
    }

    public int putGoodToOrder(Good good) {
        if (order != null) {
            Set<Integer> set = order.keySet();
            if (set.size() > 0) {
                int maxId = Collections.max(set);
                order.put(maxId + 1, good);
                return maxId + 1;
            } else {
                int index = 0;
                order.put(index, good);
                return index;
            }
        } else {
            order = new HashMap<Integer, Good>();
            int index = 0;
            order.put(index, good);
            return index;
        }
    }

    public Good getGoodById(int id) {return order != null ? order.get(id) : null;}

    public boolean updateGoodById(int id, Good good) {
        if (order == null || order.get(id) == null) return false;
        order.put(id, good);
        return true;
    }

    public boolean deleteGoodById(int id) {
        if (order == null || order.get(id) == null) return false;
        order.remove(id);
        return true;
    }
}
