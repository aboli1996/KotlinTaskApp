package com.turtlemint.code.test.app.comments

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.turtlemint.code.test.app.comments.activity.CommentsActivity
import com.turtlemint.code.test.app.comments.dataclass.ModelComments
import com.turtlemint.code.test.app.databinding.LayoutCommentsItemListBinding
import com.turtlemint.code.test.app.databinding.LayoutListItemIssuesBinding
import com.turtlemint.code.test.app.home.adapter.IssuesListAdapter
import com.turtlemint.code.test.app.utils.Utils

class CommentsListAdapter (var context : Context, modelComments : List<ModelComments>)
    : RecyclerView.Adapter<CommentsListAdapter.CommentsViewHolder>() {

    var issuesList : List<ModelComments> = listOf()

    init {
        issuesList = modelComments
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding = LayoutCommentsItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CommentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsListAdapter.CommentsViewHolder, position: Int) {
        val model : ModelComments = issuesList.get(position)

        holder.binding.txtCommentedUser.text = model.user!!.login ?: ""
        holder.binding.chipMember.setText(model.author_association ?: "")
        holder.binding.txtComments.text = model.body ?: ""

    }

    override fun getItemCount(): Int {
        return issuesList.size
    }

    inner class CommentsViewHolder(val binding: LayoutCommentsItemListBinding) : RecyclerView.ViewHolder(binding.root)

}