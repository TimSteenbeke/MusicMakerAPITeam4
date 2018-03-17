package be.kdg.ip.services.api;

import be.kdg.ip.domain.Address;

import java.util.List;

public interface AddressService {
    Address addAddress(Address address);
    Address getAddress(int addressId);
    List<Address> getAllAddress();
    void removeAddress(int addressId);
    Address updateAddress(Address address);
}
