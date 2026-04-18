package com.gossipgirls.wannmensieren.data.network

import okhttp3.*
import org.jsoup.Jsoup

class TumOnlineAutomator(private val client: OkHttpClient) {

    fun performLogin(username: String, password: String): Boolean {
        // 1. GET the login page to retrieve hidden form tokens (e.g., SAML tokens, CSRF)
        // 2. POST to the login endpoint
        // Cookies are maintained automatically if OkHttpClient is configured with a CookieJar
        return true
    }

    fun registerForCourse(courseId: String, groupId: String) {
        // 1. GET the course registration page on demo.campus.tum.de
        val request = Request.Builder()
            .url("https://demo.campus.tum.de/tumonline/ee/ui/ca2/app/desktop/#/slc.tm.cp/student/courses/$courseId")
            .build()

        val response = client.newCall(request).execute()
        val html = response.body()?.string() ?: return

        // 2. Parse HTML with JSoup to find the exact POST endpoint and hidden security tokens
        val document = Jsoup.parse(html)
        val registrationForm = document.select("form#registrationForm") // hypothetical selector
        val token = registrationForm.select("input[name=csrf_token]").attr("value")

        // 3. POST the registration payload
        val formBody = FormBody.Builder()
            .add("csrf_token", token)
            .add("groupId", groupId)
            .build()

        val postRequest = Request.Builder()
            .url("https://demo.campus.tum.de/tumonline/registration_endpoint") // hypothetical endpoint
            .post(formBody)
            .build()

        client.newCall(postRequest).execute()
    }
}