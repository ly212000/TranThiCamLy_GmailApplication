package com.example.gmailapplication;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    List<ItemModel> items;
    List<ItemModel > itemsFull;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textLetter;
        TextView textName;
        TextView textSubject;
        TextView textContent;
        TextView textTime;
        ImageView image;
        EditText searchText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textLetter = itemView.findViewById(R.id.text_letter);
            textName = itemView.findViewById(R.id.text_name);
            textSubject = itemView.findViewById(R.id.text_subject);
            textContent = itemView.findViewById(R.id.text_content);
            textTime = itemView.findViewById(R.id.text_time);
            image = itemView.findViewById(R.id.star);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isFavorite = items.get(getAdapterPosition()).isFavorite();
                    if (isFavorite == true)
                        items.get(getAdapterPosition()).setFavorite(false);
                    else
                        items.get(getAdapterPosition()).setFavorite(true);
                    notifyDataSetChanged();
                }
            });
        }
    }

    ItemAdapter(List<ItemModel> items) {
        this.items = items;
        itemsFull = new ArrayList<>(items);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_mail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ItemModel item = items.get(position);

        viewHolder.textLetter.setText(item.getName().substring(0, 1));
        Drawable background = viewHolder.textLetter.getBackground();
        background.setColorFilter(new PorterDuffColorFilter(item.getColor(), PorterDuff.Mode.SRC_ATOP));
        viewHolder.textName.setText(item.getName());
        viewHolder.textSubject.setText(item.getSubject());
        viewHolder.textContent.setText(item.getContent());
        viewHolder.textTime.setText(item.getTime());

        if(item.isFavorite() == true) viewHolder.image.setImageResource(R.drawable.star_favorite);
        else viewHolder.image.setImageResource(R.drawable.star_normal);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() { return itemsFilter;}

    private Filter itemsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ItemModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() < 3) {
                filteredList.addAll(itemsFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ItemModel i : itemsFull) {
                    if ( (i.getName().toLowerCase().contains(filterPattern))
                        || (i.getSubject().toLowerCase().contains(filterPattern))
                        || (i.getContent().toLowerCase().contains(filterPattern)) ) {
                        filteredList.add(i);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            items.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public Filter getItemsFavorite(int clickCount) {
        if (clickCount % 2 == 1)
            return itemsFavorite;
        else return allItems;
    }
    private Filter itemsFavorite = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ItemModel> filteredList = new ArrayList<>();
            for (ItemModel i : items) {
                if (i.isFavorite() == true) {
                    filteredList.add(i);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            items.addAll((List) results.values);
            notifyDataSetChanged();
        }

    };

    private Filter allItems = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ItemModel> filteredList = new ArrayList<>();
            for (ItemModel i : itemsFull)
                    filteredList.add(i);

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            items.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
