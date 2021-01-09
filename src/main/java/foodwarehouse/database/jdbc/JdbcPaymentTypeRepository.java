package foodwarehouse.database.jdbc;

import foodwarehouse.core.data.paymentType.PaymentType;
import foodwarehouse.core.data.paymentType.PaymentTypeRepository;
import foodwarehouse.database.rowmappers.PaymentTypeResultSetMapper;
import foodwarehouse.database.tables.PaymentTypeTable;
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
public class JdbcPaymentTypeRepository implements PaymentTypeRepository {

    private final Connection connection;

    @Autowired
    JdbcPaymentTypeRepository(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        }
        catch(SQLException sqlException) {
            throw new RestException("Cannot connect to database!");
        }
    }

    @Override
    public Optional<PaymentType> createPaymentType(String type) {
        try {
            CallableStatement callableStatement = connection.prepareCall(PaymentTypeTable.Procedures.INSERT);
            callableStatement.setString(1, type);

            callableStatement.executeQuery();
            int paymentTypeId = callableStatement.getInt(2);
            return Optional.of(new PaymentType(paymentTypeId, type));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<PaymentType> updatePaymentType(int paymentTypeId, String type) {
        try {
            CallableStatement callableStatement = connection.prepareCall(PaymentTypeTable.Procedures.UPDATE);
            callableStatement.setInt(1, paymentTypeId);
            callableStatement.setString(2, type);

            callableStatement.executeQuery();

            return Optional.of(new PaymentType(paymentTypeId, type));
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean deletePaymentType(int paymentTypeId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(PaymentTypeTable.Procedures.DELETE);
            callableStatement.setInt(1, paymentTypeId);

            callableStatement.executeQuery();
            return true;
        }
        catch (SQLException sqlException) {
            return false;
        }
    }

    @Override
    public Optional<PaymentType> findPaymentTypeById(int paymentTypeId) {
        try {
            CallableStatement callableStatement = connection.prepareCall(PaymentTypeTable.Procedures.READ_BY_ID);
            callableStatement.setInt(1, paymentTypeId);

            ResultSet resultSet = callableStatement.executeQuery();
            PaymentType paymentType = null;
            if(resultSet.next()) {
                paymentType = new PaymentTypeResultSetMapper().resultSetMap(resultSet, "");
            }
            return Optional.ofNullable(paymentType);
        }
        catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    @Override
    public List<PaymentType> findPayments() {
        List<PaymentType> paymentTypes = new LinkedList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(PaymentTypeTable.Procedures.READ_ALL);

            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                paymentTypes.add(new PaymentTypeResultSetMapper().resultSetMap(resultSet, ""));
            }
        }
        catch(SQLException sqlException) {
            sqlException.getMessage();
        }
        return paymentTypes;
    }
}
