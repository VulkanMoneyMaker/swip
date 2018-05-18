package here.betstavki.here;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.ViewHoler> {

    private Context context;
    private AdapterNewsListner listner;

    private List<Pair<String, String>> response;

    public interface AdapterNewsListner {
        void onClick(int position);
    }

    public AdapterNews(Context context, List<Pair<String, String>> response, AdapterNewsListner listner) {
        this.context = context;
        this.listner = listner;
        this.response = response;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,
                parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
            holder.tvMainText.setText(response.get(position).first);
            holder.tvSecondaryText.setText(response.get(position).second);
            holder.container.setOnClickListener(v -> listner.onClick(position));
    }

    @Override
    public int getItemCount() {
        return response.size();
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        TextView tvMainText;
        TextView tvSecondaryText;
        View container;

        ViewHoler(View itemView) {
            super(itemView);
            tvMainText = itemView.findViewById(R.id.tv_main_text);
            tvSecondaryText = itemView.findViewById(R.id.tv_secondary_text);
            container = itemView.findViewById(R.id.container);
        }
    }


}
