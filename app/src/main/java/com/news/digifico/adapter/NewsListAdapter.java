package com.news.digifico.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.news.digifico.R;
import com.news.digifico.db.entities.NewsEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fedchuk Maxim on 2018-05-03.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {
    private List<NewsEntity> titlesList;
    private Context context;
    private OnItemClickListener listener;

    public NewsListAdapter(Context context) {
        this.context = context;
        this.titlesList = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_item_adapter, parent, false);
        return new NewsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        String item = titlesList.get(position).getTitle();
        holder.title.setText(item);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(titlesList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return titlesList.size();
    }

    public void update(List<NewsEntity> data) {
        if (data != null && titlesList.isEmpty()) {
            titlesList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void updateItem(NewsEntity data) {
        if (data != null) {
            titlesList.add(data);
            notifyDataSetChanged();
        }
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView title;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
