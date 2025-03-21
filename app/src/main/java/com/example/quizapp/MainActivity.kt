package com.example.quizapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.ui.quiz.QuizFragment
import com.example.quizapp.ui.result.ResultFragment
import com.example.quizapp.ui.welcome.WelcomeFragment

class MainActivity : AppCompatActivity() {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var themeSwitch: Switch
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView

        // Установка NavController для управления навигацией фрагментов
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_welcome, R.id.navigation_quiz, R.id.navigation_result
            )
        )
        // Настройка ActionBar с NavController
        setupActionBarWithNavController(navController, appBarConfiguration)

        themeSwitch = findViewById(R.id.theme_switch)
        // Инициализация SharedPreferences
        val sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE)
        val isNightMode = sharedPreferences.getBoolean("night_mode", false)

        // Установка текущей темы
        AppCompatDelegate.setDefaultNightMode(
            if (isNightMode) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        themeSwitch.isChecked = isNightMode

        // Установка слушателя на переключатель темы
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )

            // Сохранение выбор темы
            sharedPreferences.edit().putBoolean("night_mode", isChecked).apply()
        }

        // Установка слушателя на элементы навигации
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_welcome -> {
                    loadFragment(WelcomeFragment()) // Замените на ваш фрагмент
                    true
                }
                R.id.navigation_quiz -> {
                    loadFragment(QuizFragment()) // Замените на ваш фрагмент
                    true
                }
                R.id.navigation_result -> {
                    loadFragment(ResultFragment()) // Замените на ваш фрагмент
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        Log.d("MainActivity", "Loading fragment: ${fragment.javaClass.simpleName}")
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        // Устанавливаем анимации
        transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit)

        transaction.replace(R.id.nav_host_fragment_activity_main, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}

//class MainActivity : AppCompatActivity() {
//
//    @SuppressLint("UseSwitchCompatOrMaterialCode")
//    private lateinit var themeSwitch: Switch
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        val navView: BottomNavigationView = binding.navView
//
//// Установка NavController для управления навигацией фрагментов
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//
//
//        findViewById<BottomNavigationView>(R.id.nav_view).setupWithNavController(navController)
//
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_welcome, R.id.navigation_quiz, R.id.navigation_result
//            )
//        )
//        // Настройка ActionBar с NavController
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//
//        themeSwitch = findViewById(R.id.theme_switch)
//        // Инициализация SharedPreferences
//        val sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE)
//        val isNightMode = sharedPreferences.getBoolean("night_mode", false)
//
//        // Установка текущей темы
//        AppCompatDelegate.setDefaultNightMode(
//            if (isNightMode) {
//                AppCompatDelegate.MODE_NIGHT_YES
//            } else {
//                AppCompatDelegate.MODE_NIGHT_NO
//            }
//        )
//        themeSwitch = findViewById(R.id.theme_switch)
//        themeSwitch.isChecked = isNightMode
//
//        // Установка слушателя на переключатель темы
//        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
//            AppCompatDelegate.setDefaultNightMode(
//                if (isChecked) {
//                    AppCompatDelegate.MODE_NIGHT_YES
//                } else {
//                    AppCompatDelegate.MODE_NIGHT_NO
//                }
//            )
//
//            // Сохранение выбор темы
//            sharedPreferences.edit().putBoolean("night_mode", isChecked).apply()
//        }
//         fun loadFragment(fragment: Fragment) {
//            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//
//            // Устанавливаем анимации
//            transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit)
//
//            transaction.replace(R.id.nav_host_fragment_activity_main, fragment)
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }
//    }
//}
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//    private lateinit var themeSwitch: Switch
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        themeSwitch = findViewById(R.id.theme_switch)
//        // Инициализация SharedPreferences
//        val sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE)
//        val isNightMode = sharedPreferences.getBoolean("night_mode", false)
//
//        // Установка текущей темы
//        AppCompatDelegate.setDefaultNightMode(
//            if (isNightMode) {
//                AppCompatDelegate.MODE_NIGHT_YES
//            } else {
//                AppCompatDelegate.MODE_NIGHT_NO
//            }
//        )
//        themeSwitch = findViewById(R.id.theme_switch)
//        themeSwitch.isChecked = isNightMode
//
//        // Установка слушателя на переключатель темы
//        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
//            AppCompatDelegate.setDefaultNightMode(
//                if (isChecked) {
//                    AppCompatDelegate.MODE_NIGHT_YES
//                } else {
//                    AppCompatDelegate.MODE_NIGHT_NO
//                }
//            )
//
//            // Сохранение выбор темы
//            sharedPreferences.edit().putBoolean("night_mode", isChecked).apply()
//        }
//
//
//        val navView: BottomNavigationView = binding.navView
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_welcome,
//                R.id.navigation_quiz,
//                R.id.navigation_result
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//    }
//}

//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_welcome,
//                R.id.navigation_quiz,
//                R.id.navigation_result
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//
//    }
//}

//<argument
//android:name="result"
//app:argType="integer" />
