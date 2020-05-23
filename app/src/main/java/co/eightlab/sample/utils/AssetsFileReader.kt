package co.eightlab.sample.utils

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by surensth on 5/22/20.
 */
object AssetsFileReader {
    fun loadAssetTextAsString(name: String, context: Context?): String? {
        var br: BufferedReader? = null
        try {
            val buf = StringBuilder()
            if (context == null) return ""
            val inputStream = context.assets.open(name)
            br = BufferedReader(InputStreamReader(inputStream))
            var str: String?
            var isFirst = true
            while (br.readLine().also { str = it } != null) {
                if (isFirst) isFirst = false else buf.append('\n')
                buf.append(str)
            }
            return buf.toString()
        } catch (e: IOException) {
            Log.e("Reader", "Error opening asset $name")
        } finally {
            if (br != null) {
                try {
                    br.close()
                } catch (e: IOException) {
                    Log.e("Reader", "Error closing asset $name")
                }
            }
        }
        return null
    }
}