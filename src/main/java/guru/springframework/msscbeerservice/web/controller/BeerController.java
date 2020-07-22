package guru.springframework.msscbeerservice.web.controller;

import guru.springframework.msscbeerservice.services.BeerService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @author Luis M. Kupferberg Ruiz (lkupferberg@overactive.com)
 * @created 2020-07-05 16:43
 */
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Valid
public class BeerController {

  private final BeerService beerService;

  @GetMapping(path = "/beer", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<BeerPagedList> listBeers(
      @RequestParam(value = "beerName", required = false) String beerName,
      @RequestParam(value = "beerStyle", required = false) String beerStyle,
      @RequestParam(value = "showInventoryOnHand", required = false) boolean showInventoryOnHand,
      Pageable pageable) {

    BeerPagedList beerList =
        beerService.listBeers(beerName, beerStyle, showInventoryOnHand, pageable);
    return new ResponseEntity<>(beerList, HttpStatus.OK);
  }

  @GetMapping("/beer/{beerId}")
  public ResponseEntity<BeerDto> getBeerById(
      @PathVariable("beerId") UUID beerId,
      @RequestParam(value = "showInventoryOnHand", required = false) boolean showInventoryOnHand) {
    return new ResponseEntity(beerService.getById(beerId, showInventoryOnHand), HttpStatus.OK);
  }

  @GetMapping("/beer-upc/{beerUpc}")
  public ResponseEntity<BeerDto> getBeerByUpc(
      @PathVariable("beerUpc") String beerUpc,
      @RequestParam(value = "showInventoryOnHand", required = false) boolean showInventoryOnHand) {
    return new ResponseEntity(beerService.getByUpc(beerUpc, showInventoryOnHand), HttpStatus.OK);
  }

  @PostMapping(path = "/beer")
  public ResponseEntity create(@Valid @RequestBody BeerDto beerDto) {
    return new ResponseEntity(beerService.create(beerDto), HttpStatus.CREATED);
  }

  @PutMapping("/beer/{beerId}")
  public ResponseEntity updateByBeerId(
      @PathVariable("beerId") UUID beerId, @Validated @RequestBody BeerDto beerDto) {
    return new ResponseEntity(beerService.updateById(beerId, beerDto), HttpStatus.NO_CONTENT);
  }
}
