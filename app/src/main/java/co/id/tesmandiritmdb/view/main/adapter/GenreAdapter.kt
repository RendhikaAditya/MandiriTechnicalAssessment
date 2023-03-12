package co.id.tesmandiritmdb.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.tesmandiritmdb.databinding.RowGendreBinding
import co.id.tesmandiritmdb.network.response.GenreResponse
import java.text.DateFormatSymbols
import kotlin.collections.ArrayList

class GenreAdapter(
    var datas: ArrayList<GenreResponse.Genre>,
    var listener: OnAdapterListener
) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowGendreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    @Override
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = datas[position]

        holder.binding.gendre.setText(model.name)

        holder.itemView.setOnClickListener { listener.onClick(model) }

    }

    override fun getItemCount() = datas.size

    class ViewHolder(val binding: RowGendreBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener {
        fun onClick(result: GenreResponse.Genre)
    }

    fun setData(data: List<GenreResponse.Genre>) {
        datas.clear()
        datas.addAll(data)
        notifyDataSetChanged()
    }
}