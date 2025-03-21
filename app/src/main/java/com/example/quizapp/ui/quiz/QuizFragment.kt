package com.example.quizapp.ui.quiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null // Объявляем переменную для привязки
    private val binding get() = _binding!! // Используем безопасный доступ к привязке

    private val questions = listOf(
        Pair("Столица Франции?", "Париж"),
        Pair("Столица Германии?", "Берлин"),
        Pair("Столица Японии?", "Токио")
    )

    private var currentQuestionIndex = 0
    private var correctAnswersCount = 0
    private lateinit var fadeInAnimation: Animation // Объявляем переменную для анимации

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentQuizBinding.inflate(inflater, container, false) // Создаем экземпляр привязки
        val view = binding.root // Получаем корневое представление

        // Инициализация анимации
        fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)

        // Скрываем кнопку перехода к результатам в начале
        binding.toResultButton.visibility = View.GONE

        // Устанавливаем обработчики клика для кнопок
        binding.submitButton.setOnClickListener { checkAnswer() }
        binding.resetQuizButton.setOnClickListener { startNewQuiz() }
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_welcomeFragment)
        }
        binding.toResultButton.setOnClickListener {
            val action =
                QuizFragmentDirections.actionQuizFragmentToResultFragment(correctAnswersCount)
            findNavController().navigate(action)
        }

        loadQuestion() // Загружаем первый вопрос
        return view // Возвращаем созданное представление
    }

    private fun loadQuestion() {
        if (currentQuestionIndex < questions.size) {
            val question = questions[currentQuestionIndex]
            binding.questionTextView.text = question.first
            binding.answersRadioGroup.removeAllViews()

            val correctAnswer = question.second
            val answerOptions = listOf(correctAnswer, "Лондон", "Москва").shuffled()

            for (answer in answerOptions) {
                val radioButton = RadioButton(requireContext())
                radioButton.text = answer
                binding.answersRadioGroup.addView(radioButton)
            }
            // Применяем анимацию к кнопке «Ответить»
            binding.submitButton.startAnimation(fadeInAnimation) // Запускаем анимацию
        } else {
            notifyQuizFinished()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkAnswer() {
        val selectedId = binding.answersRadioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(requireContext(), "Выберите ответ!", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedRadioButton = requireActivity().findViewById<RadioButton>(selectedId)
        val selectedAnswer = selectedRadioButton.text.toString()

        if (selectedAnswer == questions[currentQuestionIndex].second) {
            correctAnswersCount++
            binding.resultTextView.text = "Правильно!"
        } else {
            binding.resultTextView.text =
                "Неправильно! Правильный ответ: ${questions[currentQuestionIndex].second}"
        }

        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            loadQuestion()
        } else {
            notifyQuizFinished()
        }
    }

    private fun notifyQuizFinished() {
        Toast.makeText(
            context,
            "Квиз завершен! Нажмите кнопку, чтобы увидеть результаты. Правильные ответы: $correctAnswersCount",
            Toast.LENGTH_SHORT
        ).show()
        binding.submitButton.isEnabled = false
        binding.resetQuizButton.visibility = View.VISIBLE
        binding.toResultButton.visibility = View.VISIBLE // Показываем кнопку после завершения квиза
    }

    private fun startNewQuiz() {
        currentQuestionIndex = 0
        correctAnswersCount = 0
        binding.resultTextView.text = ""
        binding.resetQuizButton.visibility = View.GONE
        binding.toResultButton.visibility = View.GONE // Скрываем кнопку при начале нового квиза
        binding.submitButton.isEnabled = true
        loadQuestion()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



//
//package com.example.quizapp.ui.quiz
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.RadioButton
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.example.quizapp.R
//import com.example.quizapp.databinding.FragmentQuizBinding
//
//class QuizFragment : Fragment() {
//
//    private var _binding: FragmentQuizBinding? = null // Объявляем переменную для привязки
//    private val binding get() = _binding!! // Используем безопасный доступ к привязке
//
//    private val questions = listOf(
//        Pair("Столица Франции?", "Париж"),
//        Pair("Столица Германии?", "Берлин"),
//        Pair("Столица Японии?", "Токио")
//    )
//
//    private var currentQuestionIndex = 0
//    private var correctAnswersCount = 0
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentQuizBinding.inflate(inflater, container, false) // Создаем экземпляр привязки
//        val view = binding.root // Получаем корневое представление
//
//        // Устанавливаем обработчики клика для кнопок
//        binding.submitButton.setOnClickListener { checkAnswer() }
//        binding.resetQuizButton.setOnClickListener { startNewQuiz() }
//        binding.backButton.setOnClickListener {
//            findNavController().navigate(R.id.action_quizFragment_to_welcomeFragment)
//        }
//        binding.toResultButton.setOnClickListener {
//            val action = QuizFragmentDirections.actionQuizFragmentToResultFragment(correctAnswersCount)
//            findNavController().navigate(action)
//        }
//
//        loadQuestion() // Загружаем первый вопрос
//        return view // Возвращаем созданное представление
//    }
//
//    private fun loadQuestion() {
//        if (currentQuestionIndex < questions.size) {
//            val question = questions[currentQuestionIndex]
//            binding.questionTextView.text = question.first
//            binding.answersRadioGroup.removeAllViews()
//
//            val correctAnswer = question.second
//            val answerOptions = listOf(correctAnswer, "Лондон", "Москва").shuffled()
//
//            for (answer in answerOptions) {
//                val radioButton = RadioButton(requireContext())
//                radioButton.text = answer
//                binding.answersRadioGroup.addView(radioButton)
//            }
//        } else {
//            notifyQuizFinished()
//        }
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun checkAnswer() {
//        val selectedId = binding.answersRadioGroup.checkedRadioButtonId
//        if (selectedId == -1) {
//            Toast.makeText(requireContext(), "Выберите ответ!", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val selectedRadioButton = requireActivity().findViewById<RadioButton>(selectedId)
//        val selectedAnswer = selectedRadioButton.text.toString()
//
//        if (selectedAnswer == questions[currentQuestionIndex].second) {
//            correctAnswersCount++
//            binding.resultTextView.text = "Правильно!"
//        } else {
//            binding.resultTextView.text = "Неправильно! Правильный ответ: ${questions[currentQuestionIndex].second}"
//        }
//
//        if (currentQuestionIndex < questions.size - 1) {
//            currentQuestionIndex++
//            loadQuestion()
//        } else {
//            notifyQuizFinished()
//        }
//    }
//
//    private fun notifyQuizFinished() {
//        Toast.makeText(
//            context,
//            "Квиз завершен! Нажмите кнопку, чтобы начать новый квиз. Правильные ответы: $correctAnswersCount",
//            Toast.LENGTH_SHORT
//        ).show()
//        binding.submitButton.isEnabled = false
//        binding.resetQuizButton.visibility = View.VISIBLE
//        binding.toResultButton.visibility = View.VISIBLE
//    }
//
//
//    private fun startNewQuiz() {
//        currentQuestionIndex = 0
//        correctAnswersCount = 0
//        binding.resultTextView.text = ""
//        binding.resetQuizButton.visibility = View.GONE
//        binding.toResultButton.visibility = View.GONE
//        binding.submitButton.isEnabled = true
//        loadQuestion()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null // Освобождаем привязку, чтобы избежать утечек памяти
//    }
//}

//


//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.RadioButton
//import android.widget.RadioGroup
//import android.widget.TextView
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.example.quizapp.R
//import com.example.quizapp.databinding.FragmentQuizBinding
//import java.util.zip.Inflater
//
//class QuizFragment : Fragment() {  // Определяем класс QuizFragment, который наследуется от Fragment
//
//    private lateinit var questionTextView: TextView  // Объявляем переменную для TextView, показывающего вопрос
//    private lateinit var answersRadioGroup: RadioGroup  // Объявляем переменную для RadioGroup, в который будут добавляться варианты ответов
//    private lateinit var submitButton: Button  // Объявляем переменную для кнопки отправки ответа
//    private lateinit var resultTextView: TextView  // Объявляем переменную для TextView, показывающего результат ответа
//    private lateinit var resetQuizButton: Button  // Объявляем переменную для кнопки сброса квиза
//    private lateinit var backButton: Button  // Объявляем переменную для кнопки возврата на предыдущий экран
//    private lateinit var toResultButton: Button // Объявляем переменную для кнопки перехода на экран результатов
//
//    private val questions = listOf(  // Создаем список вопросов и правильных ответов
//        Pair("Столица Франции?", "Париж"),  // Первый вопрос
//        Pair("Столица Германии?", "Берлин"),  // Второй вопрос
//        Pair("Столица Японии?", "Токио")   // Третий вопрос
//    )
//
//    private var currentQuestionIndex = 0  // Индекс текущего вопроса, инициализируем нулем
//    private var correctAnswersCount = 0  // Добавляем переменную для подсчета правильных ответов
//
//    override fun onCreateView(  // Переопределяем метод onCreateView, который вызывается для создания представления фрагмента
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Раздуваем макет фрагмента из xml файла fragment_quiz
//        val view = inflater.inflate(R.layout.fragment_quiz, container, false)
//
//        // Инициализируем все наши элементы управления с помощью findViewById
//        questionTextView =
//            view.findViewById(R.id.questionTextView)  // Получаем ссылку на TextView для вопросов
//        answersRadioGroup =
//            view.findViewById(R.id.answersRadioGroup)  // Получаем ссылку на RadioGroup для ответов
//        submitButton =
//            view.findViewById(R.id.submitButton)  // Получаем ссылку на кнопку для отправки ответа
//        resultTextView =
//            view.findViewById(R.id.resultTextView)  // Получаем ссылку на TextView для результата
//        resetQuizButton =
//            view.findViewById(R.id.resetQuizButton)  // Получаем ссылку на кнопку сброса квиза
//        backButton = view.findViewById(R.id.back_button)  // Получаем ссылку на кнопку возврата
//        toResultButton =
//            view.findViewById(R.id.to_result_button)  // Инициализируем кнопку перехода к результатам
//
//        // Устанавливаем обработчики клика для кнопок
//        submitButton.setOnClickListener { checkAnswer() }  // Устанавливаем обработчик нажатия для кнопки отправки
//        resetQuizButton.setOnClickListener { startNewQuiz() }  // Устанавливаем обработчик нажатия для кнопки сброса
//        backButton.setOnClickListener {  // Устанавливаем обработчик для кнопки возврата
//            findNavController().navigate(R.id.action_quizFragment_to_welcomeFragment)  // Переходим к экрану приветствия
//        }
//        toResultButton.setOnClickListener {  // Устанавливаем обработчик клика для кнопки
//            val action = QuizFragmentDirections.actionQuizFragmentToResultFragment(correctAnswersCount)
//            findNavController().navigate(action)  // Переход на экран результатов с передачей аргумента
//        }
//
//        loadQuestion()  // Загружаем первый вопрос
//        return view  // Возвращаем созданное представление
//
//    }
//
//    private fun loadQuestion() {  // Метод для загрузки текущего вопроса
//        if (currentQuestionIndex < questions.size) {  // Проверяем, есть ли еще вопросы
//            val question = questions[currentQuestionIndex]  // Получаем текущий вопрос
//            questionTextView.text = question.first  // Устанавливаем текст вопроса в TextView
//            answersRadioGroup.removeAllViews()  // Сначала очищаем RadioGroup
//
//            val correctAnswer = question.second  // Получаем правильный ответ на текущий вопрос
//            // Создаем список вариантов ответов и перемешиваем их
//            val answerOptions = listOf(correctAnswer, "Лондон", "Москва").shuffled()
//
//            for (answer in answerOptions) {  // Для каждого варианта ответа
//                val radioButton = RadioButton(requireContext())  // Создаем новый RadioButton
//                radioButton.text = answer  // Устанавливаем текст для RadioButton
//                answersRadioGroup.addView(radioButton)  // Добавляем RadioButton в RadioGroup
//            }
//        } else {  // Если вопросов больше нет
//            notifyQuizFinished()  // Уведомляем, что квиз завершен
//        }
//    }
//
//    private fun checkAnswer() {  // Метод для проверки ответа
//        val selectedId =
//            answersRadioGroup.checkedRadioButtonId  // Получаем id выбранного RadioButton
//        if (selectedId == -1) {  // Проверяем, был ли выбран ответ
//            Toast.makeText(requireContext(), "Выберите ответ!", Toast.LENGTH_SHORT)
//                .show()  // Если нет, показываем сообщение
//            return  // Выходим из метода
//        }
//
//        val selectedRadioButton =
//            requireActivity().findViewById<RadioButton>(selectedId)  // Находим выбранный RadioButton по его id
//        val selectedAnswer = selectedRadioButton.text.toString()  // Получаем текст ответа
//
//        // Проверяем правильность ответа
//        if (selectedAnswer == questions[currentQuestionIndex].second) {
//            correctAnswersCount++  // Увеличиваем счетчик правильных ответов
//            // Устанавливаем результат в TextView в зависимости от ответа
//            resultTextView.text = "Правильно!"  // Если ответ правильный
//
//        } else {
//            resultTextView.text =
//                "Неправильно! Правильный ответ: ${questions[currentQuestionIndex].second}"  // Если ответ неправильный
//        }
//
//        // Проверяем, есть ли еще вопросы
//        if (currentQuestionIndex < questions.size - 1) {
//            currentQuestionIndex++  // Переходим к следующему вопросу
//            loadQuestion()  // Загружаем вопрос
//        } else {
//            notifyQuizFinished()  // Если вопросов больше нет, уведомляем об этом
//        }
//    }
//
//    private fun notifyQuizFinished() {  // Метод для уведомления о завершении квиза
//        Toast.makeText(
//            context,
//            "Квиз завершен! Нажмите кнопку, чтобы начать новый квиз. Правильные ответы: $correctAnswersCount",
//            Toast.LENGTH_SHORT
//        ).show()  // Показываем сообщение
//        submitButton.isEnabled = false  // Отключаем кнопку отправки
//        resetQuizButton.visibility = View.VISIBLE  // Показываем кнопку для нового квиза
//        // Использование Safe Args для передачи количества правильных ответов
////        val action = QuizFragmentDirections.actionQuizFragmentToResultFragment(correctAnswersCount)
////        findNavController().navigate(action)
//
//        // Переход на экран результатов с передачей аргумента
//        toResultButton.visibility = View.VISIBLE  // Показываем кнопку для перехода на экран результатов
//       // toResultButton: Button = view.findViewById(R.id.to_result_button)
////        toResultButton.setOnClickListener {  // Устанавливаем обработчик клика для кнопки
// //           findNavController().navigate(R.id.action_quizFragment_to_resultFragment)
////        }
//    }
//
//    private fun startNewQuiz() {  // Метод для начала нового квиза
//        currentQuestionIndex = 0  // Сбрасываем индекс текущего вопроса
//        correctAnswersCount = 0  // Сбрасываем количество правильных ответов
//        resultTextView.text = ""  // Очищаем текст результата
//        resetQuizButton.visibility = View.GONE  // Скрываем кнопку для нового квиза
//        toResultButton.visibility = View.GONE // Скрываем кнопку для перехода на экран результатов
//        submitButton.isEnabled = true  // Включаем кнопку отправки
//        loadQuestion()  // Загружаем первый вопрос
//    }
//}
//
