package com.example.suitmedia_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class FirstScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val palindromeInput = findViewById<EditText>(R.id.palindromeInput)
        val checkButton = findViewById<Button>(R.id.checkButton)
        val nextButton = findViewById<Button>(R.id.nextButton)

        checkButton.setOnClickListener {
            val inputText = palindromeInput.text.toString()
            Log.d("InputText", "Text from EditText: '$inputText'")
            val isPalindrome = isPalindrome(inputText)
            val message = if (isPalindrome) "isPalindrome" else "notPalindrome"
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
        }

        nextButton.setOnClickListener {
            val intent = Intent(this, SecondScreen::class.java)
            intent.putExtra("name", nameInput.text.toString())
            startActivity(intent)
        }
    }

    private fun isPalindrome(text: String): Boolean {
        val cleanedText = text.replace("[^a-zA-Z0-9]".toRegex(), "").lowercase()
        Log.d("PalindromeCheck", "Cleaned: '$cleanedText', Reversed: '${cleanedText.reversed()}'")
        return cleanedText == cleanedText.reversed()
    }
}