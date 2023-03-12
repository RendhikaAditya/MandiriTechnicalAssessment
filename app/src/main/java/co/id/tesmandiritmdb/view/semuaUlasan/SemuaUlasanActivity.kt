package co.id.tesmandiritmdb.view.semuaUlasan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.id.tesmandiritmdb.databinding.ActivitySemuaUlasanBinding
import co.id.tesmandiritmdb.network.ApiService
import co.id.tesmandiritmdb.network.Repository
import co.id.tesmandiritmdb.network.Resource
import co.id.tesmandiritmdb.network.response.UlasanResponse
import co.id.tesmandiritmdb.view.detail.DetailViewModel
import co.id.tesmandiritmdb.view.detail.DetailViewModelFactory
import co.id.tesmandiritmdb.view.detail.adapter.UlasanAdapter
import es.dmoral.toasty.Toasty

class SemuaUlasanActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySemuaUlasanBinding
    private lateinit var adapterUlasan: UlasanAdapter
    private val api by lazy { ApiService.getClient() }
    private lateinit var viewModel: DetailViewModel
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: DetailViewModelFactory

    var page = 1
    private var totalPage: Int = 0
    private var gendreId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySemuaUlasanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gendreId = intent.getStringExtra("id")!!.toInt()
        setupViewModel()
        viewModel.fetchUlasan("$gendreId", "$page")
        setupListener()
        setupObserve()
        setupData()


    }

    private fun setupListener() {
        binding.title.setText("Ulasan "+intent.getStringExtra("title"))
        binding.back.setOnClickListener { finish() }

        binding.nestedUlasan.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {

                if (page == totalPage) {
                    binding.shimerUlasan.visibility = View.GONE
                    Toasty.warning(this, "End Of Page", Toast.LENGTH_SHORT, true).show();
                } else {
                    page = page + 1
                    viewModel.fetchUlasan("$gendreId", "$page")
                }
            }
        })
    }

    private fun setupData() {
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
        viewModel.ulasan.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    loading(true)
                    Log.d("ulasan", " :: Loading")
                }
                is Resource.Success -> {
                    Log.d("ulasan", " :: response :: ${it.data!!.results}")
                    loading(false)
                    adapterUlasan.setData(it.data.results)
                    totalPage = it.data.total_pages

                }
                is Resource.Error -> {
                    loading(false)
                    Log.d("ulasan", " :: Error ")
                    Toasty.error(this, "Upps.. Perangkat tidak terhubung", Toast.LENGTH_SHORT, true)
                        .show();
                }
            }
        })
    }

    private fun loading(b: Boolean) {
        if (b) {
            binding.shimerUlasan.visibility = View.VISIBLE
        } else {
            binding.shimerUlasan.visibility = View.GONE
        }
    }

    private fun setupViewModel() {
        repository = Repository(api)
        viewModelFactory = DetailViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
    }
}