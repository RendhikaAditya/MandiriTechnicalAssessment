package co.id.tesmandiritmdb.view.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.id.tesmandiritmdb.databinding.ActivityDetailBinding
import co.id.tesmandiritmdb.network.ApiService
import co.id.tesmandiritmdb.network.POSTER_URL
import co.id.tesmandiritmdb.network.Repository
import co.id.tesmandiritmdb.network.Resource
import co.id.tesmandiritmdb.network.response.TrailerResponse
import co.id.tesmandiritmdb.network.response.UlasanResponse
import co.id.tesmandiritmdb.view.detail.adapter.TrailerAdapter
import co.id.tesmandiritmdb.view.detail.adapter.UlasanAdapter
import co.id.tesmandiritmdb.view.semuaUlasan.SemuaUlasanActivity
import co.id.tesmandiritmdb.view.trailer.TrailerActivity
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding


    private val api by lazy { ApiService.getClient() }
    private lateinit var viewModel: DetailViewModel
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: DetailViewModelFactory
    private lateinit var adapterTrailer : TrailerAdapter
    private lateinit var adapterUlasan : UlasanAdapter
    var id:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupListener()
        setupObserve()
        setupData()

    }

    private fun setupData() {
        adapterTrailer = TrailerAdapter(
            arrayListOf(),
            object : TrailerAdapter.OnAdapterListener {
                override fun onClick(result: TrailerResponse.Result) {
                    startActivity(
                        Intent(this@DetailActivity, TrailerActivity::class.java)
                            .putExtra("key", "${result.key}")
                            .putExtra("title", "${result.name}")
                            .putExtra("date", "${result.published_at.split("T")[0]}")
                    )
                }
            }
        )
        adapterTrailer.notifyDataSetChanged()
        binding.rvTrailer.adapter = adapterTrailer

        adapterUlasan = UlasanAdapter(
            arrayListOf(),
            object : UlasanAdapter.OnAdapterListener {
                override fun onClick(result: UlasanResponse.Result) {
                }
            }
        )
        adapterUlasan.notifyDataSetChanged()
        binding.rvUlasan.adapter = adapterUlasan
    }

    private fun setupObserve() {
        viewModel.movieDetail.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    loading(true, "detail")
                    Log.d("Detail", " :: Loading")
                }
                is Resource.Success -> {
                    loading(false, "detail")
                    Log.d("Detail", " :: response :: ${it.data!!}")
                    Picasso.get().load(POSTER_URL+it.data.backdrop_path).into(binding.imgBanerMv)
                    Picasso.get().load(POSTER_URL+it.data.poster_path).into(binding.imgPosterMv)
                    binding.title.setText(it.data.original_title)
                    binding.judulMv.setText(it.data.original_title)
                    binding.mvDateRelease.setText(it.data.release_date)
                    binding.mvDeskripsi.setText(it.data.overview)
                    binding.kosongText.setText("Kami tidak memiliki ulasan \nuntuk ${it.data.original_title}")
                    binding.kosongTextTrailer.setText("Kami tidak memiliki trailer \nuntuk ${it.data.original_title}")


                    it.data.genres.size.mod(3)==0
                    var listGenre = mutableListOf<String>()
                    repeat(it.data.genres.size){ i ->
                        listGenre.add(it.data.genres[i].name)
                    }
                    binding.mvGendreList.setText(listGenre.joinToString(separator = " | "))

                }
                is Resource.Error -> {
                    loading(false, "detail")
                    Log.d("Detail", " :: Error ")
                    Toasty.error(this, "Upps.. Perangkat tidak terhubung", Toast.LENGTH_SHORT, true)
                        .show();
                }
            }
        })

        viewModel.trailer.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    loading(true, "trailer")
                    Log.d("trailer", " :: Loading")
                }
                is Resource.Success -> {
                    Log.d("trailer", " :: response :: ${it.data!!.results}")
                    if (it.data.results.isNullOrEmpty()) {
                        Log.d("trailer", " :: cek kosong ")
                        binding.layoutKosongTrailer.visibility = View.VISIBLE
                    }

                    loading(false, "trailer")
                    adapterTrailer.setData(it.data.results)
                }
                is Resource.Error -> {
                    loading(false, "trailer")
                    Log.d("trailer", " :: Error ")
                    Toasty.error(this, "Upps.. Perangkat tidak terhubung", Toast.LENGTH_SHORT, true)
                        .show();
                }
            }
        })

        viewModel.ulasan.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    loading(true, "ulasan")
                    Log.d("ulasan", " :: Loading")
                }
                is Resource.Success -> {
                    Log.d("ulasan", " :: response :: ${it.data!!.results}")
                    if (it.data.results.isNullOrEmpty()) {
                        binding.layoutKosongUlasan.visibility = View.VISIBLE
                        binding.layoutUlasan.visibility = View.GONE
                    }else{
                        binding.layoutKosongUlasan.visibility = View.GONE
                        binding.layoutUlasan.visibility = View.VISIBLE
                    }
                    adapterUlasan.datas.clear()
                    loading(false, "ulasan")
                    if (it.data.results.size>=2) {
                        adapterUlasan.setData(it.data.results.subList(0, 2))
                    }else{
                        adapterUlasan.setData(it.data.results)
                    }

                    binding.btnSemuaUlasan.setOnClickListener {i->
                        startActivity(
                            Intent(this, SemuaUlasanActivity::class.java)
                                .putExtra("id", "$id")
                                .putExtra("title", binding.title.text.toString())
                        )
                    }


                }
                is Resource.Error -> {
                    loading(false, "ulasan")
                    Log.d("ulasan", " :: Error ")
                    Toasty.error(this, "Upps.. Perangkat tidak terhubung", Toast.LENGTH_SHORT, true)
                        .show();
                }
            }
        })

    }

    private fun loading(loading: Boolean, tipe: String) {
        if (loading){
            when(tipe){
                "detail" -> binding.shimerDetail.visibility = View.VISIBLE
                "trailer" -> binding.shimerTrailer.visibility = View.VISIBLE
                "ulasan" -> binding.shimerUlasan.visibility = View.VISIBLE
            }
        }else{
            when(tipe){
                "detail" -> binding.shimerDetail.visibility = View.GONE
                "trailer" -> binding.shimerTrailer.visibility = View.GONE
                "ulasan" -> binding.shimerUlasan.visibility = View.GONE
            }
        }
    }



    private fun setupListener() {
        binding.back.setOnClickListener { finish() }


        var i: Intent = getIntent()
        id = i.getStringExtra("id").toString()
        viewModel.fetchMovieDetail(id)
        viewModel.fetchTrailer(id)
        viewModel.fetchUlasan(id, "1")
    }

    private fun setupViewModel() {
        repository = Repository(api)
        viewModelFactory = DetailViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
    }
}