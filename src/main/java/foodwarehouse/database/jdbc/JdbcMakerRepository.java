package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.car.Car;
import foodwarehouse.core.data.maker.Maker;
import foodwarehouse.core.data.maker.MakerRepository;
import foodwarehouse.database.rowmappers.CarResultSetMapper;
import foodwarehouse.database.rowmappers.MakerResultSetMapper;
import foodwarehouse.database.tables.CarTable;
import foodwarehouse.database.tables.MakerTable;
import foodwarehouse.web.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMakerRepository implements MakerRepository {

    private final Connection connection;

    @Autowired
    JdbcMakerRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }

    @Override
    public Optional<Maker> createMaker(
            Address address,
            String name,
            String phone,
            String email) {

        try {
            CallableStatement callableStatement = connection.prepareCall(MakerTable.Procedures.INSERT);
            callableStatement.setInt(1, address.addressId());
            callableStatement.setString(2, name);
            callableStatement.setString(3, phone);
            callableStatement.setString(4, email);

            callableStatement.executeQuery();
            int makerId = callableStatement.getInt(5);
            return Optional.of(new Maker(makerId, address, name, phone, email));
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Maker> updateMaker(
            int makerId,
            Address address,
            String name,
            String phone,
            String email) {

        try {
            CallableStatement callableStatement = connection.prepareCall(MakerTable.Procedures.UPDATE);
            callableStatement.setInt(1, makerId);
            callableStatement.setString(2, name);
            callableStatement.setString(3, phone);
            callableStatement.setString(4, email);

            callableStatement.executeQuery();

            return Optional.of(new Maker(makerId, address, name, phone, email));
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteMaker(int makerId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(MakerTable.Procedures.DELETE);
            callableStatement.setInt(1, makerId);

            callableStatement.executeQuery();
            return true;
        }
        catch (SQLException sqlException) {
            return false;
        }
    }

    @Override
    public Optional<Maker> findMakerById(int makerId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(MakerTable.Procedures.READ_BY_ID);
            callableStatement.setInt(1, makerId);

            ResultSet resultSet = callableStatement.executeQuery();
            Maker maker = null;
            if(resultSet.next()) {
                maker = new MakerResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(maker);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public List<Maker> findMakers() {
        List<Maker> makers = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(MakerTable.Procedures.READ_ALL);

            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                makers.add(new MakerResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            sqlException.getMessage();
        }
        return makers;
    }
}
