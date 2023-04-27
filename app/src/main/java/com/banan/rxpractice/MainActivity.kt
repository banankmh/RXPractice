package com.banan.rxpractice

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.banan.rxpractice.databinding.ActivityMainBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var counter = 0
    private val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.clickingButton.setOnClickListener {
            incrementCounter()
            setCounter()
        }

        Observable.create{ emitter ->
            binding.navigationButton.setOnClickListener {
                emitter.onNext(0)
            }
        }
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribe {
               navigateToSecondActivity()
             // Log.e(Tag,"navigate")
            }.add(compositeDisposable)
    }


    private fun incrementCounter() {
        counter++
    }
    private fun setCounter(){
        Observable.just(counter)
            .subscribe(
                { value ->
                    binding.counter.text = value.toString()
                },
                { error ->
                    Log.e(Tag, "Error: $error")
                }
            ).add(compositeDisposable)
    }

    private fun navigateToSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }
    companion object {
        var observable = Observable.create<Int> { }
        var Tag="banan"
        fun Disposable.add(compositeDisposable: CompositeDisposable) {
            compositeDisposable.add(this)
        }
    }


}