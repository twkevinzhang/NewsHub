package self.nesl.hub_server

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.hub_server.data.*

internal class BoardExtensionsTest {

    @Test
    fun `Test Board toKBoard extensions expect successful`() {
        val kBoard = Board("sora", "https://gaia.komica.org/00", Host.KOMICA).toKBoard()
        assertEquals(kBoard.url, "https://gaia.komica.org/00")
    }

    @Test
    fun `Test String toBoard extensions expect successful`() {
        val board = "https://gaia.komica.org/00".toBoard()
        assertEquals(board.url, "https://gaia.komica.org/00")
    }
}