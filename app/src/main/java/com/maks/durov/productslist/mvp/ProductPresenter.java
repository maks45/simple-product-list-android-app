package com.maks.durov.productslist.mvp;

import com.maks.durov.productslist.entity.Product;
import java.util.List;
import java.util.Set;

public interface ProductPresenter {

    void onAttachView(ProductView productView);

    void onDetachView();

    List<Product> getCheckedProducts();

    List<Product> getUncheckedProducts();

    void checkProducts(Set<Integer> positions);

    void removeProducts(Set<Integer> positions);

    void setData(List<Product> products);

    ProductModel getProductModel();

    void onDataChanged();
}
