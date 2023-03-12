package co.id.tesmandiritmdb.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.tesmandiritmdb.databinding.RowSliderBinding
import co.id.tesmandiritmdb.network.POSTER_URL
import co.id.tesmandiritmdb.network.response.MovieResponse
import com.squareup.picasso.Picasso
import java.text.DateFormatSymbols
import java.util.*
import kotlin.collections.ArrayList

class SliderAdapter(
    var datas: ArrayList<MovieResponse.Result>,
    var listener: OnAdapterListener
) : RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    @Override
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = datas[position]

        holder.binding.mvName.setText(model.original_title)
        holder.binding.mvDesc.setText(model.overview)
        Picasso.get().load(POSTER_URL+model.backdrop_path).into(holder.binding.mvBaner)
        holder.itemView.setOnClickListener { listener.onClick(model) }

    }

    override fun getItemCount() = datas.size

    class ViewHolder(val binding: RowSliderBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener {
        fun onClick(result: MovieResponse.Result)
    }

    fun setData(data: List<MovieResponse.Result>) {
        datas.clear()
        datas.addAll(data)
        notifyDataSetChanged()
    }

}