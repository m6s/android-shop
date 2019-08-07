package info.mschmitt.shop.core.util;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Matthias Schmitt
 */
public class DataBindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public final T binding;

    public DataBindingViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
