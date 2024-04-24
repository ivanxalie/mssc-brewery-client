package guru.springframework.msscbreweryclient.web.client

import guru.springframework.msscbreweryclient.web.model.Customer
import guru.springframework.msscbreweryclient.web.client.ClientsPath.Companion.API_CUSTOMER_V1_PATH
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component
import java.net.URI
import java.util.*

@Component
@ConfigurationProperties(value = "sfg.brewery")
open class BreweryCustomerClient(restTemplateBuilder: RestTemplateBuilder) {
    private val restTemplate = restTemplateBuilder.build()
    open var apiHost: String? = null

    fun getCustomer(id: UUID): Customer? = restTemplate.getForObject(
        "${constructPath()}/${id}", Customer::class.java
    )

    private fun constructPath() = "${apiHost}/$API_CUSTOMER_V1_PATH"

    fun saveNewCustomer(customer: Customer): URI? = restTemplate.postForLocation(constructPath(), customer)

    fun updateCustomer(id: UUID, customer: Customer) = restTemplate.put("${constructPath()}/${id}", customer)

    fun deleteCustomer(id: UUID) = restTemplate.delete("${constructPath()}/${id}")
}