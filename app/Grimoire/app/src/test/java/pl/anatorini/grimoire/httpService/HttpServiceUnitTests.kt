package pl.anatorini.grimoire.httpService


import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import pl.anatorini.grimoire.services.HttpService


class HttpServiceUnitTests {
    @Test
    fun fetchAdminsCampaigns() {
        runBlocking {
            HttpService.address = "localhost"
            HttpService.login("admin", "password")
            val campaigns = HttpService.getCampaigns(null)
            for (campaign in campaigns.results) {
                for (player in campaign.players) {
                    println(player.getValue().player.getValue().name)
                }
            }
        }
        assertEquals(4, 2 + 2)
    }

    companion object {
        @JvmStatic
        @BeforeClass
        fun disableLogging(): Unit {
            System.setProperty("org.gradle.logging.level", "quiet")
        }
    }

}
