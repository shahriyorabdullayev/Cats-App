package com.example.thecatapimultipart.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatapimultipart.R
import com.example.thecatapimultipart.adapter.BreedAdapter
import com.example.thecatapimultipart.adapter.CatAdapter
import com.example.thecatapimultipart.databinding.ActivityMainBinding

import com.example.thecatapimultipart.model.Breed
import com.example.thecatapimultipart.model.Cat
import com.example.thecatapimultipart.network.ApiClient
import com.example.thecatapimultipart.network.service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var binding: ActivityMainBinding
    private lateinit var breeds: ArrayList<Breed>
    private lateinit var cats: ArrayList<Cat>

    private lateinit var rvHome: RecyclerView
    private lateinit var rvImages: RecyclerView
    private lateinit var breedAdapter: BreedAdapter
    private lateinit var catAdapter: CatAdapter
    private var page = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        apiService = ApiClient.createService(ApiService::class.java)
        breeds = ArrayList()
        cats = ArrayList()
        rvHome = binding.rvMain

        binding.btnUploadPage.setOnClickListener {
            callUploadActivity()
        }

        rvHome.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        apiBreeds(0)
        refreshAdapter(breeds)

        rvHome.addOnScrollListener(object :RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!rvHome.canScrollVertically(RecyclerView.VERTICAL) && newState == RecyclerView.SCROLL_STATE_IDLE){
                    apiBreeds(page++)
                }
            }
        })

        breedAdapter.itemClick = {
            showDialog(it)
        }




    }

    private fun callUploadActivity(){
        val intent = Intent(this, UploadActivity::class.java)
        startActivity(intent)
    }

    private fun refreshAdapter(breeds: ArrayList<Breed>){
        breedAdapter = BreedAdapter(breeds)
        rvHome.adapter = breedAdapter
    }



    private fun apiBreeds(page: Int){
        if (page < 7){
            apiService.getBreeds(page, 12).enqueue(object : Callback<List<Breed>>{
                override fun onResponse(call: Call<List<Breed>>, response: Response<List<Breed>>) {
                    Log.d("breed", response.body().toString())
                    breeds.addAll(response.body()!!)
                    breedAdapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<List<Breed>>, t: Throwable) {
                    Log.d("fail", t.localizedMessage)
                }

            })
        }


    }

    private fun showDialog(breed: Breed){
        val dialog = Dialog(this)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()


        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
        rvImages = dialog.findViewById<RecyclerView>(R.id.rv_cats)
        val tvBreed = dialog.findViewById<TextView>(R.id.tv_breed_name)
        tvBreed.text = breed.name

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        rvImages.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvImages)

        apiCatSearchByBreed(breed.id, 0)
        catAdapter = CatAdapter(cats)
        rvImages.adapter = catAdapter

        rvImages.addOnScrollListener(object :RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val manager = recyclerView.layoutManager as LinearLayoutManager?
                var activePosition = 0
                if (manager != null) {
                    activePosition = manager.findFirstVisibleItemPosition()
                }
                if (activePosition != RecyclerView.NO_POSITION) return
                Log.d("Active Position", "Active Position: $activePosition")

                if (!rvImages.canScrollVertically(RecyclerView.VERTICAL) && newState == RecyclerView.SCROLL_STATE_IDLE){

                }
            }
        })

    }

    private fun apiCatSearchByBreed(id:String, page: Int) {
        apiService.getSearchByBreed(id, page).enqueue(object : Callback<List<Cat>>{
            override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                Log.d("resBreed", response.body().toString())
                cats.clear()
                cats.addAll(response.body()!!)
                catAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {

            }

        })
    }
    private fun apiCatImages(page: Int){
        apiService.getCatImages(page, 50).enqueue(object :Callback<List<Cat>>{
            override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                Log.d("Cats", response.body().toString())
//                cats.addAll(response.body()!!)
//                catAdapter.notifyDataSetChanged()


            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                Log.d("error", t.localizedMessage)
            }

        })
    }


}