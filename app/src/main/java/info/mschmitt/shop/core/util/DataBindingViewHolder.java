package info.mschmitt.shop.core.util;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

/**
 * @author Matthias Schmitt
 */
public class DataBindingViewHolder<T extends ViewBinding> extends RecyclerView.ViewHolder {
    public final T binding;

    public DataBindingViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
