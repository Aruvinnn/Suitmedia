package com.example.suitmedia_app


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


class ThirdScreen : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var progressBar: ProgressBar
    private var currentPage = 1
    private var isLoading = false
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        recyclerView = findViewById(R.id.userRecyclerView)
        progressBar = findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter { selectedUser ->
            val resultIntent = Intent()
            resultIntent.putExtra("selectedUserName", selectedUser.firstName)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        recyclerView.adapter = userAdapter

        fetchData()
    }

    private fun fetchData() {
        if (isLoading) return

        isLoading = true
        progressBar.visibility = ProgressBar.VISIBLE

        scope.launch {
            try {
                val users = withContext(Dispatchers.IO) { fetchUsers(currentPage) }
                userAdapter.addUsers(users)
                currentPage++
            } catch (e: Exception) {
                Log.e("ThirdActivity", "Error fetching users: ${e.message}")
            } finally {
                isLoading = false
                progressBar.visibility = ProgressBar.GONE
            }
        }
    }

    private suspend fun fetchUsers(page: Int): List<User> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(UserService::class.java)
        val response = service.getUsers(page)
        return response.data ?: emptyList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}

interface UserService {
    @GET("api/users")
    suspend fun getUsers(@Query("page") page: Int): UserResponse
}