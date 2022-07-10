package com.turtlemint.code.test.app.comments.activity

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
import com.turtlemint.code.test.app.comments.CommentsListAdapter
import com.turtlemint.code.test.app.comments.dataclass.ModelComments
import com.turtlemint.code.test.app.database.DatabaseUtils
import com.turtlemint.code.test.app.databinding.LayoutCommentsActivityBinding
import com.turtlemint.code.test.app.retrofit.ApiClient
import com.turtlemint.code.test.app.retrofit.ApiInterface
import com.turtlemint.code.test.app.utils.Constants.Companion.INTENT_KEY_ISSUE_ID
import com.turtlemint.code.test.app.utils.Constants.Companion.INTENT_KEY_ISSUE_TITLE
import com.turtlemint.code.test.app.utils.Constants.Companion.INTENT_KEY_ISSUE_URL
import com.turtlemint.code.test.app.utils.Utils
import kotlinx.coroutines.*

class CommentsActivity : AppCompatActivity(){

    private lateinit var binding: LayoutCommentsActivityBinding
    private var adapter: CommentsListAdapter? = null
    private lateinit var viewModel: CommentsViewModel
    private var issueId : String = ""
    private var issueUrl : String = ""
    private var issueTitle : String = ""
    private val tag = "CommentsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LayoutCommentsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(CommentsViewModel::class.java)
        Log.i(tag, "oncreate")
        getIntentValues()
        setUpData()
        setViewModelListeners()
        if(Utils().checkInternetConnectivity(this)){
            getData()
        }else{
            Log.i(tag, "No Internet Connection")
            Toast.makeText(this, resources.getString(R.string.str_err_bad_internet_connectivity), Toast.LENGTH_SHORT).show()
            viewModel.getCommentsIsuesData(issueUrl)
        }
    }

    /*getting intent values */
    private fun getIntentValues(){
        if(intent != null){
            issueId = intent.extras!!.get(INTENT_KEY_ISSUE_ID).toString()
            issueUrl = intent.extras!!.get(INTENT_KEY_ISSUE_URL).toString()
            issueTitle = intent.extras!!.get(INTENT_KEY_ISSUE_TITLE).toString()
            Log.i(tag, "intent values: issueId = $issueId")
            Log.i(tag, "intent values: issueUrl = $issueUrl")
            Log.i(tag, "intent values: issueTitle = $issueTitle")
        }
    }

    private fun setUpData(){
        setSupportActionBar(binding.toolbarCommentsActivity)
        if(supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        binding.toolbarCommentsActivity.setSubtitle(resources.getString(R.string.str_msg_issue_id) + "$issueId")
        binding.txtIssueValue.text = issueTitle

        binding.recycler.layoutManager = LinearLayoutManager(this)
        adapter = CommentsListAdapter(this, emptyList())
        binding.recycler.adapter = adapter
    }

    private fun setViewModelListeners(){
        viewModel.commentsMutableLiveData.observe(this, Observer {
            val list : List<ModelComments>? = it
            if(list != null){
                if(list.size > 0){
                    Log.i(tag, "commentsMutableLiveData list size = "+ list.size)
                    adapter = CommentsListAdapter(this,list)
                    binding.recycler.adapter = adapter
                    binding.recycler.adapter!!.notifyDataSetChanged()
                }
            }
        })
    }


    /*API call for Comments on issues Data*/
    private fun getData() {
        try {
            val progressDialog = ProgressDialog(this)
            progressDialog.setCancelable(false)
            progressDialog.setMessage(resources.getString(R.string.str_msg_please_wait_loading_data))
            progressDialog.show()
              lifecycleScope.launch {
                  Log.i(tag, "get Comments on Issues Data in Coroutine Scope")
                    val response = ApiClient.getInstance().create(ApiInterface::class.java).getComments(issueId)

                    if(response.isSuccessful){
                        Log.i(tag, "get Comments on Issues Data Response success")
                        if (response.body() != null){

                            DatabaseUtils().insertCommentsData(this@CommentsActivity,response.body()!!)
                            viewModel.getCommentsIsuesData(issueUrl)
                        }

                    }else{
                        Log.i(tag, "get Comments on Issues Data Response not success")
                        Toast.makeText(this@CommentsActivity, resources.getString(R.string.str_err_response_not_success), Toast.LENGTH_SHORT).show()

                    }

                  if(!CommentsActivity().isDestroyed){
                      if(progressDialog.isShowing){
                          progressDialog.dismiss()
                      }
                  }



                }

        } catch (e: Exception) {
            Log.e(tag, "get Comments on Issues Data Exception - $e")
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
                    Log.i(tag, "get Comments on Issues on Refresh click")
                    getData()
                }else{
                    Log.i(tag, "No Internet Connection")
                    Toast.makeText(this, resources.getString(R.string.str_err_bad_internet_connectivity), Toast.LENGTH_SHORT).show()
                    viewModel.getCommentsIsuesData(issueUrl)
                }
            }
            android.R.id.home -> super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}