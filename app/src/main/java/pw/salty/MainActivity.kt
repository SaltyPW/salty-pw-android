package pw.salty

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import pw.salty.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val salty : Salty = Salty()
    private var password : String = ""
    private var reveal : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.inputBasePasswordEditText.doAfterTextChanged { handleTextChanged() }
        binding.inputSaltEditText.doAfterTextChanged{ handleTextChanged() }
        binding.finalPassword.setOnTouchListener { _, _ -> revealPassword() }
        binding.copyPasswordButton.setOnTouchListener { _, _ -> copyPassword() }

        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleListener(this))
    }

    private fun handleTextChanged(): Boolean{
        password = salty.hash(binding.inputBasePasswordEditText.text.toString(), binding.inputSaltEditText.text.toString())
        displayPassword()
        return false
    }

    private fun displayPassword() {
        if (reveal)
            binding.finalPassword.setText(password)
        else {
            binding.finalPassword.setText("tap to reveal")
        }
        binding.copyPasswordButton.isEnabled = true
    }

    private fun revealPassword() : Boolean {
        reveal = true
        displayPassword()
        return false
    }

    private fun copyPassword() : Boolean{
        val clipboard:ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Password", password)
        clipboard.setPrimaryClip(clip)
        return false
    }

    fun reset() {
        password = ""
        reveal = false
        binding.inputBasePasswordEditText.setText("")
        binding.inputSaltEditText.setText("")
        displayPassword()
        binding.copyPasswordButton.isEnabled = false
    }
}

class AppLifecycleListener (val mainActivity: MainActivity) : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        //mainActivity.binding.inputBasePasswordEditText.requestFocus()
    }

    override fun onStop(owner: LifecycleOwner) {
        mainActivity.reset()
    }
}