package ru.xmagi.mymaner.ui.login

import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import ru.xmagi.mymaner.databinding.ActivityLoginBinding
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random


class LoginActivity : AppCompatActivity() {
    fun averageKHCount(toRegex: String): String {
        val myRegex = Regex("\\[\\w+\\-\\w+\\-\\d+ \\w+:\\w+\\:\\w+\\] thread \\d\\: \\d+")
        // val acepptedCount = Regex("accepted: \\d+\\/\\d+ \\(.+\\), \\d+.\\d+").findAll(toRegex).map { it.groupValues[0] }.toList().size + 1 // todo:fix logic
        val acepptedCount = Regex("\\[\\w+\\-\\w+\\-\\d+ \\w+:\\w+\\:\\w+\\] thread \\d\\: \\d+").findAll(toRegex).map { it.groupValues[0] }.toList().size + 1
        val _p = myRegex.findAll(toRegex).map { it.groupValues[0] }.toList()
        // Log.d("toRegex", toRegex)
        var fullHash: Double = 0.0
        for(w in _p) {
            // Log.d("kHCOunter", w.split(" ")[4])
            val tmp = w.split(" ")[4].toLong()
            fullHash += tmp
        }
        val average = Math.floor(((fullHash / acepptedCount)/1000))
        return "AVERAGE(not fact) hashrate now is(average of ALL TIME): $average kh/s"
    }

    private lateinit var binding: ActivityLoginBinding
    private var curProc : Process? = null ; //maybe lateinit or lazy()
    private var lastMsg = ""
   fun getStreamData(proc: Process): String {
       Log.d("getProcDataStream", "return data array")
       val myFile = File("$fullPath/out.txt")
       val input = myFile.inputStream()
       val inputAsString = input.bufferedReader().use { it.readText() }

       val file_size = (myFile.length() / 1024 / 1024).toString().toInt()
       if (file_size > 100) {
           Log.d("fileSize", "clear file")
           lastMsg = averageKHCount(inputAsString)
           myFile.writeText("")
       } else lastMsg = "" // lastmsg weird here and deprecated. broken logic
       if (lastMsg.isNotEmpty()) {
           return averageKHCount(lastMsg)
       }
       Log.d("minerOutPut", inputAsString)
       return averageKHCount(inputAsString) // substring?
   }
    fun getStreamData(): String {
        return getStreamData(curProc!!)
    }
    // var mBufRead: BufferedReader? = null
    private lateinit var fullPath: String
    private lateinit var arch: String
    private lateinit var minerdName: String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        arch = System.getProperty("os.arch")
        minerdName = when (arch) {
            "aarch64" -> "minerd64"
            else -> "minerd"
        }
        Log.d("minerArch", arch)

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
        val serverHost = binding.ServerHost
        if (Random.nextInt(0,3) > 0) {
            serverHost!!.setText( "stratum+tcp://pool.gostco.in:3333")
        } else {
            serverHost!!.setText("http://192.168.196.126:3334")
        }

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
                start();
            }
        }
        runMiner!!.isClickable = true;
        runMiner!!.setOnClickListener {
                // loading.visibility = View.VISIBLE
                // bad thing.
                if (curProc == null) {
                    curProc = runMinerProccess(username.text.toString(), password.text.toString(), serverHost.text.toString())
                    runMiner.text = "stop"
                    username.isVisible = false
                    password.isVisible = false
                    serverHost.isVisible = false
                    timer.start()
                }
                else {
                    stopMinerProccess()
                    username.isVisible = true
                    password.isVisible = true
                    serverHost.isVisible = true
                    timer.cancel()
                    textView!!.text = ""
                    runMiner.text = "run"
                }
               //  minerStatus!!.setEnabled(!minerStatus.isEnabled)

            }
        }
    private fun initAsset() {

        val minerd = this.applicationContext.assets.open(minerdName)
        val c = ContextWrapper(this);
        val PathFiles = c.getFilesDir().getPath();
        val outFile = File("$PathFiles/$minerdName")
        if (outFile.exists()) {
            outFile.setExecutable(true)
            outFile.setReadable(true)
            outFile.setWritable(false)
            Runtime.getRuntime().exec("chmod 777 $PathFiles/$minerdName")
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
    private fun runMinerProccess(worker: String, pass: String, serverHost: String): Process? {
        val threads = Runtime.getRuntime().availableProcessors();
        Log.d("Miner", "runs")
        val myScript = File("$fullPath/script.sh")
        if (serverHost.contains("pool.gostco.in")) {
            Toast.makeText(getApplicationContext(), "CYA BECAUSE NOT ALLOW TO OUTPUT LESS THAN 1GST", 100).show()
            Toast.makeText(getApplicationContext(), "CYA BECAUSE NOT ALLOW TO OUTPUT LESS THAN 1GST", 100).show()
        }
        val command = "$fullPath/$minerdName -a gostd -R 5 -T 300 -o \"${serverHost}\" -u ${worker} -p ${pass} -t ${threads}" // deprecated
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
