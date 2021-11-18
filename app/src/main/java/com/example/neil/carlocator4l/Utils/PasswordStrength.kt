package com.example.neil.carlocator4l.Utils

class PasswordStrength {

    //Button Press
    // Finds Password Strength
    fun passwordStrength(inp: CharSequence): String? {
        val len = inp.length
        var l: Short = 1
        var u: Short = 1
        var n: Short = 1
        var s: Short = 1
        for (i in 0 until len) {
            if (n.toInt() != 1 && s.toInt() != 1 && l.toInt() != 1 && u.toInt() != 1) {
                break
            }
            if (Character.isDigit(inp[i])) // a digit
            {
                n = 4
            } else if (Character.isUpperCase(inp[i])) // a upper case
            {
                u = 3
            } else if (Character.isLowerCase(inp[i])) // a lower case
            {
                l = 2
            } else  // Special Char
            {
                s = 5
            }
        }
        val x = l * s * u * n
        //Log.d("lsun = ",Integer.toString(x));
        var ch = "a"
        if (x >= 2 && x <= 7) {
            ch = "a"
        } else if (x >= 8 && x <= 22) {
            ch = "b"
        } else if (x >= 23 && x <= 120) {
            ch = "c"
        }
        if (len <= 4) ch = "0$ch" else if (len > 4 && len <= 8) ch =
            "1$ch" else if (len > 8 && len <= 12) ch =
            "2$ch" else if (len > 12) ch = "3$ch"
        if (ch == "0a") return "weak" else if (ch == "0b" || ch == "1a") return "medium_weak" else if (ch == "0c" || ch == "1b" || ch == "2a") return "medium" else if (ch == "1c" || ch == "2b" || ch == "3a") return "medium_strong" else if (ch == "2c" || ch == "3b" || ch == "3c") return "strong"
        return "weak"
    }
}