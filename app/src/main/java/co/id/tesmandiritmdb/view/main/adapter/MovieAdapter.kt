package co.id.tesmandiritmdb.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.tesmandiritmdb.databinding.RowPosterBinding
import co.id.tesmandiritmdb.network.POSTER_URL
import co.id.tesmandiritmdb.network.response.MovieResponse
import com.squareup.picasso.Picasso
import java.text.DateFormatSymbols
import java.util.*
import kotlin.collections.ArrayList

class MovieAdapter(
    var datas: ArrayList<MovieResponse.Result>,
    var listener: OnAdapterListener
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    @Override
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = datas[position]


        Picasso.get().load(POSTER_URL+model.poster_path).into(holder.binding.posterMv)
        holder.itemView.setOnClickListener { listener.onClick(model) }

    }

    override fun getItemCount() = datas.size

    class ViewHolder(val binding: RowPosterBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener {
        fun onClick(result: MovieResponse.Result)
    }

    fun setData(data: List<MovieResponse.Result>) {
        datas.addAll(data)
        notifyDataSetChanged()
    }

}