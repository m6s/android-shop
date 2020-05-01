package info.mschmitt.shop.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import info.mschmitt.shop.app.databinding.ItemArticleBinding;
import info.mschmitt.shop.core.storage.Article;
import info.mschmitt.shop.core.util.ViewBindingViewHolder;

/**
 * @author Matthias Schmitt
 */
public class ArticleListAdapter
        extends RecyclerView.Adapter<ViewBindingViewHolder<ItemArticleBinding>> {
    public OnArticleClickListener onArticleClickListener;
    private final ArrayList<Article> articles = new ArrayList<>();
    private boolean enabled = true;

    @NonNull
    @Override
    public ViewBindingViewHolder<ItemArticleBinding> onCreateViewHolder(@NonNull ViewGroup parent,
                                                                        int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemArticleBinding binding = ItemArticleBinding.inflate(layoutInflater, parent, false);
        return new ViewBindingViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBindingViewHolder<ItemArticleBinding> holder,
                                 int position) {
        Article article = articles.get(position);
        holder.binding.nameText.setText(article.name);
        holder.itemView.setOnClickListener(v -> {
            if (onArticleClickListener == null) {
                return;
            }
            onArticleClickListener.onArticleClick(article);
        });
        holder.itemView.setEnabled(enabled);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        notifyDataSetChanged();
    }

    public void setArticles(List<Article> articles) {
        this.articles.clear();
        this.articles.addAll(articles);
        notifyDataSetChanged();
    }

    public interface OnArticleClickListener {
        void onArticleClick(Article article);
    }
}
