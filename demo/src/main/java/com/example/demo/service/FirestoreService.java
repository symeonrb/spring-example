package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProductEntity;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {
    private static final Logger logger = LoggerFactory.getLogger(FirestoreService.class);

    public boolean saveDocument(ProductEntity o) {
        ApiFuture<WriteResult> collectionsApiFuture = null;
        Firestore dbFirestore = FirestoreClient.getFirestore();
        collectionsApiFuture = dbFirestore.collection("PRODUCT").document(o.getIdDocument()).set(o);
        try {
            if (!collectionsApiFuture.get().getUpdateTime().toString().equals("")) {
                return true;
            }
        } catch (InterruptedException e) {
            logger.info(" InterruptedException {} ", e.getMessage());
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            logger.info(" ExecutionException {} ", e.getMessage());
            throw new RuntimeException(e);
        }
        return false;
    }
}
