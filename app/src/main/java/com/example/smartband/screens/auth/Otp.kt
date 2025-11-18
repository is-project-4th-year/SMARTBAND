// build.gradle (app)
// implementation("com.squareup.okhttp3:okhttp:4.12.0")
// implementation("com.google.code.gson:gson:2.11.0")

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import com.google.gson.Gson

data class MailPayload(
    val apiKey: String,
    val senderEmail: String,
    val subject: String,
    val appName: String,
    val recipientEmail: String,
    val html: String,
    val emailBody: String // if your API also uses a plain-text fallback
)

private val client = OkHttpClient()
private val gson = Gson()

fun sendOtpHtmlEmail(
    apiKey: String,
    senderEmail: String,
    recipientEmail: String,
    otp: String,
    appName: String = "Neuroband"
) {
    val html = """
        <!doctype html>
        <html lang="en">
        <head>
          <meta charset="utf-8">
          <meta name="viewport" content="width=device-width,initial-scale=1">
          <title>$appName Verification</title>
          <style>
            /* Use inline styles for critical parts below; some clients strip <style> */
            .wrapper { background:#f6f7fb; padding:24px; }
            .card { max-width:560px; margin:0 auto; background:#ffffff; border-radius:12px;
                    box-shadow:0 6px 16px rgba(0,0,0,.06); }
            .header { padding:24px; border-bottom:1px solid #eee; text-align:center; }
            .brand  { font:600 20px/1.2 -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,Arial; color:#3c3f44; }
            .body   { padding:24px 28px; color:#3c3f44; font:400 15px/1.6 -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,Arial; }
            .otp    { display:inline-block; letter-spacing:4px; font:700 28px/1.2 "SF Mono",Consolas,Monaco,monospace;
                      background:#f0e7f5; color:#7c3aad; padding:14px 18px; border-radius:10px; }
            .btn    { display:inline-block; background:#7c3aed; color:#ffffff !important; text-decoration:none;
                      padding:12px 18px; border-radius:10px; font-weight:600; }
            .muted  { color:#6b7280; font-size:12px; }
            .spacer { height:16px; }
          </style>
        </head>
        <body class="wrapper" style="background:#f6f7fb;margin:0;padding:24px;text-align:center">
          <div class="card" style="max-width:560px;margin:0 auto;background:#ffffff;border-radius:12px;box-shadow:0 6px 16px rgba(0,0,0,.06);">
            <div class="header" style="padding:24px;border-bottom:1px solid #eee;text-align:center;">
              <div class="brand" style="font-weight:600;font-size:20px;color:#3c3f44;">$appName</div>
            </div>
            <div class="body" style="padding:24px 28px;color:#3c3f44;font-family:Arial,Helvetica,sans-serif;text-align:center;">
              <h2 style="margin:0 0 10px;">Verify your email</h2>
              <p>Use the One-Time Password below to finish signing in.</p>
              <div class="spacer"></div>
              <div class="otp">$otp</div>
              <div class="spacer"></div>
              <p class="muted" style="color:#6b7280;font-size:12px;">
                This code expires in 5 minutes. If you didn’t request it, you can ignore this email.
              </p>
              <div class="spacer"></div>
              <a class="btn" style="background:#7c3aed;color:#fff;text-decoration:none;padding:12px 18px;border-radius:10px;font-weight:600;"
                 href="#">Open $appName</a>
              <div class="spacer"></div>
              <p class="muted" style="color:#6b7280;font-size:12px;">© ${java.time.Year.now()} $appName</p>
            </div>
          </div>
        </body>
        </html>
    """.trimIndent()

    val payload = MailPayload(
        apiKey = apiKey,
        senderEmail = senderEmail,
        subject = "$appName verification code",
        appName = appName,
        recipientEmail = recipientEmail,
        html = html,
        emailBody = "Your $appName verification code is: $otp"
    )

    val json = gson.toJson(payload)
    val request = Request.Builder()
        .url("https://deepphysio-api.onrender.com/public_mail_send")
        .post(json.toRequestBody("application/json; charset=utf-8".toMediaType()))
        .build()

    client.newCall(request).enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
            android.util.Log.e("EmailAPI", "Send failed", e)
        }
        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
            response.use {
                android.util.Log.d("EmailAPI", "Code=${it.code} Body=${it.body?.string()}")
            }
        }
    })
}
