package com.turtlemint.code.test.app.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.turtlemint.code.test.app.utils.Constants
import java.lang.Exception

class DatabaseOpenHelper(context : Context, factory : SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context,Constants.DATABASE_NAME,
    factory, Constants.DATABASE_VERSION) {

    var database : SQLiteDatabase? = null
    var instance : DatabaseOpenHelper? = null

    fun getInstance(context: Context) : SQLiteDatabase? {
        if (instance == null) {
            instance = DatabaseOpenHelper(context,null)
        }
        if (database == null) {
            database = instance!!.getWritableDatabase()
        }
        return database
    }


    override fun onCreate(db: SQLiteDatabase?) {

        try {

            db!!.execSQL(
                "CREATE TABLE IF NOT EXISTS " +DatabaseTableHelper.IssuesTable.TABLE_NAME + " (" +
                        DatabaseTableHelper.IssuesTable.URL + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.REPO_URL + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.LABELS_URL + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.EVENTS_URL + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.HTML_URL + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.ID + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.NODE_ID + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.NUMBER + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.TITLE + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.STATE + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.LOCKED + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.COMMENTS + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.CREATED_AT + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.UPDATED_AT + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.CLOSED_AT + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.AUTHOR_ASSOCIATION + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.BODY + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.TIMELINE_URL + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.PERF_GIT_HUB + " TEXT, " +
                        DatabaseTableHelper.IssuesTable.STATE_REASON + " TEXT " +
                        ")"
            )

            db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + DatabaseTableHelper.UsersTable.TABLE_NAME + " (" +
                        DatabaseTableHelper.UsersTable.LOGIN + " TEXT, " +
                        DatabaseTableHelper.UsersTable.BASE_URL + " TEXT, " +
                        DatabaseTableHelper.UsersTable.ID + " TEXT, " +
                        DatabaseTableHelper.UsersTable.NODE_ID + " TEXT, " +
                        DatabaseTableHelper.UsersTable.AVATAR_URL + " TEXT, " +
                        DatabaseTableHelper.UsersTable.GRAVATAR_URL + " TEXT, " +
                        DatabaseTableHelper.UsersTable.URL + " TEXT, " +
                        DatabaseTableHelper.UsersTable.HTML_URL + " TEXT, " +
                        DatabaseTableHelper.UsersTable.FOLLOWING_URL + " TEXT, " +
                        DatabaseTableHelper.UsersTable.FOLLOWERS_URL + " TEXT, " +
                        DatabaseTableHelper.UsersTable.GISTS_URL + " TEXT, " +
                        DatabaseTableHelper.UsersTable.STARRED_URL + " TEXT, " +
                        DatabaseTableHelper.UsersTable.SUBSCRIPTIONS_URL + " TEXT, " +
                        DatabaseTableHelper.UsersTable.ORGANISATION_URL + " TEXT, " +
                        DatabaseTableHelper.UsersTable.REPO_URL + " TEXT, " +
                        DatabaseTableHelper.UsersTable.EVENTS_URL + " TEXT, " +
                        DatabaseTableHelper.UsersTable.RECEIVED_EVENTS_URL + " TEXT, " +
                        DatabaseTableHelper.UsersTable.TYPE + " TEXT, " +
                        DatabaseTableHelper.UsersTable.SITE_ADMIN + " TEXT " +
                        ")"
            )

            db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + DatabaseTableHelper.CommentsTable.TABLE_NAME + " (" +
                        DatabaseTableHelper.CommentsTable.URL + " TEXT, " +
                        DatabaseTableHelper.CommentsTable.ISSUE_URL + " TEXT, " +
                        DatabaseTableHelper.CommentsTable.HTML_URL + " TEXT, " +
                        DatabaseTableHelper.CommentsTable.ID + " TEXT, " +
                        DatabaseTableHelper.CommentsTable.NODE_ID + " TEXT, " +
                        DatabaseTableHelper.CommentsTable.CREATED_AT + " TEXT, " +
                        DatabaseTableHelper.CommentsTable.UPDATED_AT + " TEXT, " +
                        DatabaseTableHelper.CommentsTable.AUTHOR_ASSOCIATION + " TEXT, " +
                        DatabaseTableHelper.CommentsTable.BODY + " TEXT, " +
                        DatabaseTableHelper.CommentsTable.PERF_GIT_HUB + " TEXT " +
                        ")"
            )

        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }


}