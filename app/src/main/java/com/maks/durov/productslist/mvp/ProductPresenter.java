package com.maks.durov.productslist.mvp;

import com.maks.durov.productslist.entity.Product;
import java.util.List;

public interface ProductPresenter {

    void onAttachView(ProductView productView);

    void onDetachView();

    List<Product> getCheckedProducts();

    List<Product> getUncheckedProducts();

    void checkProducts(List<Integer> positions);

    void removeProducts(List<Integer> positions);

    void setData(List<Product> products);

    ProductModel getProductModel();

    void onDataChanged();
}
