package guru.springframework.msscbreweryclient.web.config

import org.apache.http.impl.nio.client.HttpAsyncClients
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor
import org.apache.http.impl.nio.reactor.IOReactorConfig
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

//@Component
class NioRestTemplateCustomizer : RestTemplateCustomizer {
    override fun customize(restTemplate: RestTemplate?) {
        restTemplate?.setRequestFactory(clientHttpRequestFactory())
    }

    private fun clientHttpRequestFactory(): ClientHttpRequestFactory {
        val reactor = DefaultConnectingIOReactor(
            IOReactorConfig
                .custom()
                .setConnectTimeout(3_000)
                .setIoThreadCount(4)
                .setSoTimeout(3_000)
                .build()
        )

        val manager = PoolingNHttpClientConnectionManager(reactor)
        manager.defaultMaxPerRoute = 100
        manager.maxTotal = 1000

        val client = HttpAsyncClients.custom()
            .setConnectionManager(manager)
            .build()

        return HttpComponentsAsyncClientHttpRequestFactory(client)
    }


}