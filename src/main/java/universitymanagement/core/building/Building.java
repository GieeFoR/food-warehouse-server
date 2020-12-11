package universitymanagement.core.building;

import universitymanagement.core.common.Address;

public record Building(int buildingId, Address address, String name) {
}
