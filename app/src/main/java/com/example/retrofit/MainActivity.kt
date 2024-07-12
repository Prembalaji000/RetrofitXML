package com.example.retrofit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit.Adapters.RvAdapter
import com.example.retrofit.Models.UsersItem
import com.example.retrofit.databinding.ActivityMainBinding
import com.example.retrofit.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: RvAdapter
    private lateinit var usersList: List<UsersItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usersList = listOf()

        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getAllUsers()
            }catch (e: IOException){
                Toast.makeText(applicationContext,"app error ${e.message}",Toast.LENGTH_LONG).show()
                return@launch
            }catch (e: HttpException){
                Toast.makeText(applicationContext,"http error ${e.message}",Toast.LENGTH_LONG).show()
                return@launch
            }
            if (response.isSuccessful && response.body() != null){
                withContext(Dispatchers.Main){
                    usersList = response.body()!!
                    binding.rvMain.apply {
                        rvAdapter = RvAdapter(usersList)
                        adapter = rvAdapter
                        layoutManager = LinearLayoutManager(this@MainActivity)
                    }
                }
            }
        }
    }
}