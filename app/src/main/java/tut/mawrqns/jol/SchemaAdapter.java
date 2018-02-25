package tut.mawrqns.jol;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class SchemaAdapter extends RecyclerView.Adapter<SchemaAdapter.ViewHolder> {

    private List<SchemaModel> item;
    private Context context;

    SchemaAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SchemaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schema, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SchemaAdapter.ViewHolder holder, int position) {
        if (item != null) {
            SchemaModel schemaModel = item.get(position);
            if (schemaModel != null && !schemaModel.getTitle().isEmpty()) {
                holder.textView.setText(schemaModel.getTitle());
                if (schemaModel.isUnable()) {
                    holder.container.setBackground(context.getResources()
                            .getDrawable(R.drawable.shcema_bkg));
                    holder.textView.setTextColor(context.getResources().getColor(R.color.white));
                } else {
                    holder.container.setBackground(context.getResources()
                            .getDrawable(R.drawable.shcema_bkg_unable));
                    holder.textView.setTextColor(context.getResources().getColor(R.color.black));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (item != null) return item.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private RelativeLayout container;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_schema);
            container = itemView.findViewById(R.id.container);
        }
    }


    void setItem(List<SchemaModel> item) {
        this.item = item;
    }
}
