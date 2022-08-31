package self.nesl.newshub

import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun String.toBitmap(): Bitmap {
    val imageBytes = android.util.Base64.decode(this, android.util.Base64.DEFAULT)
    return imageBytes.toBitmap()
}

fun ByteArray.toBitmap() =
    BitmapFactory.decodeByteArray(this, 0, this.size)