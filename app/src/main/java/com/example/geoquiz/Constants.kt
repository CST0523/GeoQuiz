package com.example.geoquiz

object Constants {
    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_question"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestions():ArrayList<Question>{
        val questionsList = ArrayList<Question>()

        val que1 = Question(
            1,
            "What country does this flag beling to?",
            R.drawable.ic_flag_malaysia,
            "Argentina",
            "Asutralia",
            "Malaysia",
            "Austria",
            3)

        questionsList.add(que1)


        val que2 = Question(
            2,
            "In which country is Mount Fuji located?",
            R.drawable.ic_fuji,
            "Thailand",
            "Japan",
            "Singapore",
            "Korea",
            2)
        questionsList.add(que2)

        val que3 = Question(
            3,
            "Which of the following is the longest river in the world?",
            R.drawable.ic_nile,
            "Amazon",
            "Nile",
            "Mississippi",
            "Yangtze",
            2)
        questionsList.add(que3)

        val que4 = Question(
            4,
            "Which country is the smallest in terms of land area?",
            R.drawable.ic_vatican,
            "Monaco",
            "Vatican City",
            "Nauru",
            "San Marino",
            2)
        questionsList.add(que4)

        val que5 = Question(
            5,
            "Mount Everest, the highest mountain in the world, is located in which mountain range?",
            R.drawable.ic_himalayas,
            "Himalayas",
            "Andes",
            "Alps",
            "Rockies",
            1)
        questionsList.add(que5)

        val que6 = Question(
            6,
            "Which ocean is the largest and deepest on Earth?",
            R.drawable.ic_pacific,
            "Atlantic Ocean",
            "Indian Ocean",
            "Pacific Ocean",
            "Arctic Ocean",
            3)
        questionsList.add(que6)

        val que7 = Question(
            7,
            "What is the world's largest archipelago by area?",
            R.drawable.ic_archipelago,
            "Philippines",
            "Indonesia",
            "Japan",
            "Maldives",
            2)
        questionsList.add(que7)

        val que8 = Question(
            8,
            "Which two regions make up Malaysia?",
            R.drawable.ic_malaysia,
            "Peninsular Malaysia and East Malaysia",
            "West Malaysia and East Malaysia",
            "Mainland Malaysia and Borneo Malaysia",
            "Northern Malaysia and Southern Malaysia",
            1)
        questionsList.add(que8)

        val que9 = Question(
            9,
            "Malaysia shares its land borders with which two countries?",
            R.drawable.ic_malaysiaborder,
            "Thailand and Singapore",
            "Thailand and Indonesia",
            "Indonesia and Brunei",
            "Singapore and Brunei",
            2)
        questionsList.add(que9)

        val que10 = Question(
            10,
            "Malaysia shares its land borders with which two countries?",
            R.drawable.ic_kl,
            "Penang",
            "Johor Bahru",
            "Kuala Lumpur",
            "Kota Kinabalu",
            3)
        questionsList.add(que10)

        return questionsList
    }
}