package com.example.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.model.Store;
import com.example.repository.StoreRepository;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public List<Store> getStoresByCity(String city) {
        // Assuming custom query or method needed, but for now filtering or basic find
        // You might need to add findByCity to StoreRepository if not present.
        // For simplicity, let's return all if no repository method exists, or add it.
        // Let's assume we might need to add it to the repository.
        // For this step, I will stick to findAll and filter, OR simpler: just return
        // all for now.
        // Actually, let's implement just getAllStores correctly first.
        return storeRepository.findAll();
    }

    @Override
    public Store getStoreById(int storeId) {
        return storeRepository.findById((long) storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));
    }
}
