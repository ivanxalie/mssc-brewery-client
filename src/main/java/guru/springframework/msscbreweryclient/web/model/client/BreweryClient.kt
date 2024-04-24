package guru.springframework.msscbreweryclient.web.model.client

import guru.springframework.msscbreweryclient.web.model.Beer
import guru.springframework.msscbreweryclient.web.model.client.ClientsPath.Companion.API_BEER_V1_PATH
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component
import org.springframework.web.client.getForObject
import java.net.URI
import java.util.*

@Component
@ConfigurationProperties(value = "sfg.brewery")
open class
BreweryClient(restTemplateBuilder: RestTemplateBuilder) {
    private val restTemplate = restTemplateBuilder.build()
    open var apiHost: String? = null

    fun getBeerById(id: UUID): Beer? = restTemplate.getForObject<Beer>("${constructPath()}/${id}")

    private fun constructPath() = "${apiHost}/$API_BEER_V1_PATH"

    fun saveNewBeer(beer: Beer): URI? = restTemplate.postForLocation(constructPath(), beer)

    fun updateBeer(id: UUID, beer: Beer) = restTemplate.put("${constructPath()}/${id}", beer)

    fun deleteBeer(id: UUID) = restTemplate.delete("${constructPath()}/${id}")
}