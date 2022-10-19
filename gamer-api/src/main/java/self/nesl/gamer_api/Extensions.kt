package self.nesl.gamer_api

/**
 * returns 49000 if the string is "49k"
 */
fun String.expandInt() =
    if (this.endsWith("k")) {
        this.substring(0, this.length - 1).toInt() * 1000
    } else {
        this.toInt()
    }