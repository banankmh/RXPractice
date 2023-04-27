package com.banan.rxpractice

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.banan.rxpractice.MainActivity.Companion.add
import com.banan.rxpractice.MainActivity.Companion.observable
import com.banan.rxpractice.databinding.ActivitySecondBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable

class SecondActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondBinding
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observable.subscribe(
            {
                binding.textView.text = it.toString()
            }, {
                Log.e(MainActivity.Tag, "Errrror ")
            }
        ).add(compositeDisposable)
    }

}