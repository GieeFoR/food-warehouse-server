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
public class MakerService {

    private final MakerRepository makerRepository;

    @Autowired
    public MakerService(MakerRepository makerRepository) {
        this.makerRepository = makerRepository;
    }

    public Optional<Maker> createMaker(Address address, String name, String phone, String email) {
        return makerRepository.createMaker(address, name, phone, email);
    }

    public Optional<Maker> updateMaker(int makerId, String name, String phone, String email) {
        return makerRepository.updateMaker(makerId, name, phone, email);
    }

    public boolean deleteMaker(int makerId) {
        return makerRepository.deleteMaker(makerId);
    }

    public Optional<Maker> findMakerById(int makerId) {
        return makerRepository.findMakerById(makerId);
    }

    public List<Maker> findMakers() {
        return makerRepository.findMakers();
    }
}
