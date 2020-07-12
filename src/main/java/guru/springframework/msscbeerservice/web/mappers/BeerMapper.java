package guru.springframework.msscbeerservice.web.mappers;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

/**
 * @author Luis M. Kupferberg Ruiz (lkupferberg@overactive.com)
 * @created 2020-07-12 09:04
 */
@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

  BeerDto beerToBeerDto(Beer beer);

  Beer beerDtoToBeer(BeerDto beerDto);
}
