package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.controller.NotFoundException;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Luis M. Kupferberg Ruiz (lkupferberg@overactive.com)
 * @created 2020-07-18 15:15
 */
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

  private final BeerRepository beerRepository;
  private final BeerMapper beerMapper;

  @Override
  @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand = false")
  public BeerDto getById(UUID beerId, boolean showInventoryOnHand) {
    return beerMapper.beerToBeerDto(
        beerRepository.findById(beerId).orElseThrow(NotFoundException::new), showInventoryOnHand);
  }

  @Override
  @Cacheable(
      cacheNames = "beerByUpcCache",
      key = "#upc",
      condition = "#showInventoryOnHand = false")
  public BeerDto getByUpc(String upc, boolean showInventoryOnHand) {
    return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc), showInventoryOnHand);
  }

  @Override
  public BeerDto create(BeerDto beerDto) {
    return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
  }

  @Override
  public BeerDto updateById(UUID beerId, BeerDto beerDto) {
    Beer beerToUpdate = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
    beerToUpdate.setBeerName(beerDto.getBeerName());
    beerToUpdate.setBeerStyle(beerDto.getBeerStyle().name());
    beerToUpdate.setPrice(beerDto.getPrice());
    beerToUpdate.setUpc(beerDto.getUpc());

    return beerMapper.beerToBeerDto(beerRepository.save(beerToUpdate));
  }

  @Override
  @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand = false ")
  public BeerPagedList listBeers(
      String beerName, String beerStyle, boolean showInventoryOnHand, Pageable pageable) {
    BeerPagedList beerPagedList;
    Page<Beer> beerPage;

    if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
      beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageable);

    } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
      beerPage = beerRepository.findAllByBeerName(beerName, pageable);
    } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
      beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageable);
    } else {
      beerPage = beerRepository.findAll(pageable);
    }

    beerPagedList =
        new BeerPagedList(
            beerPage.getContent().stream()
                .map(beer -> beerMapper.beerToBeerDto(beer, showInventoryOnHand))
                .collect(Collectors.toList()),
            pageable,
            beerPage.getSize());

    return beerPagedList;
  }
}
