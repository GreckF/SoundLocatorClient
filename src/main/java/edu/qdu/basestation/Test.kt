package edu.qdu.basestation

import java.net.Socket
import java.util.*

fun main() {
  val sock = Socket("127.0.0.1", 8888)
  println("connected")
  val scanner = Scanner(sock.getInputStream())
  val outputStream = sock.getOutputStream()

  val testTime : Long = 1243328943424
  val testShorts : ShortArray = shortArrayOf(
    7435, 20624, 16748, 9635, 22998, 6429, -17618, -10103, -1656, -27775,
  28867, -14574, -29523, -1576, 6679, -6839, 11318, -15566, 7363, 4063,
  -13082, 28143, -9952, -6318, -9677, 6124, 5021, -4355, 6956, 31186,
  29960, -1874, -2897, 593, -4490, 18527, 14220, -12914, 6368, 17556,
  -743, -27902, 12671, -19212, -32542, 23821, -4842, -20186, 4992,
  -16354, 21717, -28074, -31552, 20231, -7479, 23840, -18377, 2064,
  -10197, 1765, 7960, 12692, 28762, -10975, -31139, 29929, -20164,
  -8152, -1567, 5488, 2460, -29190, -20603, -8308, 3992, -29041, -9563,
  -25074, -26531, -1025, -7270, -23735, -13074, -12748, 16498, -3016,
  16141, 24986, -635, 26669, 16887, 29126, -31931, 12082, -559, 14786,
  31824, 25489, -12808, 10160, 19683, -22343, 1310, 15208, -23153,
  -5845, 838, 31691, 24909, 4952, 4299, -1367, -9393, -31107, -30503,
  10296, 25631, -20850, -8302, 29181, 27606, -15663, 11414, -24226,
  -25467, -19379, 11771, 11457, -21164, 11644, -21498, 27131, -25620,
  32381, -30058, -2664, -4714, -23540, 21398, -14208, -14961, 6382,
  30710, -28037, -32761, 20356, 23612, 21002, -23751, -14809, -17089,
  -25776, -21788, -28373, -8708, -26351, 11943, 30583, 4252, -24947,
  30309, -27668, -487, -14467, 5878, 26041, 1651, 20318, 18267, -24484,
  -32184, -16697, -2002, -14795, 4271, 20151, 5982, -13195, -30672,
  16768, 5121, 7779, -18380, -27995, 4557, 24779, 13945, 19559, -21255,
  -22827, 2259, -18235, -17526, 1065, -28504, 17169, -23630, 25343,
  32675, -32768, 32767
  )
  println(testShorts.size)
  outputStream.write(putZeros(testTime.toString()).toByteArray())
  outputStream.write(toByteArray(testShorts, testShorts.size))
  outputStream.flush()

  outputStream.write(putZeros(testTime.toString()).toByteArray())
  outputStream.write(toByteArray(testShorts, testShorts.size))
  outputStream.flush()

  outputStream.write(putZeros(testTime.toString()).toByteArray())
  outputStream.write(toByteArray(testShorts, testShorts.size))
  outputStream.flush()

  outputStream.write(putZeros(testTime.toString()).toByteArray())
  outputStream.write(toByteArray(testShorts, testShorts.size))
  outputStream.flush()

  println(scanner.nextLine())

}







fun putZeros(str: String): String {
  val l = str.length
  fun replicate(n : Int): String {
    var s : String = ""
    for (i in 1 .. n) {
      s += "0"
    }
    return s
  }
  return replicate(64 - l) + str
}



































