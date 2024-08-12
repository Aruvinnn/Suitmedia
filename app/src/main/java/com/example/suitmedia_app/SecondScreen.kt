package com.example.suitmedia_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class SecondScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val chooseUserButton = findViewById<Button>(R.id.chooseUserButton)

        val name = intent.getStringExtra("name")
        nameTextView.text = "$name"

        chooseUserButton.setOnClickListener {
            val intent = Intent(this, ThirdScreen::class.java)
            startActivityForResult(intent, REQUEST_CODE_CHOOSE_USER)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE_USER && resultCode == Activity.RESULT_OK) {
            val selectedUserName = data?.getStringExtra("selectedUserName")
            findViewById<TextView>(R.id.selectedUserNameTextView).text = selectedUserName
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish() // Navigate back
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val REQUEST_CODE_CHOOSE_USER = 1
    }
}