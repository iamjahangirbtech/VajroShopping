package com.vajro.shoppingcart.repository.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class Products(
    @SerializedName("name"        ) var name        : String?           = null,
    @SerializedName("id"          ) var id          : String?           = null,
    @SerializedName("product_id"  ) var productId   : String?           = null,
    @SerializedName("sku"         ) var sku         : String?           = null,
    @SerializedName("image"       ) var image       : String?           = null,
    @SerializedName("thumb"       ) var thumb       : String?           = null,
    @SerializedName("zoom_thumb"  ) var zoomThumb   : String?           = null,
    @SerializedName("options"     ) var options     : ArrayList<String> = arrayListOf(),
    @SerializedName("description" ) var description : String?           = null,
    @SerializedName("href"        ) var href        : String?           = null,
    @SerializedName("quantity"    ) var quantity    : Int?              = null,
    @SerializedName("images"      ) var images      : ArrayList<String> = arrayListOf(),
    @SerializedName("price"       ) var price       : String?           = null,
    @SerializedName("special"     ) var special     : String?           = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("options"),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        TODO("images"),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(id)
        parcel.writeString(productId)
        parcel.writeString(sku)
        parcel.writeString(image)
        parcel.writeString(thumb)
        parcel.writeString(zoomThumb)
        parcel.writeString(description)
        parcel.writeString(href)
        parcel.writeValue(quantity)
        parcel.writeString(price)
        parcel.writeString(special)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Products> {
        override fun createFromParcel(parcel: Parcel): Products {
            return Products(parcel)
        }

        override fun newArray(size: Int): Array<Products?> {
            return arrayOfNulls(size)
        }
    }
}