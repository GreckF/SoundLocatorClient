package edu.qdu.basestation

import java.lang.Exception
import java.lang.RuntimeException
import java.net.Socket
import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class NetThread(
  val host : String,
  val mainActivity: MainActivity,
  val bufferSize : Int
) : Thread() {
  var server : Socket? = null
  private var runState : Boolean = false

  private var queue : BlockingQueue<Tuple<Long, ShortArray>> = LinkedBlockingQueue()

  fun send(time : Long, data : ShortArray) {
    queue.put(Tuple(time, data))
  }

  override fun run() {
    server = Socket(host, 8888)
    val scanner = Scanner(server?.getInputStream())
    val outputStream = server?.getOutputStream()
    mainActivity.isConnected = true

    put("CONNECTED")

    outputStream?.write(putZeros(bufferSize.toString()).toByteArray())

    val expectIN = scanner.nextLine()
    if(expectIN[0] == 'I') {
      put("IN")
    } else {
      put("NOT IN, BUT: $expectIN")

      throw RuntimeException()
    }

    val expectSTART = scanner.nextLine()
    if(expectSTART[0] == 'S') {
      put("START")
      runState = true
    } else {
      put("NOT START, BUT: $expectSTART")
      throw RuntimeException()
    }

    while(isRun()) {
      val (t, d) = queue.take()
      outputStream?.write(putZeros(t.toString()).toByteArray())
      outputStream?.write(toByteArray(d, bufferSize))
      outputStream?.flush()
    }

  }

  private fun putZeros(str: String): String {
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

  fun isRun() = runState

  fun over() {
    runState = false
    server?.close()
    put("over")
  }
}