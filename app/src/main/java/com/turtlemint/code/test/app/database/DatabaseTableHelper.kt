package com.turtlemint.code.test.app.database

class DatabaseTableHelper {

    interface IssuesTable {
        companion object {
            val TABLE_NAME: String = "issues_table"
            val URL: String = "url"
            val REPO_URL = "repo_url"
            val LABELS_URL = "labels_url"
            val EVENTS_URL = "events_url"
            val HTML_URL = "html_url"
            val ID = "id"
            val NODE_ID = "node_id"
            val NUMBER = "number"
            val TITLE = "title"
            val STATE = "state"
            val LOCKED = "locked"
            val COMMENTS = "comments"
            val CREATED_AT = "created_at"
            val UPDATED_AT = "updates_at"
            val CLOSED_AT = "closed_at"
            val AUTHOR_ASSOCIATION = "author_association"
            val BODY = "body"
            val TIMELINE_URL = "timeline_url"
            val PERF_GIT_HUB = "perf_git_hub"
            val STATE_REASON = "state_reason"
        }

    }

    interface UsersTable {
        companion object {
            val TABLE_NAME = "users_table"
            val BASE_URL = "base_url"
            val LOGIN = "login"
            val ID = "id"
            val NODE_ID = "node_id"
            val AVATAR_URL = "avatar_url"
            val GRAVATAR_URL = "gravatar_url"
            val URL = "url"
            val HTML_URL = "html_url"
            val FOLLOWING_URL = "following_url"
            val FOLLOWERS_URL = "followers_url"
            val GISTS_URL = "gists_url"
            val STARRED_URL = "starred_url"
            val SUBSCRIPTIONS_URL = "subscription_url"
            val ORGANISATION_URL = "organisation_url"
            val REPO_URL = "repo_url"
            val EVENTS_URL = "events_url"
            val RECEIVED_EVENTS_URL = "received_events_url"
            val TYPE = "type"
            val SITE_ADMIN = "site_admin"
        }

    }

    interface CommentsTable {
        companion object {
            val TABLE_NAME = "comments_table"
            val URL: String = "url"
            val ISSUE_URL: String = "issue_url"
            val HTML_URL = "html_url"
            val ID = "id"
            val NODE_ID = "node_id"
            val CREATED_AT = "created_at"
            val UPDATED_AT = "updates_at"
            val AUTHOR_ASSOCIATION = "author_association"
            val BODY = "body"
            val PERF_GIT_HUB = "perf_git_hub"
        }

    }
}