package com.turtlemint.code.test.app.comments.activity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.turtlemint.code.test.app.comments.dataclass.ModelComments
import com.turtlemint.code.test.app.database.DatabaseUtils
import com.turtlemint.code.test.app.utils.Constants
import kotlinx.coroutines.launch

class CommentsViewModel (application: Application) : AndroidViewModel(application) {
    val commentsMutableLiveData = MutableLiveData<List<ModelComments>?>()
    val progressState = MutableLiveData<Int>()
    var modelCommentsList : List<ModelComments> = emptyList()
    val tag = "CommentsViewModel"

    fun getDummyData(issueUrl : String){
        progressState.value = Constants.IN_PROGRESS
        viewModelScope.launch {
            Log.i(tag, "get Comments on Issues Data")
            modelCommentsList = DatabaseUtils().getCommentsData(getApplication(), issueUrl)
        }
        setCommentsData(modelCommentsList)
    }

    fun setCommentsData(commentsList : List<ModelComments>?){
        if(commentsList != null){
            if(commentsList.size > 0){
                progressState.value = Constants.IN_COMPLETE_WITH_ZERO
            }else{
                progressState.value = Constants.IN_COMPLETE
            }
            commentsMutableLiveData.value = commentsList
        }

    }

}