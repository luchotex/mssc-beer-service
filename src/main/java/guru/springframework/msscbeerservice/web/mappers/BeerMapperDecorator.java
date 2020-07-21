package guru.springframework.msscbeerservice.web.mappers;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.services.inventory.BeerInventoryService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Luis M. Kupferberg Ruiz (lkupferberg@overactive.com)
 * @created 2020-07-20 21:53
 */
public abstract class BeerMapperDecorator implements BeerMapper {
  private BeerInventoryService beerInventoryService;

  private BeerMapper beerMapper;

  @Autowired
  public void setBeerMapper(BeerMapper beerMapper) {
    this.beerMapper = beerMapper;
  }

  @Autowired
  public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
    this.beerInventoryService = beerInventoryService;
  }

  @Override
  public BeerDto beerToBeerDto(Beer beer) {
    BeerDto beerDto = beerMapper.beerToBeerDto(beer);
    beerDto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));

    return beerDto;
  }

  @Override
  public BeerDto beerToBeerDto(Beer beer, boolean showInventoryOnHand) {
    BeerDto beerDto = beerMapper.beerToBeerDto(beer);
    if (showInventoryOnHand) {
      beerDto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
    }

    return beerDto;
  }

  @Override
  public Beer beerDtoToBeer(BeerDto beerDto) {
    return beerMapper.beerDtoToBeer(beerDto);
  }
}
