package co.eightlab.sample.view.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.eightlab.eightvision.Constants
import co.eightlab.eightvision.scanner.*
import co.eightlab.model.CardType
import co.eightlab.sample.R
import co.eightlab.sample.model.ResultData
import co.eightlab.sample.view.intro.IntroActivity
import co.eightlab.sample.view.result.adapter.ResultListAdapter
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.toolbar_main.*


/**
 * Created by surensth on 5/22/20.
 */

class ResultActivity : AppCompatActivity() {
    private var cardImageUri: Uri? = null

    private var result: ScanResult? = null
    private var cardType: CardType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setupToolbar()
        result = intent.getParcelableExtra(Constants.EXTRA_SCAN_RESULT)
        cardType = result?.cardType
        setupRecyclerView()
        ocrResultNextButton?.setOnClickListener { openIntroActivity() }
    }

    /**
     * Clear all existing activities and open intro activity
     */
    private fun openIntroActivity() {
        val intent = Intent(this@ResultActivity, IntroActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    /**
     * toolbar set up and click listener
     */
    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        titleTextView.text = getString(R.string.details)
        backImageView.setOnClickListener { onBackPressed() }
    }

    /**
     * prepare result data to show in view
     */
    private fun prepareResult(): List<ResultData> {
        val results: MutableList<ResultData> = ArrayList<ResultData>()
        if (result is MyKadScanResult) { //MyKad Result
            val myKadScanResult: MyKadScanResult = result as MyKadScanResult
            val nricNumber: String = myKadScanResult.nricNumber
            val fullName: String = myKadScanResult.name
            val ownerAddress: String = myKadScanResult.address
            val addressObj: Address = myKadScanResult.getAddressObject()
            val birthDate: String = myKadScanResult.dateOfBirth
            val gender: String = myKadScanResult.gender
            cardImageUri = myKadScanResult.imageUri
            results.add(ResultData(getString(R.string.full_name), fullName))
            results.add(ResultData(getString(R.string.nationality), "Malaysia"))
            results.add(ResultData(getString(R.string.nric_number), nricNumber))
            results.add(ResultData(getString(R.string.gender), gender))
            results.add(ResultData(getString(R.string.date_of_birth), birthDate))
            if (addressObj != null) {
                results.add(ResultData(getString(R.string.postcode), addressObj.getPostCode()))
                results.add(ResultData(getString(R.string.state), addressObj.getState()))
                results.add(ResultData(getString(R.string.city), addressObj.getCity()))
            }
            results.add(ResultData(getString(R.string.address), ownerAddress))
        } else if (result is MyTenteraScanResult) {
            val myKadScanResult: MyTenteraScanResult = result as MyTenteraScanResult
            val nricNumber: String = myKadScanResult.nricNumber
            val armyNumber: String = myKadScanResult.milNumber
            val fullName: String = myKadScanResult.name
            val ownerAddress: String = myKadScanResult.address
            val addressObj: Address? = myKadScanResult.getAddressObject()
            val birthDate: String = myKadScanResult.dateOfBirth
            val gender: String = myKadScanResult.gender
            cardImageUri = myKadScanResult.imageUri
            results.add(ResultData(getString(R.string.full_name), fullName))
            results.add(ResultData(getString(R.string.nationality), "Malaysia"))
            results.add(ResultData(getString(R.string.nric_number), nricNumber))
            results.add(ResultData(getString(R.string.army_number), armyNumber))
            results.add(ResultData(getString(R.string.gender), gender))
            results.add(ResultData(getString(R.string.date_of_birth), birthDate))
            if (addressObj != null) {
                results.add(ResultData(getString(R.string.postcode), addressObj.getPostCode()))
                results.add(ResultData(getString(R.string.state), addressObj.getState()))
                results.add(ResultData(getString(R.string.city), addressObj.getCity()))
            }
            results.add(ResultData(getString(R.string.address), ownerAddress))
        } else if (result is PassportScanResult) { //Passport Result
            val passportScanResult: PassportScanResult = result as PassportScanResult
            val nationality: String = passportScanResult.nationality
            val personalNumber: String = passportScanResult.personalNumber
            val lastName: String = passportScanResult.lastName
            val dob: String = passportScanResult.dateOfBirth
            val expiryDate: String = passportScanResult.expiryDate
            val issueCountry: String = passportScanResult.issuingCountry
            val gender: String = passportScanResult.gender
            val passportNumber: String = passportScanResult.passportNumber
            val firstName: String = passportScanResult.firstNames
            val fullName = "$firstName $lastName"
            cardImageUri = passportScanResult.imageUri

            results.add(ResultData(getString(R.string.full_name), fullName))
            results.add(ResultData(getString(R.string.passport_number), passportNumber))
            results.add(ResultData(getString(R.string.nationality), nationality))
            results.add(ResultData(getString(R.string.date_of_birth), dob))
            results.add(ResultData(getString(R.string.gender), gender))
            results.add(ResultData(getString(R.string.expiry_date), expiryDate))
            results.add(ResultData(getString(R.string.issue_country), issueCountry))
            if (!TextUtils.isEmpty(personalNumber)) results.add(ResultData(getString(R.string.personal_number), personalNumber))
        } else if (result is IKadScanResult) { //Ikad Result
            val myKadScanResult: IKadScanResult = result as IKadScanResult
            val passportNumber: String = myKadScanResult.passportNumber
            val fullName: String = myKadScanResult.name
            val nationality: String = myKadScanResult.country
            val ownerAddress: String = myKadScanResult.address
            val birthDate: String = myKadScanResult.dateOfBirth
            val gender: String = myKadScanResult.gender
            val sector: String = myKadScanResult.sector
            val employer: String = myKadScanResult.employer
            val expiryDate: String = myKadScanResult.expiryDate
            cardImageUri = myKadScanResult.imageUri
            results.add(ResultData(getString(R.string.full_name), fullName))
            results.add(ResultData(getString(R.string.full_name), passportNumber))
            results.add(ResultData(getString(R.string.nationality), nationality))
            results.add(ResultData(getString(R.string.gender), gender))
            results.add(ResultData(getString(R.string.expiry_date), birthDate))
            results.add(ResultData(getString(R.string.sector), sector))
            results.add(ResultData(getString(R.string.employer), employer))
            results.add(ResultData(getString(R.string.expiry_date), expiryDate))
            results.add(ResultData(getString(R.string.address), ownerAddress))
        }
        if (cardImageUri != null) cardImageView!!.setImageURI(cardImageUri)
        return results
    }

    /**
     * set up recycler view
     */
    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        resultRecyclerView.layoutManager = layoutManager
        resultRecyclerView.setHasFixedSize(true)
        resultRecyclerView.adapter = ResultListAdapter(prepareResult())
    }

    companion object {
        private val TAG: String = ResultActivity::class.java.simpleName
    }
}