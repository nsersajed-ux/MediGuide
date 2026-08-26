package com.example.mediguide

// 1. نموذج تمثيل الرسالة داخل واجهة التطبيق
data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

// 2. نموذج طلب البيانات الموجه للسيرفر (Request)
data class ChatRequest(
    val model: String = "deepseek/deepseek-chat",
    val messages: List<ApiMessage>
)

data class ApiMessage(
    val role: String,
    val content: String
)

// 3. نموذج استقبال الاستجابة من السيرفر (Response)
data class ChatResponse(
    val choices: List<Choice>?
)

data class Choice(
    val message: ApiMessage?
)