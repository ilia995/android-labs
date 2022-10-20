package com.example.lab3

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.lab3.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) } // оголошення binding для доступу до елементів макету
    private var isRunning = true // чи відкрита активність
    private var buttonsVisible = false // чи показано список студентів
    private var seconds = 0 // таймер
    private var textSize = 0.0f
    private var students = "" // список студентів
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        runTimer()
        val showListBtn = binding.button
        val studentList = binding.studentList
        val spinner = binding.spinner
        val sendBtn = binding.send
        val increaseTextSizeBtn = binding.increaseTextSize
        val decreaseTextSizeBtn = binding.decreaseTextSize
        val detailsBtn = binding.details
        textSize = studentList.textSize / 2.765f
        var group = spinner.selectedItem.toString()
        println(textSize)
        showListBtn.setOnClickListener{ // показати список студентів з вказаної групи
            setVisibility()
            group = spinner.selectedItem.toString()
            students = Student.getStudents(group)
            studentList.text = students
        }
        detailsBtn.setOnClickListener {
            group = spinner.selectedItem.toString()
            val intent = Intent(this, StudentGroupActivity::class.java)
            intent.putExtra(StudentGroupActivity.GROUP_NUMBER, group)
            startActivity(intent)
        }
        sendBtn.setOnClickListener { // відправлення списку через зовнішній застосунок
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, studentList.text)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Список студентів групи $group")
            startActivity(intent)
        }
        increaseTextSizeBtn.setOnClickListener{ // змінення розміру тектсу
            if (textSize < 50){
                textSize *= 1.1f
                studentList.textSize = textSize
            }
        }
        decreaseTextSizeBtn.setOnClickListener{ // змінення розміру тектсу
            if (textSize > 12){
                textSize *= 0.9f
                studentList.textSize = textSize
            }
        }
        if (savedInstanceState != null){ // відновлення зміних активності (якщо збережені)
            buttonsVisible = savedInstanceState.getBoolean("buttonsVisible")
            if (buttonsVisible) setVisibility()
            val stList = savedInstanceState.getString("studentList")
            val txtSize = savedInstanceState.getFloat("textSize")
            studentList.textSize = txtSize
            studentList.text = stList
            textSize = txtSize
            students = stList.toString()
            seconds = savedInstanceState.getInt("timer")
        }
    }
    private fun runTimer(){ // запуск таймеру
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                if (isRunning){ // таймер працює якщо активність відкрита
                    seconds++
                    val hours = seconds / 3600
                    val minutes = (seconds % 3600) / 60
                    val sec = seconds % 60
                    this@MainActivity.runOnUiThread{
                        binding.timer.text = String.format("%d:%02d:%02d", hours, minutes, sec)
                    }
                }
            }
        }, 1000, 1000)
    }
    override fun onSaveInstanceState(outState: Bundle) { // збереження зміних активності при повороті екрану
        super.onSaveInstanceState(outState)
        outState.putFloat("textSize", textSize)
        outState.putString("studentList", students)
        outState.putInt("timer", seconds)
        outState.putBoolean("buttonsVisible", buttonsVisible)
    }

    override fun onStart() { // відновлення таймеру при відновленні активності
        super.onStart()
        isRunning = true
    }

    override fun onStop() { // зупинення таймеру при виході з активності
        super.onStop()
        isRunning = false
    }
    private fun setVisibility(){ // приховуємо привітання, відображаємо список студентів, кнопки збільшення шрифту та відправлення списку
        buttonsVisible = true // змінюємо це поле на true при першому показі списку та відновляємо видимість в onCreate тільки якщо список видимий
        binding.greeting.visibility = View.GONE
        binding.studentList.visibility = View.VISIBLE
        binding.send.visibility = View.VISIBLE
        binding.increaseTextSize.visibility = View.VISIBLE
        binding.decreaseTextSize.visibility = View.VISIBLE
    }
}