package ru.xmagi.mymaner.ui.login

import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import ru.xmagi.mymaner.databinding.ActivityLoginBinding
import java.io.File
import java.io.FileOutputStream
import java.lang.Thread.sleep


class LoginActivity : AppCompatActivity() {

    // private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    var curProc : Process? = null ; //maybe lateinit or lazy()
    var lastMsg = ""
   // var curStream: InputStream? = null
   fun getStreamData(proc: Process): String {
       Log.d("getProcDataStream", "return data array")
       // System.out.flush()
       // val input = proc.getInputStream()
       val input = File("$fullPath/out.txt").inputStream()
       val inputAsString = input.bufferedReader().use { it.readText() }
       if (inputAsString.isNotEmpty()) {
           lastMsg = inputAsString
       }
       File("$fullPath/out.txt").writeText("")
       return lastMsg + inputAsString // substring?
       // return curProc!!.getInputStream().readBytes()
   }
    fun getStreamData(): String {
        return getStreamData(curProc!!)
    }
    // var mBufRead: BufferedReader? = null
    private lateinit var fullPath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide() // hide action bar

        initAsset()
        fullPath = ContextWrapper(this).getFilesDir().getPath().toString() // for program minerd ; todo fix weird name
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = binding.worker
        username.setText("violetday.android_1")
        val password = binding.password
        password.setText("x")
        val runMiner = binding.startButton // runMiner
        val textView = binding.textView

        val timer = object: CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (curProc != null) {
                    try {

                        val bytes = getStreamData()
                        val inputAsString = bytes.toString()
                        textView!!.text =
                            if (inputAsString.isNotBlank() && inputAsString.isNotEmpty()) inputAsString else "not logs"
                        Log.d("logStats", "updated $inputAsString")
                    } catch (e: Exception) {
                        Log.d("onTickTimerE", e.toString())
                        textView!!.text = e.toString()
                    }
                }
            }

            override fun onFinish() {

            }
        }
        runMiner!!.isClickable = true;
        runMiner!!.setOnClickListener {
                // loading.visibility = View.VISIBLE
                // bad thing.
                if (curProc == null) {
                    curProc = runMinerProccess(username.text.toString(), password.text.toString())
                    runMiner.text = "stop"
                    username.isVisible = false
                    password.isVisible = false
                    timer.start()
                }
                else {
                    stopMinerProccess()
                    username.isVisible = true
                    password.isVisible = true
                    timer.cancel()
                    textView!!.text = ""
                    runMiner.text = "run"
                }
               //  minerStatus!!.setEnabled(!minerStatus.isEnabled)

            }
        }
    private fun initAsset() {

        val minerd = this.applicationContext.assets.open("minerd")
        val c = ContextWrapper(this);
        val PathFiles = c.getFilesDir().getPath();
        val outFile = File("$PathFiles/minerd")
        if (outFile.exists()) {
            outFile.setExecutable(true)
            outFile.setReadable(true)
            outFile.setWritable(false)
            Runtime.getRuntime().exec("chmod 777 $PathFiles/minerd")
            Log.d("Miner", "assets was before init ${outFile.absoluteFile}")

            return
        }
        var byteArr = minerd.readBytes()
        FileOutputStream(outFile).write(byteArr)
        outFile.setExecutable(true)
        outFile.setReadable(true)
        outFile.setWritable(false)
        Log.d("Miner", "assets init")
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun runMinerProccess(worker: String, pass: String): Process? {
        val threads = Runtime.getRuntime().availableProcessors();
        Log.d("Miner", "runs")
        val myScript = File("$fullPath/script.sh")

        val command = "$fullPath/minerd -a gostd -R 5 -T 300 -o  stratum+tcp://pool.gostco.in:3333 -u ${worker} -p ${pass} -t ${threads}" // deprecated
        myScript.writeText(command)
        myScript.setExecutable(true)
        // /bin/sh
        val builder = ProcessBuilder("/system/bin/sh", "$fullPath/script.sh")
        builder.redirectOutput(File("$fullPath/out.txt"));
        builder.redirectError(File("$fullPath/out.txt"));
        Log.d("runMiner", "exec $command")
        try {
            val proc = builder.start()
            Log.d("procStatus", proc.toString())
            // proc.waitFor()
            // sleep(1000);
            // curStream = proc!!.getInputStream()
            val data = getStreamData(proc)

            Log.d("procInputStream", data)
            return proc
        } catch(e: Exception) {
            Log.d("runMinerError", e.toString())
        }
        return null
    }
    private fun stopMinerProccess() {
        curProc?.destroy()
        curProc = null
        // curStream= null
    }

}
