package guru.springframework.msscbeerservice.events;

import guru.springframework.msscbeerservice.web.model.BeerDto;

/**
 * @author Luis M. Kupferberg Ruiz (lkupferberg@overactive.com)
 * @created 2020-08-07 06:29
 */
public class BrewBeerEvent extends BeerEvent {
  public BrewBeerEvent(BeerDto beerDto) {
    super(beerDto);
  }
}
