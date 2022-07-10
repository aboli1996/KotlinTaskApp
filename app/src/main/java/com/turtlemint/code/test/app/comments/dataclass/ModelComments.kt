package com.turtlemint.code.test.app.comments.dataclass

import com.google.gson.Gson
import com.turtlemint.code.test.app.home.dataclass.ModelReactions
import com.turtlemint.code.test.app.home.dataclass.ModelUser

/*Data model for Comments on Issues*/
data class ModelComments(
    var url : String?,
    var html_url : String?,
    var issue_url : String?,
    var id : Int?,
    var node_id : String?,
    var user : ModelUser?,
    var  created_at : String?,
    var updated_at : String?,
    var author_association : String?,
    var  body : String?,
    var reactions : ModelReactions?,
    var  performed_via_github_app : String?,
    ){

    constructor(): this("","","",0,"",null, "", "", "", "", null, "")



}
