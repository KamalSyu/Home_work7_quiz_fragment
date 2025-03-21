package com.example.quizapp.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null // Объявляем переменную для привязки
    private val binding get() = _binding!! // Используем безопасный доступ к привязке

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false) // Создаем экземпляр привязки
        return binding.root // Возвращаем корневое представление
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState) // Вызываем родительский метод

        // Получение результата из аргументов с использованием Safe Args
        val result = ResultFragmentArgs.fromBundle(requireArguments()).correctAnswersCount

        // Установка текста результата
        binding.resultText.text = "Ваш результат: $result"

        // Обработчик нажатия на кнопку "Начать заново"
        binding.restartButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)
        }

        binding.backButtonQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_quizFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Освобождаем привязку, чтобы избежать утечек памяти
    }
}




//package com.example.quizapp.ui.result
//
//import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.example.quizapp.R
//
//class ResultFragment : Fragment(R.layout.fragment_result) {
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        // Инициализация Views
//        val resultText: TextView = view.findViewById(R.id.result_text)
//        val restartButton: Button = view.findViewById(R.id.restart_button)
//        val backButtonQuiz: Button = view.findViewById(R.id.back_button_quiz)
//
//        // Получение результата из аргументов
//        requireArguments().getInt("correctAnswersCount", 0) // 0 - значение по умолчанию, если аргумент не найден
//
//        // Получение результата из аргументов с использованием Safe Args
//        val result = ResultFragmentArgs.fromBundle(requireArguments()).correctAnswersCount
//
//        // Установка текста результата
//        resultText.text = "Ваш результат: $result"
//
//        // Обработчик нажатия на кнопку "Начать заново"
//        restartButton.setOnClickListener {
//            // Переход к welcomeFragment (экрану с опросником)
//            findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)
//        }
//        backButtonQuiz.setOnClickListener {
//            findNavController().navigate(R.id.action_resultFragment_to_quizFragment)
//        }
//    }
//}