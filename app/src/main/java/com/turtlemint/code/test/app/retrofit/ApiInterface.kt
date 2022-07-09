package com.turtlemint.code.test.app.retrofit

import com.turtlemint.code.test.app.comments.dataclass.ModelComments
import com.turtlemint.code.test.app.home.dataclass.ModelIssues
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("/repos/square/okhttp/issues")
    suspend fun getIssues() : Response<List<ModelIssues>>


    @GET("/repos/square/okhttp/issues/{issueID}/comments")
    suspend fun getComments(@Path("issueID") issueId : String) : Response<List<ModelComments>>
}