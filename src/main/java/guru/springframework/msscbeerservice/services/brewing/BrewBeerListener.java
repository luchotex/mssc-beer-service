package guru.springframework.msscbeerservice.services.brewing;

import guru.springframework.msscbeerservice.config.JmsConfig;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.events.BrewBeerEvent;
import guru.springframework.msscbeerservice.events.NewInventoryEvent;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Luis M. Kupferberg Ruiz (lkupferberg@overactive.com)
 * @created 2020-08-07 07:42
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BrewBeerListener {

  private final JmsTemplate jmsTemplate;
  private final BeerRepository beerRepository;

  @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
  public void listen(BrewBeerEvent event) {
    BeerDto beerDto = event.getBeerDto();

    Beer beer = beerRepository.getOne(beerDto.getId());
    beerDto.setQuantityOnHand(beer.getQuantityToBrew());

    log.debug("Brewed beer: {}, QOH: {}", beer.getMinOnHand(), beerDto.getQuantityOnHand());

    NewInventoryEvent inventoryEvent = new NewInventoryEvent(beerDto);
    jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, inventoryEvent);
  }
}
