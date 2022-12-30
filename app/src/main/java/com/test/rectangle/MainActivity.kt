package com.test.rectangle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.test.rectangle.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigate(MainFragment.newInstance(), false)
    }

    fun navigate(fragment: Fragment, addBackStack: Boolean = true) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(
            R.id.container,
            fragment
        )
        if (addBackStack) transaction.addToBackStack(null)
        transaction.commit()
    }
}
