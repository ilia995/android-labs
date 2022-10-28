package com.example.lab3

import kotlin.collections.ArrayList

class Student(private val name: String, private val groupNumber: String) {
    companion object {
        private val students : ArrayList<Student> = ArrayList(
            listOf(
                Student("Едуард Бутенко", "K25–1"),
                Student("Костянтин Гнатюк", "K25–2"),
                Student("Орест Пащенко", "K25–4"),
                Student("Іван Фоменко", "K25–3"),
                Student("Олексій Іванов", "K25–2"),
                Student("Станіслава Заїка", "K25–3"),
                Student("Яна Сушко", "K25–1"),
                Student("Ксенія Кузьменко", "K25–2"),
                Student("Тетяна Горбатюк", "K25–4"),
                Student("Олександра Остапчук", "K25–3"),
                Student("Денис Гриценко", "K25–2"),
                Student("Борис Грицюк", "K25–2"),
                Student("Вадим Миронюк", "K25–1"),
                Student("Микита Литвин", "K25–3"),
                Student("Феодосій Лобода", "K25–2"),
                Student("Панас Антонюк", "K25–4"),
                Student("Ірина Лисак", "K25–3"),
                Student("Віра Цимбал", "K25–2"),
                Student("Марія Корнійчук", "K25–1"),
                Student("Яна Матвійчук", "K25m")
            )
        )
        fun getStudents(group: String) = run {
            var names = ""
            for (student in students){
                if (student.groupNumber == group){
                    names += "${student.name}\n"
                }
            }
            names
        }
        fun getStudentsList(group: String) = run{
            val studentNameList = ArrayList<String>()
            for (student in students){
                if (student.groupNumber == group){
                    studentNameList.add(student.name)
                }
            }
            studentNameList
        }
    }
}