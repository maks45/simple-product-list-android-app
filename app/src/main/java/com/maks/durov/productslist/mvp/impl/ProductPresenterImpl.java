package com.maks.durov.productslist.mvp.impl;

import android.os.AsyncTask;
import java.lang.ref.WeakReference;
import com.maks.durov.productslist.entity.Product;
import com.maks.durov.productslist.mvp.ProductModel;
import com.maks.durov.productslist.mvp.ProductPresenter;
import com.maks.durov.productslist.mvp.ProductView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ProductPresenterImpl implements ProductPresenter {
    private final List<Product> checkedProducts;
    private final List<Product> unCheckedProducts;
    private final ProductModel productModel;
    private ProductView productView;

    public ProductPresenterImpl(ProductModel productModel) {
        this.productModel = productModel;
        checkedProducts = new ArrayList<>();
        unCheckedProducts = new ArrayList<>();
    }

    @Override
    public void onAttachView(ProductView ProductView) {
        this.productView = ProductView;
        if (unCheckedProducts.isEmpty() && checkedProducts.isEmpty()) {
            new GetProductsAsyncTask(this).execute();
        } else {
            updateProductView();
        }
    }

    @Override
    public void onDetachView() {
        new SaveProductsAsyncTask(this).execute();
        productView = null;
    }

    @Override
    public List<Product> getCheckedProducts() {
        return checkedProducts;
    }

    @Override
    public List<Product> getUncheckedProducts() {
        return unCheckedProducts;
    }

    @Override
    public void checkProducts(List<Integer> positions) {
        positions.sort(Comparator.reverseOrder());
        positions.stream().mapToInt(Integer::intValue).forEach(i -> {
            unCheckedProducts.get(i).setDone(true);
            checkedProducts.add(unCheckedProducts.get(i));
        });
        positions.stream().mapToInt(Integer::intValue).forEach(unCheckedProducts::remove);
        onDataChanged();
    }

    @Override
    public void removeProducts(List<Integer> positions) {
        positions.sort(Comparator.reverseOrder());
        new RemoveProductAsyncTask(this)
                .execute(positions.toArray(new Integer[0]));
        onDataChanged();
    }


    @Override
    public void setData(List<Product> Products) {
        this.checkedProducts.clear();
        this.unCheckedProducts.clear();
        for (Product Product : Products) {
            if (Product.getDone()) {
                checkedProducts.add(Product);
            } else {
                unCheckedProducts.add(Product);
            }
        }
        updateProductView();
    }

    @Override
    public ProductModel getProductModel() {
        return productModel;
    }

    @Override
    public void onDataChanged() {
        new SaveProductsAsyncTask(this).execute();
        new GetProductsAsyncTask(this).execute();
    }

    private void updateProductView() {
        if (productView != null) {
            productView.onDataChanged();
        }
    }

    private static class RemoveProductAsyncTask extends AsyncTask<Integer, Void, Void> {
        private final WeakReference<ProductPresenter> productPresenter;

        public RemoveProductAsyncTask(ProductPresenter productPresenter) {
            this.productPresenter = new WeakReference<>(productPresenter);
        }

        @Override
        protected Void doInBackground(Integer... args) {
            Arrays.stream(args).mapToInt(Integer::intValue).forEach(i -> {
                productPresenter.get().getProductModel()
                        .removeProduct(productPresenter.get().getCheckedProducts().get(i));
            });
            Arrays.stream(args).mapToInt(Integer::intValue)
                    .forEach(i -> productPresenter.get().getCheckedProducts().remove(i));
            return null;
        }
    }

    private static class GetProductsAsyncTask extends AsyncTask<Void, Void, List<Product>> {
        private final WeakReference<ProductPresenter> productPresenter;

        public GetProductsAsyncTask(ProductPresenter productPresenter) {
            this.productPresenter = new WeakReference<>(productPresenter);
        }

        @Override
        protected List<Product> doInBackground(Void... args) {
            return productPresenter.get().getProductModel().loadProductsFromDatabase();
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            productPresenter.get().setData(products);
        }
    }

    private static class SaveProductsAsyncTask extends AsyncTask<Void, Void, Void> {
        private final WeakReference<ProductPresenter> productPresenter;

        public SaveProductsAsyncTask(ProductPresenter productPresenter) {
            this.productPresenter = new WeakReference<>(productPresenter);
        }

        @Override
        protected Void doInBackground(Void... args) {
            productPresenter.get().getProductModel()
                    .saveProductsToDatabase(productPresenter.get().getCheckedProducts());
            productPresenter.get().getProductModel()
                    .saveProductsToDatabase(productPresenter.get().getUncheckedProducts());
            return null;
        }
    }
}
