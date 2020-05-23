object Versions {
    const val kotlinVersion = "1.3.41"
    const val appCompat = "1.1.0"
    const val gson = "2.8.6"
    const val eightVision = "1.0.1"
}

object Dependencies {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.appCompat}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    const val eightVision = "co.eightlab:eightvision:${Versions.eightVision}"
}