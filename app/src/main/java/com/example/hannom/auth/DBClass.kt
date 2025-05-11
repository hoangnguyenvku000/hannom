package com.example.hannom.auth
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class DBClass(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "AmirDatabase"
        private const val TABLE_CONTACTS = "user"
        private const val KEY_NAME = "name"
        private const val KEY_UNAME = "username"
        private const val KEY_PSWD = "pswd"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val newtb = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_NAME + " TEXT," + KEY_UNAME + " TEXT,"
                + KEY_PSWD + " TEXT" + ")")
        db?.execSQL(newtb)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

}