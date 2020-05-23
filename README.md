# VisionDemo
This is a EightVision OCR sample application. 

Integration Steps
=================

1. Add it in your root build.gradle at the end of repositories
  ```gradle
    allprojects {
        repositories {
            maven { url "https://dl.bintray.com/eightlab/maven" }
        }
    }
```
2. Add the dependency
```gradle
   dependencies {
         implementation 'co.eightlab:eightvision:1.0.1'
   }
```

3. Start OCR by
```kotlin
 EightVisionSdk.Builder()
                .apiKey("license_key")
                .cardType(CardType.PASSPORT)
                .dateFormat("MM/dd/yyyy")
                .scanTime(30)
                .build()
                .start(this)
```
4. Receive results in `onActivityResult`
```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == EightVisionSdk.REQUEST_EIGHT_VISION_ID_OCR) {
            if (data != null && data.hasExtra(Constants.EXTRA_SCAN_RESULT)) {
                //Received OCR result
                val result: ScanResult = data.getParcelableExtra(Constants.EXTRA_SCAN_RESULT)
                 val passportScanResult: PassportScanResult = result as PassportScanResult
            } else { //Result failed
                toast(getString(R.string.scan_failed_try_again))
            }
        }
    }
```

  Parameters
  ----------
  * `apiKey` - Get valid licnese key from support team
  * `cardType` - 
          1. CardType.PASSPORT
          2. CardType.MYKAD
          3. CardType.IKAD
          4. CardType.MYTENTERA
  * `dateFormat` - Pass custom dateformat. For example: MM/dd/yyyy
  * `scanTime` - scan timeout in seconds
  
  ScanResult
  ----------
  Available `ScanResult`
  1. For Passport, use `PassportScanResult`
  2. For MyKad, use `MyKadScanResult`
  3. For iKad, use `IKadScanResult`
  4. For iKad, use `IKadScanResult
