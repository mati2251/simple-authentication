package session

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SessionTests{
    val session = Session(0)

    @Test
    fun checkSessionId () {
        Assertions.assertEquals(session.sessionId.isNotEmpty(), true)
    }
}