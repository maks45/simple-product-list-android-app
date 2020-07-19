package com.maks.durov.productslist.mvp.impl;

import com.maks.durov.productslist.entity.Product;
import com.maks.durov.productslist.mvp.ProductModel;
import com.maks.durov.productslist.room.ProductDao;
import java.util.List;

public class ProductModelImpl implements ProductModel {
    private final ProductDao productDao;

    public ProductModelImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> loadProductsFromDatabase() {
        return productDao.getAll();
    }

    @Override
    public void saveProductsToDatabase(List<Product> products) {
        productDao.saveAll(products);
    }

    @Override
    public void removeProduct(Product Product) {
        productDao.delete(Product);
    }
}
