package stavki.bukmekerskaya.jfdha;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.ViewHoler> {

    private Context context;
    private AdapterNewsListner listner;

    private List<String> response;

    public interface AdapterNewsListner {
        void onClick(int position);
    }

    public AdapterNews(Context context, List<String> response, AdapterNewsListner listner) {
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
            holder.tvMainText.setText(response.get(position));
            holder.container.setOnClickListener(v -> listner.onClick(position));
    }

    @Override
    public int getItemCount() {
        return response.size();
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        TextView tvMainText;
        View container;

        ViewHoler(View itemView) {
            super(itemView);
            tvMainText = itemView.findViewById(R.id.tv_main_text);
            container = itemView.findViewById(R.id.container);
        }
    }


}
