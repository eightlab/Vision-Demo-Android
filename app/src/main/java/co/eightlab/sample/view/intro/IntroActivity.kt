package co.eightlab.sample.view.intro

import co.eightlab.sample.R
import co.eightlab.sample.base.AppBaseActivity
import co.eightlab.sample.view.country.CountrySelectionActivity
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppBaseActivity() {
    override val layoutResourceId: Int
        get() = R.layout.activity_intro

    override fun initView() {
        startButton.setOnClickListener {
            CountrySelectionActivity.start(this)
        }
    }
}