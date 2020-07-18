package guru.springframework.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbeerservice.bootstrap.BeerLoader;
import guru.springframework.msscbeerservice.services.BeerService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BeerControllerTest {

  @Autowired MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private BeerService beerService;

  @Test
  void getBeerById_successfulTest() throws Exception {

    given(beerService.getById(any())).willReturn(getValidBeerDto());

    mockMvc
        .perform(
            get("/api/v1/beer/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void create_successfulTest() throws Exception {

    BeerDto beerDto = getValidBeerDto();
    String beerDtoJson = objectMapper.writeValueAsString(beerDto);

    given(beerService.create(any())).willReturn(getValidBeerDto());

    mockMvc
        .perform(post("/api/v1/beer").contentType(MediaType.APPLICATION_JSON).content(beerDtoJson))
        .andExpect(status().isCreated());
  }

  @Test
  void updateByBeerId_successfulTest() throws Exception {

    UUID uuid = UUID.randomUUID();
    BeerDto beerDto = getValidBeerDto();
    String beerDtoJson = objectMapper.writeValueAsString(beerDto);

    given(beerService.updateById(any(), any())).willReturn(getValidBeerDto());

    mockMvc
        .perform(
            put("/api/v1/beer/" + uuid.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
        .andExpect(status().isNoContent());
  }

  BeerDto getValidBeerDto() {
    return BeerDto.builder()
        .beerName("Test Beer")
        .price(new BigDecimal("2.99"))
        .beerStyle(BeerStyleEnum.ALE)
        .upc(BeerLoader.BEER_1_UPC)
        .build();
  }
}
