package com.pouyaa.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sevenminuteworkout.Constants
import com.sevenminuteworkout.ExerciseModel
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.activity_exercise.view.*
import kotlinx.android.synthetic.main.activity_exercise_org.view.*
import kotlinx.android.synthetic.main.dialoug_custom_back.*
import kotlinx.android.synthetic.main.progress_bar.view.*
import kotlinx.android.synthetic.main.progress_bar.view.progressBar
import kotlinx.android.synthetic.main.progress_bar.view.tvTimer
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var restProgress = 0
    private var restTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var currentExercisePosition = -1
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null
    private var restDuration: Long = 10
    private var exerciseDuration: Long = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbarExerciseActivity)
        val actionbar = supportActionBar

        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        toolbarExerciseActivity.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        exerciseList = Constants.defaultExerciseList()
        setupRestView()
        tts = TextToSpeech(this, this)

        setupExerciseRecycleView()

    }

    override fun onDestroy() {

        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }


        super.onDestroy()
    }

    private fun setRestProgress() {
        restProgressBar.progressBar.progress = restProgress


        restTimer = object : CountDownTimer(restDuration * 1000, 1000) {
            override fun onFinish() {
                currentExercisePosition++
                // llRestView.visibility = View.GONE
                tvGetReady.visibility = View.GONE
                restProgressBar.visibility = View.GONE
                tvUpcomingExercise.visibility = View.GONE
                tvUpcomingExerciseName.visibility = View.GONE

//                llExerciseView.visibility = View.VISIBLE
                ivExercise.visibility = View.VISIBLE
                tvExerciseName.visibility = View.VISIBLE
                exerciseProgressBar.visibility = View.VISIBLE


                setupExerciseView()

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
            }

            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                restProgressBar.progressBar.progress = (restDuration.toInt() - restProgress)
                restProgressBar.tvTimer.text = (restDuration.toInt() - restProgress).toString()

            }

        }.start()

    }

    private fun setupRestView() {

        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        restProgressBar.progressBar.max = restDuration.toInt()
        setRestProgress()
        tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()
    }

    private fun setExerciseView() {

        exerciseProgressBar.progressBar.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(exerciseDuration * 1000, 1000) {
            override fun onFinish() {

                if (currentExercisePosition < exerciseList!!.size - 1) {
                    setupRestView()

                    // llExerciseView.visibility = View.GONE
                    ivExercise.visibility = View.GONE
                    tvExerciseName.visibility = View.GONE
                    exerciseProgressBar.visibility = View.GONE

                    // llRestView.visibility = View.VISIBLE

                    tvGetReady.visibility = View.VISIBLE
                    restProgressBar.visibility = View.VISIBLE
                    tvUpcomingExercise.visibility = View.VISIBLE
                    tvUpcomingExerciseName.visibility = View.VISIBLE

                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                } else {
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }

            }

            override fun onTick(millisUntilFinished: Long) {
                //Toast.makeText(this@ExerciseActivity,exerciseProgress,Toast.LENGTH_SHORT).show()
                exerciseProgress++
                exerciseProgressBar.progressBar.progress = (exerciseDuration.toInt() - exerciseProgress)
                exerciseProgressBar.tvTimer.text = (exerciseDuration.toInt() - exerciseProgress).toString()

            }

        }.start()

    }

    private fun setupExerciseView() {
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        exerciseProgressBar.progressBar.max = exerciseDuration.toInt()
        speakOut(exerciseList!![currentExercisePosition].getName())
        setExerciseView()
        ivExercise.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("tts", "missing data or lang not supported")
            } else {
                Log.e("tts", "tts not success")
            }
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setupExerciseRecycleView() {
        rvExerciseStatus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        rvExerciseStatus.adapter = exerciseAdapter
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialoug_custom_back)
        customDialog.dialogBtnYes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        customDialog.dialogBtnNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }

}
