package com.maks.durov.productslist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.maks.durov.productslist.R;
import com.maks.durov.productslist.entity.Product;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm");
    private final List<Product> products;
    private final List<Integer> selectedItems;
    private final Context context;
    private final OnSelect onSelect;

    public ProductAdapter(Context context, List<Product> products, OnSelect onSelect) {
        selectedItems = new ArrayList<>();
        this.onSelect = onSelect;
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.title.setText(product.getTitle());
        holder.checkMark.setVisibility(selectedItems.contains(position) ? View.VISIBLE : View.GONE);
        holder.productImage.setVisibility(product.getImagePath() == null ? View.GONE : View.VISIBLE);
        holder.timeStamp.setText(product.getDateTime().format(DATE_TIME_FORMATTER));
        if (product.getImagePath() != null) {
            Glide.with(context).load(product.getImagePath()).into(holder.productImage);
        }
        holder.itemView.setOnLongClickListener(v -> {
            onSelect.onStartSelect();
            if (!selectedItems.contains(position)) {
                selectedItems.add(position);
            } else {
                selectedItems.remove((Integer) position);
            }
            if (selectedItems.isEmpty()) {
                onSelect.onCancel();
            }
            notifyItemChanged(position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public List<Integer> getSelectedItems() {
        return selectedItems;
    }

    public void clearSelected() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView timeStamp;
        private final ImageView checkMark;
        private final ImageView productImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_view_title);
            timeStamp = itemView.findViewById(R.id.item_view_date_time);
            checkMark = itemView.findViewById(R.id.item_view_checkmark);
            productImage = itemView.findViewById(R.id.item_image);
        }
    }

    public interface OnSelect {
        void onStartSelect();

        void onCancel();
    }
}
