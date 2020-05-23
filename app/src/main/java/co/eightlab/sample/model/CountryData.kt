package co.eightlab.sample.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CountryData(
        var name: String?,
        var code: String?,
        var cards: List<Card>
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            ArrayList<Card>().apply { source.readList(this, Card::class.java.classLoader) }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(code)
        writeList(cards)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CountryData> = object : Parcelable.Creator<CountryData> {
            override fun createFromParcel(source: Parcel): CountryData = CountryData(source)
            override fun newArray(size: Int): Array<CountryData?> = arrayOfNulls(size)
        }
    }
}

data class Card(
        @SerializedName("id_name")
        var idName: String?,
        var id: Int?
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(idName)
        writeValue(id)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Card> = object : Parcelable.Creator<Card> {
            override fun createFromParcel(source: Parcel): Card = Card(source)
            override fun newArray(size: Int): Array<Card?> = arrayOfNulls(size)
        }
    }
}