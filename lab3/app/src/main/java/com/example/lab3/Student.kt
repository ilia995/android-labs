package com.example.lab3

import kotlin.collections.ArrayList

class Student(private val name: String, private val groupNumber: Int) {
    companion object {
        private val students : ArrayList<Student> = ArrayList(
            listOf(
                Student("Едуард Бутенко", 301),
                Student("Костянтин Гнатюк", 301),
                Student("Орест Пащенко", 302),
                Student("Іван Фоменко", 301),
                Student("Олексій Іванов", 303),
                Student("Станіслава Заїка", 302),
                Student("Яна Сушко", 304),
                Student("Ксенія Кузьменко", 303),
                Student("Тетяна Горбатюк", 301),
                Student("Олександра Остапчук", 302),
                Student("Денис Гриценко", 302),
                Student("Борис Грицюк", 303),
                Student("Вадим Миронюк", 303),
                Student("Микита Литвин", 304),
                Student("Феодосій Лобода", 301),
                Student("Панас Антонюк", 304),
                Student("Ірина Лисак", 303),
                Student("Віра Цимбал", 302),
                Student("Марія Корнійчук", 302),
                Student("Яна Матвійчук", 302)
            )
        )
        fun getStudents(group: Int) = run {
            var names = ""
            for (student in students){
                if (student.groupNumber == group){
                    names += "${student.name}\n"
                }
            }
            names
        }
    }
}