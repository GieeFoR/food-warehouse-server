package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.maker.Maker;
import foodwarehouse.core.data.maker.MakerRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMakerRepository implements MakerRepository {
    @Override
    public Optional<Maker> createMaker(Address address, String name, String phone, String email) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Maker> updateMaker(int makerId, String name, String phone, String email) throws SQLException {
        return Optional.empty();
    }

    @Override
    public boolean deleteMaker(int makerId) throws SQLException {
        return false;
    }

    @Override
    public Optional<Maker> findMakerById(int makerId) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Maker> findMakers() throws SQLException {
        return null;
    }
}
