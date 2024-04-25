package guru.springframework.msscbreweryclient.web.config

import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Profile
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
@Profile("default")
open class BlockingRestTemplateCustomizer(
    private val config: RestClientCustomizerConfig) : RestTemplateCustomizer {
    override fun customize(restTemplate: RestTemplate?) {
        restTemplate?.setRequestFactory(clientHttpRequestFactory())
    }

    private fun clientHttpRequestFactory(): ClientHttpRequestFactory {
        val manager = PoolingHttpClientConnectionManager()
        manager.maxTotal = config.maxTotalConnections
        manager.defaultMaxPerRoute = config.defaultMaxTotalConnections

        val requestConfig = RequestConfig
            .custom()
            .setConnectionRequestTimeout(config.connectionRequestTimeout)
            .setSocketTimeout(config.socketTimeout)
            .build()

        val client = HttpClients
            .custom()
            .setConnectionManager(manager)
            .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy())
            .setDefaultRequestConfig(requestConfig)
            .build()

        return HttpComponentsClientHttpRequestFactory(client)
    }
}