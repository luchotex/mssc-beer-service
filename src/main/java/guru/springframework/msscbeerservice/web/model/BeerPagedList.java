package guru.springframework.msscbeerservice.web.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Luis M. Kupferberg Ruiz (lkupferberg@overactive.com)
 * @created 2020-07-04 20:06
 */
public class BeerPagedList extends PageImpl<BeerDto> {
  public BeerPagedList(List<BeerDto> content, Pageable pageable, long total) {
    super(content, pageable, total);
  }

  public BeerPagedList(List<BeerDto> content) {
    super(content);
  }
}
