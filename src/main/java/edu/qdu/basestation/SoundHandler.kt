package edu.qdu.basestation

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.EditText

class SoundHandler(val output: EditText) : Handler(Looper.getMainLooper()) {

  override fun handleMessage(msg: Message) {
    super.handleMessage(msg)
    output.append(msg.data.getString("msg") as String + "\n")
  }

}