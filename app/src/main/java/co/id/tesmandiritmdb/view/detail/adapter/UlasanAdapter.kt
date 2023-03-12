package co.id.tesmandiritmdb.view.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.tesmandiritmdb.databinding.RowPosterBinding
import co.id.tesmandiritmdb.databinding.RowTrailerBinding
import co.id.tesmandiritmdb.databinding.RowUlasanBinding
import co.id.tesmandiritmdb.network.POSTER_URL
import co.id.tesmandiritmdb.network.THUMNAIL_YT
import co.id.tesmandiritmdb.network.response.MovieResponse
import co.id.tesmandiritmdb.network.response.TrailerResponse
import co.id.tesmandiritmdb.network.response.UlasanResponse
import com.squareup.picasso.Picasso
import java.text.DateFormatSymbols
import java.util.*
import kotlin.collections.ArrayList

class UlasanAdapter(
    var datas: ArrayList<UlasanResponse.Result>,
    var listener: OnAdapterListener
) : RecyclerView.Adapter<UlasanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowUlasanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    @Override
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = datas[position]

        holder.binding.namaUser.text = model.author
        holder.binding.tglUlasan.text = model.created_at.toString().split("T")[0]
        Picasso.get().load(POSTER_URL+model.author_details.avatar_path).into(holder.binding.profilUser)
        holder.binding.ulasanUser.text = model.content
        holder.binding.ratingBar.rating = (model.author_details.rating/2).toFloat()
        holder.itemView.setOnClickListener { listener.onClick(model) }

    }

    override fun getItemCount() = datas.size

    class ViewHolder(val binding: RowUlasanBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener {
        fun onClick(result: UlasanResponse.Result)
    }

    fun setData(data: List<UlasanResponse.Result>) {
        datas.addAll(data)
        notifyDataSetChanged()
    }
}