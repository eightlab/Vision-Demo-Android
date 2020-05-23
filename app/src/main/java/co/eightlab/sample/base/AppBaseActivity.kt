package co.eightlab.sample.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class AppBaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResourceId)
        initView()
    }

    protected abstract val layoutResourceId: Int

    protected abstract fun initView()
}