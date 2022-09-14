package edu.qdu.basestation

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.net.NetworkRequest
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.lang.Exception
import java.lang.RuntimeException
import java.net.Socket

private const val REQUEST_RECORD_AUDIO_PERMISSION = 200


class MainActivity : AppCompatActivity() {

  private var outputer : EditText? = null

  private var stopButton : Button? = null
  private var connect : Button? = null
  private var soundRecorder : SoundRecorder? = null
  private var ipAddr : EditText? = null
  var isConnected : Boolean = false
  private var netThread : NetThread? = null

  // Requesting permission to RECORD_AUDIO
  private var permissionToRecordAccepted = false
  private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
      grantResults[0] == PackageManager.PERMISSION_GRANTED
    } else {
      false
    }
    if (!permissionToRecordAccepted) finish()
  }
  //

    val bufferSize : Int =
    AudioRecord.getMinBufferSize( SAMPLE_RATE_IN_HZ
                                , AudioFormat.CHANNEL_CONFIGURATION_MONO
                                , AudioFormat.ENCODING_PCM_16BIT
    )

  private var audioRecord : AudioRecord? = null

  override fun onCreate(savedInstanceState: Bundle?) {

    put("onCreate")

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    outputer    = findViewById(R.id.outputer)
    stopButton  = findViewById(R.id.stop_button)
    connect     = findViewById(R.id.connect)
    ipAddr      = findViewById(R.id.ip_addr)

    outputer?.isFocusable = false
    outputer?.isFocusableInTouchMode = false


    stopButton?.setOnClickListener { view ->
      outputer?.append("stop!\n")
      soundRecorder?.over()
      netThread?.over()
    }

    connect?.setOnClickListener { view ->

      if (! isConnected) {
        outputer?.append("connecting...... \n")

          importantPut(ipAddr?.text.toString())
          // val sock = Socket("192.168.43.32", 8888)

          val _thread = NetThread(ipAddr?.text.toString(), this, bufferSize)
          _thread.start()
          netThread = _thread
          val handler = SoundHandler(outputer?:throw RuntimeException("Can't get edit text"))
          soundRecorder =
            SoundRecorder(
              handler,
              audioRecord?:throw RuntimeException("Can't get recorder"),
              bufferSize,
              _thread
            )
          put("Record Thread Ready")
          soundRecorder?.start()
          put("Record Thread Start")

        outputer?.append("connected")


      } else {
        outputer?.append("already connected \n")
      }

    }

    put("Setup Done")

    ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
    // permission check
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        != PackageManager.PERMISSION_GRANTED) {

      put("Finish Here")
      throw RuntimeException("No Permission")
    }
    // get audio recorder
    audioRecord = AudioRecord(
      MediaRecorder.AudioSource.MIC, SAMPLE_RATE_IN_HZ,
      AudioFormat.CHANNEL_CONFIGURATION_MONO,
      AudioFormat.ENCODING_PCM_16BIT, bufferSize
    )

    put("Permission Checked")

    outputer?.append("$bufferSize\n")

  }


}
