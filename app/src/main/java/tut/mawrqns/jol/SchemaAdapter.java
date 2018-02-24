package tut.mawrqns.jol;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public class SchemaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> item;

    public SchemaAdapter() {
        item.add("");
        item.add("");
        item.add("");
        item.add("");
        item.add("");
        item.add("");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schema);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return item.size();
    }
}
