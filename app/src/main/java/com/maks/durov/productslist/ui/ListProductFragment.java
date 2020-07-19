package com.maks.durov.productslist.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import javax.inject.Inject;

import com.maks.durov.productslist.App;
import com.maks.durov.productslist.R;
import com.maks.durov.productslist.adapter.ProductAdapter;
import com.maks.durov.productslist.mvp.ProductPresenter;
import com.maks.durov.productslist.mvp.ProductView;

public class ListProductFragment extends Fragment implements ProductView {
    private static final String ARG_SHOW_DONE = "ListProductFragment_show_done";
    @Inject
    public ProductPresenter productPresenter;
    private boolean showDone;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;

    public ListProductFragment() {
    }

    public static ListProductFragment newInstance(boolean showDone) {
        ListProductFragment fragment = new ListProductFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_SHOW_DONE, showDone);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            showDone = getArguments().getBoolean(ARG_SHOW_DONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_product, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView = root.findViewById(R.id.products_recyclerview);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        productAdapter = new ProductAdapter(getContext(),
                showDone ? productPresenter.getCheckedProducts() : productPresenter.getUncheckedProducts(),
                new ProductAdapter.OnSelect() {
                    @Override
                    public void onStartSelect() {
                        ((MainActivity) getActivity()).enableMultiSelecting();
                    }

                    @Override
                    public void onCancel() {
                        ((MainActivity) getActivity()).disableMultiSelecting();
                    }
                });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productAdapter);
        recyclerView.addItemDecoration(dividerItemDecoration);
        productPresenter.onAttachView(this);
        return root;
    }

    public void checkSelectedItems() {
        if (showDone) {
            productPresenter.removeProducts(productAdapter.getSelectedItems());
        }else {
            productPresenter.checkProducts(productAdapter.getSelectedItems());
        }
        productAdapter.clearSelected();
    }

    @Override
    public void onDataChanged() {
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        productPresenter.onDetachView();
        super.onDestroyView();
    }
}
