package universitymanagement.core.building;

import universitymanagement.core.building.Building;

public record Room(int roomId, Building building, String number) {
}
