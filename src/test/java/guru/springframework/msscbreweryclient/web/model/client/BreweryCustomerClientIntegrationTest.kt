package guru.springframework.msscbreweryclient.web.model.client

import guru.springframework.msscbreweryclient.web.model.Customer
import guru.springframework.msscbreweryclient.web.model.client.ClientsPath.Companion.API_CUSTOMER_V1_PATH
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.net.URI
import java.util.UUID.randomUUID

@SpringBootTest
class BreweryCustomerClientIntegrationTest {
    @Autowired
    private var client: BreweryCustomerClient? = null

    @Test
    fun getCustomer() {
        val customer = client?.getCustomer(randomUUID())
        assertNotNull(customer)
    }

    @Test
    fun saveNewCustomer() {
        val location: URI? = client?.saveNewCustomer(Customer.builder().name("Alex").build())
        assertNotNull(location)
        location?.path?.contains(API_CUSTOMER_V1_PATH)?.let { assertTrue(it) }
    }

    @Test
    fun updateCustomer() {
        assertDoesNotThrow {
            client?.updateCustomer(
                randomUUID(),
                Customer.builder().build()
            )
        }
    }

    @Test
    fun deleteCustomer() {
        assertDoesNotThrow {
            client?.deleteCustomer(randomUUID())
        }
    }
}