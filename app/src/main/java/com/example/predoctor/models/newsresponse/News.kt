package com.example.predoctor.models.newsresponse

data class News(
    val image: String,
    val link: String?,
    val title: String
){

    override fun hashCode(): Int {
        var result = title.hashCode()
        if(link.isNullOrEmpty()){
            result = 31 * result + link.hashCode()
        }
        return result
    }
}