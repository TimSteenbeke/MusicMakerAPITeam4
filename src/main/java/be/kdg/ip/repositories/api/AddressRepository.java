package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
