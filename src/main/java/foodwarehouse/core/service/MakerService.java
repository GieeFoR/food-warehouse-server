package foodwarehouse.core.service;

import foodwarehouse.core.data.address.Address;
import foodwarehouse.core.data.maker.Maker;
import foodwarehouse.core.data.maker.MakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class MakerService implements MakerRepository {

    private final MakerRepository makerRepository;

    @Autowired
    public MakerService(MakerRepository makerRepository) {
        this.makerRepository = makerRepository;
    }

    @Override
    public Optional<Maker> createMaker(Address address, String name, String phone, String email) throws SQLException {
        return makerRepository.createMaker(address, name, phone, email);
    }

    @Override
    public Optional<Maker> updateMaker(int makerId, String name, String phone, String email) throws SQLException {
        return makerRepository.updateMaker(makerId, name, phone, email);
    }

    @Override
    public boolean deleteMaker(int makerId) throws SQLException {
        return makerRepository.deleteMaker(makerId);
    }

    @Override
    public Optional<Maker> findMakerById(int makerId) throws SQLException {
        return makerRepository.findMakerById(makerId);
    }

    @Override
    public List<Maker> findMakers() throws SQLException {
        return makerRepository.findMakers();
    }
}
