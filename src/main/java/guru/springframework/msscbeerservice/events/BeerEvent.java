package guru.springframework.msscbeerservice.events;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * @author Luis M. Kupferberg Ruiz (lkupferberg@overactive.com)
 * @created 2020-08-07 06:28
 */
@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {

  private static final long serialVersionUID = -2701637156095321011L;

  private final BeerDto beerDto;
}
