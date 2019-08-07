package info.mschmitt.shop.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import info.mschmitt.shop.app.databinding.ItemArticleBinding;
import info.mschmitt.shop.core.database.Article;
import info.mschmitt.shop.core.util.DataBindingViewHolder;

import java.util.ArrayList;

/**
 * @author Matthias Schmitt
 */
public class ArticleListAdapter extends RecyclerView.Adapter<DataBindingViewHolder<ItemArticleBinding>> {
    public final ArrayList<Article> articles = new ArrayList<>();
    public OnArticleClickListener onArticleClickListener;

    @NonNull
    @Override
    public DataBindingViewHolder<ItemArticleBinding> onCreateViewHolder(@NonNull ViewGroup parent,
                                                                               int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemArticleBinding binding = ItemArticleBinding.inflate(layoutInflater, parent, false);
        return new DataBindingViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBindingViewHolder<ItemArticleBinding> holder, int position) {
        Article article = articles.get(position);
        holder.binding.nameText.setText(article.name);
        holder.itemView.setOnClickListener(v -> {
            if (onArticleClickListener == null) {
                return;
            }
            onArticleClickListener.onArticleClick(article);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public interface OnArticleClickListener {
        void onArticleClick(Article article);
    }
}
