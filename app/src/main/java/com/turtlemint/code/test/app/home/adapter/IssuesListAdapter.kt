package com.turtlemint.code.test.app.home.adapter

import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.turtlemint.code.test.app.comments.activity.CommentsActivity
import com.turtlemint.code.test.app.databinding.LayoutListItemIssuesBinding
import com.turtlemint.code.test.app.home.activities.HomeActivity
import com.turtlemint.code.test.app.home.dataclass.ModelIssues
import com.turtlemint.code.test.app.home.fragment.FragmentImageEnlarge
import com.turtlemint.code.test.app.utils.Constants.Companion.INTENT_KEY_ISSUE_ID
import com.turtlemint.code.test.app.utils.Constants.Companion.INTENT_KEY_ISSUE_TITLE
import com.turtlemint.code.test.app.utils.Constants.Companion.INTENT_KEY_ISSUE_URL
import com.turtlemint.code.test.app.utils.Utils

class IssuesListAdapter(var context : Context,var modelIssues : List<ModelIssues>)
    : RecyclerView.Adapter<IssuesListAdapter.IssuesViewHolder>() {

    var issuesList : List<ModelIssues> = listOf()

    init {
        issuesList = modelIssues

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuesViewHolder {
        val binding = LayoutListItemIssuesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return IssuesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IssuesListAdapter.IssuesViewHolder, position: Int) {
       val model : ModelIssues = issuesList.get(position)

        val content : SpannableString = SpannableString(model.title)
        content.setSpan(UnderlineSpan(),0,content.length, 0)
        holder.binding.txtIssueTitle.text = content ?: ""

        if(!TextUtils.isEmpty(model.user!!.login)){
            holder.binding.imgUserAvatar.visibility = VISIBLE
            holder.binding.txtIssueUserName.visibility = VISIBLE
            Glide.with(context).load(model.user!!.avatar_url).thumbnail(0.25f).into(holder.binding.imgUserAvatar)
            holder.binding.txtIssueUserName.text = model.user!!.login
        }else{
            holder.binding.imgUserAvatar.visibility = GONE
            holder.binding.txtIssueUserName.visibility = GONE
        }
        if(model.body != null){
            if(model.body!!.length < 200){
                holder.binding.txtIssueReadMore.visibility = GONE
                holder.binding.txtIssueReadLess.visibility = GONE
                holder.binding.txtIssueDesc.text = model.body
            }else{
                holder.binding.txtIssueReadMore.visibility = VISIBLE
                holder.binding.txtIssueReadLess.visibility = GONE
                holder.binding.txtIssueDesc.text = model.body!!.substring(0,200)
            }
        }

        holder.binding.chipCount.text = model.comments.toString()
        holder.binding.txtIssueUpdatedAtDt.text = Utils().getDate(model.updated_at ?: "")

        holder.binding.txtIssueReadMore.setOnClickListener(View.OnClickListener {
            holder.binding.txtIssueReadLess.visibility = VISIBLE
            holder.binding.txtIssueReadMore.visibility = GONE
            holder.binding.txtIssueDesc.text = model.body
        })

        holder.binding.txtIssueReadLess.setOnClickListener(View.OnClickListener {
            holder.binding.txtIssueReadLess.visibility = GONE
            holder.binding.txtIssueReadMore.visibility = VISIBLE
            holder.binding.txtIssueDesc.text = model.body!!.substring(0,200)
        })

        holder.binding.imgUserAvatar.setOnClickListener(View.OnClickListener {
            val fm = (context as HomeActivity).supportFragmentManager
            val fragment = FragmentImageEnlarge(model.user!!.login!!, model.user!!.avatar_url!!)
            fragment.show(fm,"fragment_enlarge")
        })

        holder.binding.chipCount.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, CommentsActivity::class.java)
            intent.putExtra(INTENT_KEY_ISSUE_URL, model.url)
            intent.putExtra(INTENT_KEY_ISSUE_ID, model.number)
            intent.putExtra(INTENT_KEY_ISSUE_TITLE, model.title)
            context.startActivity(intent)

        })

    }

    override fun getItemCount(): Int {
      return issuesList.size
    }

   inner class IssuesViewHolder(val binding: LayoutListItemIssuesBinding) : RecyclerView.ViewHolder(binding.root)

}