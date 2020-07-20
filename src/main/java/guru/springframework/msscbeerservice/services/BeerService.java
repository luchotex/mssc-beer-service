package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.web.model.BeerDto;

import java.util.UUID;

/**
 * @author Luis M. Kupferberg Ruiz (lkupferberg@overactive.com)
 * @created 2020-07-18 15:12
 */
public interface BeerService {
  BeerDto getById(UUID beerId);

  BeerDto create(BeerDto beerDto);

  BeerDto updateById(UUID beerId, BeerDto beerDto);
}