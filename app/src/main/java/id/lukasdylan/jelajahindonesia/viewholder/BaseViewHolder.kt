package id.lukasdylan.jelajahindonesia.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by Lukas Dylan on 30/10/20.
 */
abstract class BaseViewHolder<T, V : ViewBinding>(protected val binding: V) :
    RecyclerView.ViewHolder(binding.root) {

    protected val context: Context
        get() = binding.root.context

    abstract fun bind(item: T)
}