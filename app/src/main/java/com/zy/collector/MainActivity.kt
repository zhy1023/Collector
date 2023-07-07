package com.zy.collector

import android.util.Log
import com.zy.collector.databinding.ActivityMainBinding
import com.zy.common.base.BaseBindingActivity
import com.zy.common.ext.launchActivity

class MainActivity : BaseBindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    init {
        Log.i(TAG, stringFromJNI())
    }

    override fun initView() {
        b.download.setOnClickListener { launchActivity<DownloadActivity>() }
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

}