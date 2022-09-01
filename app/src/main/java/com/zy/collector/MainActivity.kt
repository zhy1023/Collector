package com.zy.collector

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Example of a call to a native method
        Log.i(TAG, stringFromJNI())
        initView();
    }

    private fun initView() {
        findViewById<View>(R.id.download).setOnClickListener(this)
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    private external fun stringFromJNI(): String

    private external fun getIndex(): Int

    companion object {
        const val TAG = "MainActivity"

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.download -> startActivity(Intent(this, DownloadActivity::class.java))

        }
    }
}