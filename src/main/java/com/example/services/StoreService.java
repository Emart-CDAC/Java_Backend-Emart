package com.example.services;

import java.util.List;
import com.example.model.Store;

public interface StoreService {
    List<Store> getAllStores();

    List<Store> getStoresByCity(String city);

    Store getStoreById(int storeId);
}
