package guru.springframework.msscbeerservice.repositories;

import guru.springframework.msscbeerservice.domain.Beer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Luis M. Kupferberg Ruiz (lkupferberg@overactive.com)
 * @created 2020-07-09 05:23
 */
public interface BeerRepository extends JpaRepository<Beer, UUID> {
  Page<Beer> findAllByBeerName(String beerName, Pageable pageable);

  Page<Beer> findAllByBeerStyle(String beerStyle, Pageable pageable);

  Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, String beerStyle, Pageable pageable);

  Beer findByUpc(String upc);
}
