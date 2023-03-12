package co.id.tesmandiritmdb.view.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.tesmandiritmdb.databinding.RowPosterBinding
import co.id.tesmandiritmdb.databinding.RowTrailerBinding
import co.id.tesmandiritmdb.network.POSTER_URL
import co.id.tesmandiritmdb.network.THUMNAIL_YT
import co.id.tesmandiritmdb.network.response.MovieResponse
import co.id.tesmandiritmdb.network.response.TrailerResponse
import com.squareup.picasso.Picasso
import java.text.DateFormatSymbols
import java.util.*
import kotlin.collections.ArrayList

class TrailerAdapter(
    var datas: ArrayList<TrailerResponse.Result>,
    var listener: OnAdapterListener
) : RecyclerView.Adapter<TrailerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowTrailerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    @Override
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = datas[position]


        Picasso.get().load(THUMNAIL_YT+model.key+"/0.jpg").into(holder.binding.thumnailYt)
        holder.itemView.setOnClickListener { listener.onClick(model) }

    }

    override fun getItemCount() = datas.size

    class ViewHolder(val binding: RowTrailerBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener {
        fun onClick(result: TrailerResponse.Result)
    }

    fun setData(data: List<TrailerResponse.Result>) {
        datas.clear()
        datas.addAll(data)
        notifyDataSetChanged()
    }

}