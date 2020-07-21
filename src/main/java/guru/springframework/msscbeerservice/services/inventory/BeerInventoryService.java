package guru.springframework.msscbeerservice.services.inventory;

import java.util.UUID;

/**
 * @author Luis M. Kupferberg Ruiz (lkupferberg@overactive.com)
 * @created 2020-07-20 21:20
 */
public interface BeerInventoryService {

    Integer getOnHandInventory(UUID beerId);
}
