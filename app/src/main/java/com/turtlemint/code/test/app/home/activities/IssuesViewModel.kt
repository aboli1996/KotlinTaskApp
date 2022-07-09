package com.turtlemint.code.test.app.home.activities

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.turtlemint.code.test.app.utils.Constants
import com.turtlemint.code.test.app.database.DatabaseUtils
import com.turtlemint.code.test.app.home.dataclass.ModelIssues
import kotlinx.coroutines.launch

class IssuesViewModel(application: Application) : AndroidViewModel(application) {
    val issuesMutableLiveData = MutableLiveData<List<ModelIssues>?>()
    val progressState = MutableLiveData<Int>()
    var modelIssueList : List<ModelIssues> = emptyList()
    val tag = "CommentsViewModel"

    fun getDummyData(){
        progressState.value = Constants.IN_PROGRESS
        viewModelScope.launch {
            Log.i(tag, "get Issues Data")
            modelIssueList = DatabaseUtils().getIssuesData(getApplication())
        }
        setIssuesData(modelIssueList)
    }

    fun setIssuesData(issueList : List<ModelIssues>?){
        if(issueList != null){
            if(issueList.size > 0){
                progressState.value = Constants.IN_COMPLETE_WITH_ZERO
            }else{
                progressState.value = Constants.IN_COMPLETE
            }
            issuesMutableLiveData.value = issueList
        }

    }

}