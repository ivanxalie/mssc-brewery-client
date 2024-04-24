package guru.springframework.msscbreweryclient.web.model.client;

import guru.springframework.msscbreweryclient.web.client.BreweryBeerClient;
import guru.springframework.msscbreweryclient.web.model.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static guru.springframework.msscbreweryclient.web.client.ClientsPath.API_BEER_V1_PATH;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreweryBeerClientIntegrationTest {
    @Autowired
    private BreweryBeerClient client;

    @Test
    void getBeerById() {
        Beer beer = client.getBeerById(UUID.randomUUID());
        assertNotNull(beer);
    }

    @Test
    void saveNewBeer() {
        URI location = client.saveNewBeer(Beer.builder().beerName("New Beer").build());
        assertNotNull(location);
        assertTrue(location.getPath().contains(API_BEER_V1_PATH));
    }

    @Test
    void updateBeer() {
        assertDoesNotThrow(() -> client.updateBeer(UUID.randomUUID(), Beer.builder().build()));
    }

    @Test
    void deleteBeer() {
        assertDoesNotThrow(() -> client.deleteBeer(UUID.randomUUID()));
    }
}