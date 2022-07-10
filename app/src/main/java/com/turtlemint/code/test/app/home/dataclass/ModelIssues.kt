package com.turtlemint.code.test.app.home.dataclass

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ModelIssues (

    var url : String?,
    var repository_url : String?,
    var labels_url : String? ,
    var comments_url : String? ,
    var events_url : String?,
    var html_url : String?,
    var id : Int,
    var node_id : String?,
    var number : Int?,
    var title : String?,
    var user : ModelUser?,
    var labels : List<ModelLabels>?,
    var state : String?,
    var locked : Boolean?,
    var comments : Int,
    var  created_at : String?,
    var updated_at : String?,
    var  closed_at : String?,
    var author_association : String?,
    var  active_lock_reason : String?,
    var  body : String?,
    var reactions : ModelReactions?,
    var  timeline_url : String?,
    var  performed_via_github_app : String?,
    var  state_reason : String?){

    constructor(): this("","","","","","",0,"",0,"",
        null, emptyList(),"",false,0,"","","","","","",null,
        "","","")



}