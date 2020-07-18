package guru.springframework.msscbeerservice.web.controller;

import guru.springframework.msscbeerservice.services.BeerService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @author Luis M. Kupferberg Ruiz (lkupferberg@overactive.com)
 * @created 2020-07-05 16:43
 */
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
@Valid
public class BeerController {

  private final BeerService beerService;

  @GetMapping("/{beerId}")
  public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId) {
    return new ResponseEntity(beerService.getById(beerId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity create(@Valid @RequestBody BeerDto beerDto) {
    return new ResponseEntity(beerService.create(beerDto), HttpStatus.CREATED);
  }

  @PutMapping("{beerId}")
  public ResponseEntity updateByBeerId(
      @PathVariable("beerId") UUID beerId, @Validated @RequestBody BeerDto beerDto) {
    return new ResponseEntity(beerService.updateById(beerId, beerDto), HttpStatus.NO_CONTENT);
  }
}
