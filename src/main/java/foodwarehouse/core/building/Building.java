package foodwarehouse.core.building;

import foodwarehouse.core.common.Address;

public record Building(int buildingId, Address address, String name) {
}
