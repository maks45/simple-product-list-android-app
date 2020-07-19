package com.maks.durov.productslist.mvp;

import com.maks.durov.productslist.entity.Product;
import java.util.List;

public interface ProductModel {

    List<Product> loadProductsFromDatabase();

    void saveProductsToDatabase(List<Product> products);

    void removeProduct(Product product);
}
