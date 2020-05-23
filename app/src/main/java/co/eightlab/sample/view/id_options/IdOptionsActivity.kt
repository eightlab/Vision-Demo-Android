package co.eightlab.sample.view.id_options

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import co.eightlab.EightVisionSdk
import co.eightlab.eightvision.Constants
import co.eightlab.eightvision.scanner.ScanResult
import co.eightlab.model.CardType
import co.eightlab.sample.R
import co.eightlab.sample.base.AppBaseActivity
import co.eightlab.sample.model.Card
import co.eightlab.sample.model.CountryData
import co.eightlab.sample.utils.extensions.toast
import co.eightlab.sample.utils.extensions.v
import co.eightlab.sample.view.id_options.adapter.IdOptionListAdapter
import co.eightlab.sample.view.result.ResultActivity
import kotlinx.android.synthetic.main.activity_id_options.*
import kotlinx.android.synthetic.main.toolbar_main.*

class IdOptionsActivity : AppBaseActivity() {
    override val layoutResourceId: Int
        get() = R.layout.activity_id_options

    override fun initView() {
        setupToolbar()

        var countryData: CountryData? = intent.getParcelableExtra(INTENT_EXTRA_COUNTRY_DATA)
        setupRecyclerView(countryData)
    }

    /**
     * set up recycler view
     */
    private fun setupRecyclerView(countryData: CountryData?) {
        if (countryData?.cards == null || countryData.cards.isEmpty()) {
            toast(getString(R.string.please_contact_our_customer_support))
            return
        }
        idOptionsRecyclerView.layoutManager = LinearLayoutManager(this)
        idOptionsRecyclerView.setHasFixedSize(true)
        idOptionsRecyclerView.adapter = IdOptionListAdapter(countryData?.cards) {
            idOptionsClicked(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EightVisionSdk.REQUEST_EIGHT_VISION_ID_OCR) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                if (data.hasExtra(Constants.EXTRA_SCAN_RESULT)) {
                    val result: ScanResult = data.getParcelableExtra(Constants.EXTRA_SCAN_RESULT)
                    openResultActivity(result)
                } else v(TAG, getString(R.string.please_contact_our_customer_support))
            } else { //Result failed
                toast(getString(R.string.scan_failed_try_again))
            }
        }
    }

    private fun openResultActivity(result: ScanResult) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(Constants.EXTRA_SCAN_RESULT, result)
        startActivity(intent)
    }


    /**
     * Start OCR based on ID card clicked
     * 1 => Passport
     * 2 => MyKad
     * 3 => iKad
     * 4 => UNHDCR
     */
    private fun idOptionsClicked(card: Card) {
        when (card.id) {
            1 -> startLiveScan(CardType.PASSPORT, SCAN_TIME)
            2 -> startLiveScan(CardType.MYKAD, SCAN_TIME)
            3 -> startLiveScan(CardType.IKAD, SCAN_TIME)
            4 -> startLiveScan(CardType.UNHCR, SCAN_TIME)
            5 -> startLiveScan(CardType.MYTENTERA, SCAN_TIME)
            else -> {
                toast(getString(R.string.please_contact_our_customer_support))
            }
        }
    }

    /**
     * start the sdk
     */
    private fun startLiveScan(cardType: CardType, scanTime: Int) {
        val validLicense = ""   //please add valid license key here
        EightVisionSdk.Builder()
                .apiKey(validLicense)
                .cardType(cardType)
                .dateFormat("MM/dd/yyyy")
                .scanTime(scanTime)
                .build()
                .start(this)
    }

    /**
     * set up toolbar and click listeners
     */
    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        titleTextView.text = getString(R.string.identity_verification_text)
        backImageView.setOnClickListener { onBackPressed() }
    }

    companion object {
        private val SCAN_TIME = 30 //time in second

        private val TAG = IdOptionsActivity::class.java.simpleName
        private const val INTENT_EXTRA_COUNTRY_DATA = "intent_extra_country_data"
        fun start(context: Context?, countryData: CountryData) {
            val intent = Intent(context, IdOptionsActivity::class.java)
            intent.putExtra(INTENT_EXTRA_COUNTRY_DATA, countryData)
            context?.startActivity(intent)
        }
    }
}