package edu.qdu.basestation

import android.Manifest
import android.content.pm.PackageManager
import android.content.pm.PathPermission
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.os.Message.obtain
import android.util.Log
import androidx.core.app.ActivityCompat
import java.util.*


class SoundRecorder
  ( val handler : Handler
  , val audioRecord : AudioRecord
  , val bufferSize : Int
  , val netThread: NetThread
  ) : Thread() {

  private var isRun = true

  override fun run() {
    while (! netThread.isRun());
    setT0()
    put("Running")
    super.run()
    audioRecord.startRecording()
    val buffer = ShortArray(bufferSize)

    importantPut("bufferSize = $bufferSize; shortArraySize = ${buffer.size}")

    var t = 0L
    while (isRun) {

      val r = audioRecord.read(buffer, 0, bufferSize)

      val nowt = now()
      netThread.send(nowt, buffer)

      importantPut("dT = ${nowt-t}")
      t = nowt

      put("r = $r")
      val avg: Int = (buffer.sumOf { it.toDouble() * it.toDouble() } / r) .toInt()
      val msg = obtain()
      val budle = Bundle()
      budle.putString("msg", avg.toString())
      msg.data = budle
      handler.sendMessage(msg)
      soundValue(avg.toString())

    }

    audioRecord.stop()
  }

  fun over() {
    isRun = false
    put("over")
  }
}


