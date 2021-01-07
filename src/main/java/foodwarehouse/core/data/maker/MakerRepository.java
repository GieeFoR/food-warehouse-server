package foodwarehouse.core.data.maker;

import foodwarehouse.core.data.address.Address;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MakerRepository {

    Optional<Maker> createMaker(Address address, String name, String phone, String email) throws SQLException;

    Optional<Maker> updateMaker(int makerId, String name, String phone, String email) throws SQLException;

    boolean deleteMaker(int makerId) throws SQLException;

    Optional<Maker> findMakerById(int makerId) throws SQLException;

    List<Maker> findMakers() throws SQLException;
}
