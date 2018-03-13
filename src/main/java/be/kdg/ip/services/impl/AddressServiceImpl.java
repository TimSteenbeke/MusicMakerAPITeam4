package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Address;
import be.kdg.ip.repositories.api.AddressRepository;
import be.kdg.ip.services.api.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service("AddressService")
@Transactional
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressRepository addressRepository;
    @Override
    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address getAddress(int addressId) {
        return addressRepository.findOne(addressId);
    }

    @Override
    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }

    @Override
    public void removeAddress(int addressId) {
        Address address = addressRepository.findOne(addressId);
        addressRepository.delete(address);
    }

    @Override
    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }
}
