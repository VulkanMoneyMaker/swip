package lad.nakkinto.koajs;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.ViewHoler> {

    private List<String> listText;
    private List<Integer> listDrawable;
    private Context context;

    public AdapterNews(Context context, List<String> listText, List<Integer> listDrawable) {
        this.listText = listText;
        this.context = context;
        this.listDrawable = listDrawable;
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
            holder.imgIcon.setImageResource(listDrawable.get(position));
            holder.tvText.setText(listText.get(position));
    }

    @Override
    public int getItemCount() {
        return listText.size();
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView tvText;

        ViewHoler(View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_news);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }


}