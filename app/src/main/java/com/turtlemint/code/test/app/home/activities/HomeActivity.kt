package com.turtlemint.code.test.app.home.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.turtlemint.code.test.app.R
import com.turtlemint.code.test.app.database.DatabaseUtils
import com.turtlemint.code.test.app.databinding.LayoutHomeActivityBinding
import com.turtlemint.code.test.app.home.adapter.IssuesListAdapter
import com.turtlemint.code.test.app.home.dataclass.ModelIssues
import com.turtlemint.code.test.app.retrofit.ApiClient
import com.turtlemint.code.test.app.retrofit.ApiInterface
import com.turtlemint.code.test.app.utils.Utils
import kotlinx.coroutines.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: LayoutHomeActivityBinding
    private var adapter: IssuesListAdapter? = null
    private lateinit var viewModel: IssuesViewModel
    private val tag = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LayoutHomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(IssuesViewModel::class.java)
        setUpData()
        setViewModelListeners()

    }

    override fun onResume() {
        super.onResume()
        if(Utils().checkInternetConnectivity(this)){
            getData()
        }else{
            Log.i(tag, "No Internet Connection")
            Toast.makeText(this, resources.getString(R.string.str_err_bad_internet_connectivity), Toast.LENGTH_SHORT).show()
            viewModel.getIssuesData()
        }
    }

    private fun setUpData(){

        setSupportActionBar(binding.homeToolbar)

        binding.recycler.layoutManager = LinearLayoutManager(this)
        adapter = IssuesListAdapter(this, emptyList())
        binding.recycler.adapter = adapter
    }

    private fun setViewModelListeners(){
        viewModel.issuesMutableLiveData.observe(this, Observer {
                val list : List<ModelIssues>? = it
            if(list != null){
                if(list.size > 0){
                    Log.i(tag, "issuesMutableLiveData list size - "+ list.size)
                    adapter = IssuesListAdapter(this,list)
                    binding.recycler.adapter = adapter
                    binding.recycler.adapter!!.notifyDataSetChanged()
                }
            }
        })
    }

    /*API call for getting issues data*/
    private fun getData() {
        try {
            val progressDialog = ProgressDialog(this@HomeActivity)
            progressDialog.setCancelable(false)
            progressDialog.setMessage(resources.getString(R.string.str_msg_please_wait_loading_data))
            progressDialog.show()
                lifecycleScope.launch {
                    Log.i(tag, "get Issues Data in Coroutine Scope")
                    val response = ApiClient.getInstance().create(ApiInterface::class.java).getIssues()

                    if(response.isSuccessful){
                        Log.i(tag, "get Issues Data Response success")
                        if (response.body() != null){
                            DatabaseUtils().insertIssuesData(this@HomeActivity,response.body()!!)
                            viewModel.getIssuesData()
                        }

                    }else{
                        Log.i(tag, "get Issues Data Response not success")
                        Toast.makeText(this@HomeActivity, resources.getString(R.string.str_err_response_not_success), Toast.LENGTH_SHORT).show()

                    }

                    if(!HomeActivity().isDestroyed){
                        if(progressDialog.isShowing){
                            progressDialog.dismiss()
                        }
                    }

                }

        } catch (e: Exception) {
            Log.e(tag, "get Issues Data Exception - $e")
            e.printStackTrace()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
           R.id.menu_refresh -> {
               if(Utils().checkInternetConnectivity(this)){
                   Log.i(tag, "get Issues on Refresh click")
                   getData()
               }else{
                   Log.i(tag, "No Internet Connection")
                   Toast.makeText(this, resources.getString(R.string.str_err_bad_internet_connectivity), Toast.LENGTH_SHORT).show()
                   viewModel.getIssuesData()
               }
           }
            android.R.id.home ->{

            }
        }
        return super.onOptionsItemSelected(item)
    }


}