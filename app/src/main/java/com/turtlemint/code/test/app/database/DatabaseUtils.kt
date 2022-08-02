package com.turtlemint.code.test.app.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.turtlemint.code.test.app.comments.dataclass.ModelComments
import com.turtlemint.code.test.app.home.dataclass.ModelIssues
import com.turtlemint.code.test.app.home.dataclass.ModelUser

class DatabaseUtils {

    fun insertIssuesData(context: Context, issuesList: List<ModelIssues>) {
        val database = DatabaseOpenHelper(context, null).getInstance(context)
        var values: ContentValues = ContentValues()

        try {
            for (model in issuesList.indices) {
                values.put(DatabaseTableHelper.IssuesTable.URL, issuesList.get(model).url)
                values.put(
                    DatabaseTableHelper.IssuesTable.REPO_URL,
                    issuesList.get(model).repository_url
                )
                values.put(
                    DatabaseTableHelper.IssuesTable.LABELS_URL,
                    issuesList.get(model).labels_url
                )
                values.put(
                    DatabaseTableHelper.IssuesTable.EVENTS_URL,
                    issuesList.get(model).events_url
                )
                values.put(
                    DatabaseTableHelper.IssuesTable.HTML_URL,
                    issuesList.get(model).html_url
                )
                values.put(DatabaseTableHelper.IssuesTable.ID, issuesList.get(model).id)
                values.put(DatabaseTableHelper.IssuesTable.NODE_ID, issuesList.get(model).node_id)
                values.put(DatabaseTableHelper.IssuesTable.NUMBER, issuesList.get(model).number)
                values.put(DatabaseTableHelper.IssuesTable.TITLE, issuesList.get(model).title)
                values.put(DatabaseTableHelper.IssuesTable.STATE, issuesList.get(model).state)
                values.put(DatabaseTableHelper.IssuesTable.LOCKED, issuesList.get(model).locked)
                values.put(
                    DatabaseTableHelper.IssuesTable.COMMENTS,
                    issuesList.get(model).comments
                )
                values.put(
                    DatabaseTableHelper.IssuesTable.CREATED_AT,
                    issuesList.get(model).created_at
                )
                values.put(
                    DatabaseTableHelper.IssuesTable.UPDATED_AT,
                    issuesList.get(model).updated_at
                )
                values.put(
                    DatabaseTableHelper.IssuesTable.CLOSED_AT,
                    issuesList.get(model).closed_at
                )
                values.put(
                    DatabaseTableHelper.IssuesTable.AUTHOR_ASSOCIATION,
                    issuesList.get(model).author_association
                )
                values.put(DatabaseTableHelper.IssuesTable.BODY, issuesList.get(model).body)
                values.put(
                    DatabaseTableHelper.IssuesTable.TIMELINE_URL,
                    issuesList.get(model).timeline_url
                )
                values.put(
                    DatabaseTableHelper.IssuesTable.PERF_GIT_HUB,
                    issuesList.get(model).performed_via_github_app
                )
                values.put(
                    DatabaseTableHelper.IssuesTable.STATE_REASON,
                    issuesList.get(model).state_reason
                )

                if (isExists(
                        database!!,
                        DatabaseTableHelper.IssuesTable.TABLE_NAME,
                        DatabaseTableHelper.IssuesTable.URL,
                        issuesList.get(model).url!!
                    )
                ) {
                    database.update(
                        DatabaseTableHelper.IssuesTable.TABLE_NAME,
                        values,
                        DatabaseTableHelper.IssuesTable.URL + " =? ",
                        arrayOf(issuesList.get(model).url!!)
                    )
                } else {
                    database.insert(DatabaseTableHelper.IssuesTable.TABLE_NAME, null, values)
                }

                insertUsersData(context, issuesList.get(model).user!!, issuesList.get(model).url!!)

            }
        }catch (e : Exception) {
            Log.i("insertIssuesData", "exception - $e")
            e.printStackTrace()
        }
    }


    fun insertUsersData(context: Context, modelUser: ModelUser, baseurl : String) {
        val database = DatabaseOpenHelper(context, null).getInstance(context)
        var values: ContentValues = ContentValues()

        try {
            values.put(DatabaseTableHelper.UsersTable.LOGIN, modelUser.login)
            values.put(DatabaseTableHelper.UsersTable.BASE_URL, baseurl)
            values.put(DatabaseTableHelper.UsersTable.ID, modelUser.id)
            values.put(DatabaseTableHelper.UsersTable.NODE_ID, modelUser.node_id)
            values.put(DatabaseTableHelper.UsersTable.AVATAR_URL, modelUser.avatar_url)
            values.put(DatabaseTableHelper.UsersTable.GRAVATAR_URL, modelUser.gravatar_id)
            values.put(DatabaseTableHelper.UsersTable.URL, modelUser.url)
            values.put(DatabaseTableHelper.UsersTable.HTML_URL, modelUser.html_url)
            values.put(DatabaseTableHelper.UsersTable.FOLLOWING_URL, modelUser.following_url)
            values.put(DatabaseTableHelper.UsersTable.FOLLOWERS_URL, modelUser.followers_url)
            values.put(DatabaseTableHelper.UsersTable.GISTS_URL, modelUser.gists_url)
            values.put(DatabaseTableHelper.UsersTable.STARRED_URL, modelUser.starred_url)
            values.put(DatabaseTableHelper.UsersTable.SUBSCRIPTIONS_URL, modelUser.subscriptions_url)
            values.put(DatabaseTableHelper.UsersTable.ORGANISATION_URL, modelUser.organizations_url)
            values.put(DatabaseTableHelper.UsersTable.REPO_URL, modelUser.repos_url)
            values.put(DatabaseTableHelper.UsersTable.EVENTS_URL, modelUser.events_url)
            values.put(DatabaseTableHelper.UsersTable.RECEIVED_EVENTS_URL, modelUser.received_events_url)
            values.put(DatabaseTableHelper.UsersTable.TYPE, modelUser.type)
            values.put(DatabaseTableHelper.UsersTable.SITE_ADMIN, modelUser.site_admin)

            if (isExists(database!!, DatabaseTableHelper.UsersTable.TABLE_NAME, DatabaseTableHelper.UsersTable.BASE_URL, baseurl)) {
                database.update(DatabaseTableHelper.UsersTable.TABLE_NAME, values, DatabaseTableHelper.UsersTable.BASE_URL + " =? ",
                    arrayOf(baseurl)
                )
            } else {
                database.insert(DatabaseTableHelper.UsersTable.TABLE_NAME, null, values)
            }

        }catch (e :Exception){
            Log.i("insertUsersData", "exception - $e")
            e.printStackTrace()
        }


    }

    fun isExists(
        database: SQLiteDatabase,
        table: String,
        columnName: String,
        value: String
    ): Boolean {
        var isExists = false

        val cursor: Cursor = database.rawQuery(
            "Select * from " + table + " where " + columnName + " ='" + value + "' ",
            null
        )
        if (cursor != null) {
            if (cursor.count > 0) {
                isExists = true
            }
            cursor.close()
        }
        return isExists
    }

    @SuppressLint("Range")
    fun getIssuesData(context: Context): List<ModelIssues> {
        val database = DatabaseOpenHelper(context, null).getInstance(context)
        var issuesList: ArrayList<ModelIssues> = ArrayList()
        var cursor: Cursor? = null
        try {
            val query : String = "Select * from " + DatabaseTableHelper.IssuesTable.TABLE_NAME
            Log.i("getIssuesData", "query : $query")
            cursor = database!!.rawQuery(query, null)

            if (cursor != null) {
                Log.i("getIssuesData", "cursor.count - $cursor.count")
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    do {
                        var model = ModelIssues()
                        model.title = cursor.getString(cursor.getColumnIndex(DatabaseTableHelper.IssuesTable.TITLE))
                        model.body = cursor.getString(cursor.getColumnIndex(DatabaseTableHelper.IssuesTable.BODY))
                        model.updated_at = cursor.getString(cursor.getColumnIndex(DatabaseTableHelper.IssuesTable.UPDATED_AT))
                        model.number = cursor.getString(cursor.getColumnIndex(DatabaseTableHelper.IssuesTable.NUMBER)).toInt()
                        model.url = cursor.getString(cursor.getColumnIndex(DatabaseTableHelper.IssuesTable.URL))
                        model.comments = cursor.getString(cursor.getColumnIndex(DatabaseTableHelper.IssuesTable.COMMENTS)).toInt()
                        model.user = getUserData(context,model.url!!)
                        issuesList.add(model)

                    } while (cursor.moveToNext())
                }
                cursor.close()
            }
        } catch (e: Exception) {
            Log.i("getIssuesData", "exception - $e")
            e.printStackTrace()
        }
        return issuesList
    }

    @SuppressLint("Range")
    fun getUserData(context: Context, baseurl: String) : ModelUser{
        val database = DatabaseOpenHelper(context, null).getInstance(context)
        var modelUser : ModelUser = ModelUser()
        var cursor: Cursor? = null

        try {
            val query : String = "Select * from " + DatabaseTableHelper.UsersTable.TABLE_NAME + " where " + DatabaseTableHelper.UsersTable.BASE_URL + " = '" + baseurl + "' "
            Log.i("getUserData", "query : $query")
            cursor = database!!.rawQuery(query, null)

            if (cursor != null) {
                Log.i("getUserData", "cursor.count - $cursor.count")
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    do {
                        modelUser.login = cursor.getString(cursor.getColumnIndex(DatabaseTableHelper.UsersTable.LOGIN))
                        modelUser.avatar_url = cursor.getString(cursor.getColumnIndex(DatabaseTableHelper.UsersTable.AVATAR_URL))

                    } while (cursor.moveToNext())
                }
                cursor.close()
            }
        } catch (e: Exception) {
            Log.i("getUserData", "exception - $e")
            e.printStackTrace()
        }
        return modelUser
    }

    fun insertCommentsData(context: Context, commentsList: List<ModelComments>) {
        val database = DatabaseOpenHelper(context, null).getInstance(context)
        var values: ContentValues = ContentValues()
        try {

            for (model in commentsList.indices) {
                values.put(DatabaseTableHelper.CommentsTable.URL, commentsList.get(model).url)
                values.put(
                    DatabaseTableHelper.CommentsTable.ISSUE_URL,
                    commentsList.get(model).issue_url
                )
                values.put(
                    DatabaseTableHelper.CommentsTable.HTML_URL,
                    commentsList.get(model).html_url
                )
                values.put(DatabaseTableHelper.CommentsTable.ID, commentsList.get(model).id)
                values.put(
                    DatabaseTableHelper.CommentsTable.NODE_ID,
                    commentsList.get(model).node_id
                )
                values.put(
                    DatabaseTableHelper.CommentsTable.CREATED_AT,
                    commentsList.get(model).created_at
                )
                values.put(
                    DatabaseTableHelper.CommentsTable.UPDATED_AT,
                    commentsList.get(model).updated_at
                )
                values.put(
                    DatabaseTableHelper.CommentsTable.AUTHOR_ASSOCIATION,
                    commentsList.get(model).author_association
                )
                values.put(DatabaseTableHelper.CommentsTable.BODY, commentsList.get(model).body)
                values.put(
                    DatabaseTableHelper.CommentsTable.PERF_GIT_HUB,
                    commentsList.get(model).performed_via_github_app
                )

                if (isExists(
                        database!!,
                        DatabaseTableHelper.CommentsTable.TABLE_NAME,
                        DatabaseTableHelper.CommentsTable.URL,
                        commentsList.get(model).url!!
                    )
                ) {
                    database.update(
                        DatabaseTableHelper.CommentsTable.TABLE_NAME,
                        values,
                        DatabaseTableHelper.CommentsTable.URL + " =? ",
                        arrayOf(commentsList.get(model).url!!)
                    )
                } else {
                    database.insert(DatabaseTableHelper.CommentsTable.TABLE_NAME, null, values)
                }

                insertUsersData(
                    context,
                    commentsList.get(model).user!!,
                    commentsList.get(model).url!!
                )

            }
        }catch (e : Exception){
            Log.i("insertCommentsData", "exception - $e")
            e.printStackTrace()
        }
    }

    @SuppressLint("Range")
    fun getCommentsData(context: Context, issueUrl: String): List<ModelComments> {
        val database = DatabaseOpenHelper(context, null).getInstance(context)
        var commentList: ArrayList<ModelComments> = ArrayList()
        var cursor: Cursor? = null

        try {

            val query : String = "Select * from " + DatabaseTableHelper.CommentsTable.TABLE_NAME + " where " +
                    DatabaseTableHelper.CommentsTable.ISSUE_URL + " = '" + issueUrl + "' "
            Log.i("getCommentsData", "query : $query")
            cursor = database!!.rawQuery(query,null)

            if (cursor != null) {
                Log.i("getCommentsData", "cursor.count : $cursor.count")
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    do {
                        var model = ModelComments()
                        model.url = cursor.getString(cursor.getColumnIndex(DatabaseTableHelper.CommentsTable.URL))
                        model.body = cursor.getString(cursor.getColumnIndex(DatabaseTableHelper.CommentsTable.BODY))
                        model.updated_at = cursor.getString(cursor.getColumnIndex(DatabaseTableHelper.CommentsTable.UPDATED_AT))
                        model.author_association = cursor.getString(cursor.getColumnIndex(DatabaseTableHelper.CommentsTable.AUTHOR_ASSOCIATION))
                        model.user = getUserData(context,model.url!!)
                        commentList.add(model)

                    } while (cursor.moveToNext())

                }
                cursor.close()
            }
        } catch (e: Exception) {
            Log.i("getCommentsData", "exception - $e")
            e.printStackTrace()
        }
        return commentList
    }

}