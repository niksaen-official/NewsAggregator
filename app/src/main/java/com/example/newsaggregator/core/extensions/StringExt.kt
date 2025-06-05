package com.example.newsaggregator.core.extensions

fun String.removeHtmlTags(): String {
    return this.replace(Regex("<[^>]*>"), "")
}

fun String.formatDate(): String{
    val parts = this.split(" ")
    return "${parts[1]} ${parts[2]} ${parts[3]}"
}