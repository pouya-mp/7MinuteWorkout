package com.pouyaa.a7minuteworkout

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqLiteOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME,factory,
    DATABASE_VERSION) {

    companion object{

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "SevenMinutesWorkout.db"
        private const val TABLE_HISTORY = "history"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_COMPLETED_DATE = "completedDate"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createExerciseTable = ("CREATE TABLE $TABLE_HISTORY($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_COMPLETED_DATE TEXT)")
        db?.execSQL(createExerciseTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY")
        onCreate(db)
    }

    fun addDate(date: String){
        val values = ContentValues()
        values.put(COLUMN_COMPLETED_DATE,date)
        val db = writableDatabase
        db.insert(TABLE_HISTORY,null, values)
        db.close()
    }

    fun getAllCompletedDates() : ArrayList<String> {
        val list = ArrayList<String>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY", null)

        while (cursor.moveToNext()){
            val date = cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE))
            list.add(date)

        }
        cursor.close()

        return list
    }

}