package com.example.quizapp.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null // Объявляем переменную для привязки
    private val binding get() = _binding!! // Используем безопасный доступ к привязке

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false) // Создаем экземпляр привязки
        return binding.root // Возвращаем корневое представление
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState) // Вызываем родительский метод

        // Установка приветственного текста
        binding.welcomeText.text = binding.welcomeText.text.toString()

        // Получение ссылки на кнопку и установка слушателя
        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_quizFragment)
        }

        // Получение ссылки на кнопку "Закрыть" и установка слушателя
        binding.closeButton.setOnClickListener {
            // Закрытие приложения
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Освобождаем привязку, чтобы избежать утечек памяти
    }
}


//package com.example.quizapp.ui.welcome
//
//import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.example.quizapp.R
//
//class WelcomeFragment : Fragment(R.layout.fragment_welcome) {
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        // Установка приветственного текста
//        val welcomeText: TextView = view.findViewById(R.id.welcome_text)
//        welcomeText.text = welcomeText.text.toString()
//
//        // Получение ссылки на кнопку и установка слушателя
//        val startButton: Button = view.findViewById(R.id.start_button)
//        startButton.setOnClickListener {
//            findNavController().navigate(R.id.action_welcomeFragment_to_quizFragment)
//        }
//        // Получение ссылки на кнопку "Закрыть" и установка слушателя
//        val closeButton: Button = view.findViewById(R.id.close_button)
//        closeButton.setOnClickListener {
//            // Закрытие приложения
//            requireActivity().finish()
//        }
//    }
//}
