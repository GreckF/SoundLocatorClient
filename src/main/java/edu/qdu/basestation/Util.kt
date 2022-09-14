package edu.qdu.basestation

import android.util.Log
import java.util.*

const val MINE = "Mine"
const val IMPORTANT = "!IMP!"
const val SOUND_VALUE = "SV"
const val SAMPLE_RATE_IN_HZ = 8000

fun put(str: String) {
  Log.i(MINE, str)
}

fun importantPut(str: String) {
  Log.i(IMPORTANT, str)
}

fun soundValue(str: String) {
  Log.i(SOUND_VALUE, str)
}

// long
const val TIME_SIZE = 8
const val SAMPLE_SIZE = 4

var _T0: Long = 0

fun getT0() = _T0

fun setT0() {
  _T0 = Date().time
}

fun now() = Date().time - _T0

fun toByteArray(shorts: ShortArray, bufferSize: Int): ByteArray {
  val buf = ByteArray(2 * bufferSize)
  var i = 0
  for (v in shorts) {
    val bs = ByteArray(2)
    bs.writeInt16BE(v.toInt())
    buf[i] = bs[0]
    buf[i+1] = bs[1]
    i += 2
  }

  return buf
}