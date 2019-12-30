package user

import java.io.FileInputStream
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


object MailOperation {

    private fun sendMail(to: String, title: String, text: String) {
        // You must create in folder resources mail.properties with properties
        val props = Properties()
        FileInputStream("src/main/resources/mail.properties").use { input ->
            props.load(input)
        }
        val username = props["mail"].toString()
        val password = props["password"].toString()
        val session = Session.getInstance(props,
            object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(username, password)
                }
            })
        try {
            val message: Message = MimeMessage(session)
            message.setFrom(InternetAddress(username))
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(to)
            )
            message.subject = title
            message.setText(text)
            Transport.send(message)
            println("MAIL SANDED")
        } catch (e: MessagingException) {
            throw e
        }
    }

    fun sendActivationMail (mail: String, key: String){
        sendMail(mail,"Activation Mail", "http://localhost:8000/verification?key=${key}")
    }
}