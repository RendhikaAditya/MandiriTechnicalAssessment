package co.id.tesmandiritmdb.view.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.PagerSnapHelper
import co.id.tesmandiritmdb.databinding.ActivityMainBinding
import co.id.tesmandiritmdb.network.ApiService
import co.id.tesmandiritmdb.network.Repository
import co.id.tesmandiritmdb.network.Resource
import co.id.tesmandiritmdb.network.response.GenreResponse
import co.id.tesmandiritmdb.network.response.MovieResponse
import co.id.tesmandiritmdb.view.detail.DetailActivity
import co.id.tesmandiritmdb.view.main.adapter.GenreAdapter
import co.id.tesmandiritmdb.view.main.adapter.MovieAdapter
import co.id.tesmandiritmdb.view.main.adapter.SliderAdapter
import es.dmoral.toasty.Toasty


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val api by lazy { ApiService.getClient() }
    private lateinit var viewModel: MainViewModel
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var adapterSlider: SliderAdapter
    private lateinit var adapterGenre: GenreAdapter
    private lateinit var adapterMovie: MovieAdapter

    private var page : Int = 1
    private var totalPage : Int = 0
    private var filtered : Boolean = false
    private var gendreId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupObserve()
        setupData()
        setupListener()


    }

    private fun setupListener() {

            viewModel.fetchMovie("", "$page")
        binding.btnClose.setOnClickListener {
            filtered = false
            adapterMovie.datas.clear()
            page = 1
            binding.nestedHome.scrollTo(0,0)

            binding.isSelectedGendre.visibility = View.GONE
            binding.rvGenre.visibility = View.VISIBLE
            viewModel.fetchMovie("","$page")
        }

        binding.nestedHome.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (page==totalPage){
                    binding.shimerPoster.visibility = View.GONE
                    Toasty.warning(this, "End Of Page", Toast.LENGTH_SHORT, true).show();
                }else {
                    page = page + 1
                    if (filtered) {
                        viewModel.fetchMovie("$gendreId", "$page")
                    } else {
                        viewModel.fetchMovie("", "$page")
                    }
                }
            }
        })
    }

    private fun setupData() {
        adapterMovie = MovieAdapter(
            arrayListOf(),
            object : MovieAdapter.OnAdapterListener{
                override fun onClick(result: MovieResponse.Result) {
                    startActivity(
                        Intent(this@MainActivity, DetailActivity::class.java)
                            .putExtra("id", ""+result.id.toString())
                    )
                }
            }
        )
        adapterMovie.notifyDataSetChanged()
        binding.rvMovie.adapter = adapterMovie

        viewModel.fetchGenre()
        adapterGenre = GenreAdapter(
            arrayListOf(),
            object : GenreAdapter.OnAdapterListener {
                override fun onClick(result: GenreResponse.Genre) {
                    filtered = true
                    adapterMovie.datas.clear()
                    page = 1
                    binding.nestedHome.scrollTo(0,0)

                    viewModel.fetchMovie("${result.id}","$page")
                    gendreId = result.id
                    binding.isSelectedGendre.visibility = View.VISIBLE
                    binding.gendreSelected.text = result.name
                    binding.rvGenre.visibility = View.GONE
                }
            }
        )
        adapterGenre.notifyDataSetChanged()
        binding.rvGenre.adapter = adapterGenre

        viewModel.fetchMovieTrand()
        adapterSlider = SliderAdapter(
            arrayListOf(),
            object : SliderAdapter.OnAdapterListener {
                override fun onClick(result: MovieResponse.Result) {
                    startActivity(
                        Intent(this@MainActivity, DetailActivity::class.java)
                            .putExtra("id", ""+result.id.toString())
                    )
                }
            }
        )
        adapterSlider.notifyDataSetChanged()
        binding.rvSlider.adapter = adapterSlider

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvSlider)
        val speedScroll = 3000L
        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            var count = 0
            override fun run() {
                if (count < 20) {
                    binding.rvSlider.smoothScrollToPosition(count++)
                    handler.postDelayed(this, speedScroll)
                }else{
                    count=0
                    binding.rvSlider.smoothScrollToPosition(0)
                    handler.postDelayed(this, speedScroll)
                }
            }
        }

        handler.postDelayed(runnable, speedScroll)
    }


    private fun setupObserve() {
        viewModel.movieTranding.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    loading(true, "slider")
                    Log.d("Slider", " :: Loading")
                }
                is Resource.Success -> {
                    loading(false, "slider")
                    Log.d("Slider", " :: response :: ${it.data!!.results}")
                    adapterSlider.setData(it.data.results)

                }
                is Resource.Error -> {
                    loading(false, "slider")
                    Log.d("Slider", " :: Error ")
                    Toasty.error(this, "Upps.. Perangkat tidak terhubung", Toast.LENGTH_SHORT, true)
                        .show();
                }
            }
        })

        viewModel.genre.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    loading(true, "gendre")

                    Log.d("Genre", " :: Loading")
                }
                is Resource.Success -> {
                    loading(false, "gendre")
                    Log.d("Genre", " :: response :: ${it.data!!.genres}")
                    adapterGenre.setData(it.data.genres)

                }
                is Resource.Error -> {
                    loading(false, "gendre")
                    Log.d("Genre", " :: Error ")
                    Toasty.error(this, "Upps.. Perangkat tidak terhubung", Toast.LENGTH_SHORT, true)
                        .show();
                }
            }
        })

        viewModel.movie.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    loading(true, "movie")

                    Log.d("Movie", " :: Loading")
                }
                is Resource.Success -> {
                    loading(false, "movie")
                    Log.d("Movie", " :: response :: ${it.data!!.results}")
                    totalPage = it.data.total_pages
                    adapterMovie.setData(it.data.results)

                }
                is Resource.Error -> {
                    loading(false, "movie")
                    Log.d("Movie", " :: Error ")
                    Toasty.error(this, "Upps.. Perangkat tidak terhubung", Toast.LENGTH_SHORT, true)
                        .show();
                }
            }
        })


    }

    private fun loading(loading: Boolean, tipe: String) {
        if (loading){
            when(tipe){
                "gendre" -> binding.shimerSlider.visibility = View.VISIBLE
                "slider" -> binding.shimerGendre.visibility = View.VISIBLE
                "movie" -> binding.shimerPoster.visibility = View.VISIBLE
            }
        }else{
            when(tipe){
                "gendre" -> binding.shimerGendre.visibility = View.GONE
                "slider" -> binding.shimerSlider.visibility = View.GONE
            }
        }

    }

    private fun setupViewModel() {
        repository = Repository(api)
        viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }
}