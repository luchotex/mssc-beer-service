package guru.springframework.msscbeerservice.services.inventory;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

/**
 * @author Luis M. Kupferberg Ruiz (lkupferberg@overactive.com)
 * @created 2020-07-20 21:21
 */
@Slf4j
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
@Component
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {

  private static final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";
  private RestTemplate restTemplate;

  @Setter private String beerInventoryServiceHost;

  public BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  @Override
  public Integer getOnHandInventory(UUID beerId) {
    ResponseEntity<List<BeerInventoryDto>> responseEntity =
        restTemplate.exchange(
            beerInventoryServiceHost + INVENTORY_PATH,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<BeerInventoryDto>>() {},
            (Object) beerId);

    // sum from inventory List
    Integer quantityOnHand =
        responseEntity.getBody().stream().mapToInt(BeerInventoryDto::getQuantityOnHand).sum();

    return quantityOnHand;
  }
}
