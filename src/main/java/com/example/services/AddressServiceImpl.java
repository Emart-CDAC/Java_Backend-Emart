package com.example.services;

import org.springframework.stereotype.Service;

import com.example.model.Address;
import com.example.model.Customer;
import com.example.repository.AddressRepository;
import com.example.repository.CustomerRepository;
import com.example.services.AddressService;

import java.util.List;
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public AddressServiceImpl(AddressRepository addressRepository,
                              CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Address addAddress(int customerId, Address address) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        address.setCustomer(customer);
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long addressId, Address address) {
        Address existing = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        existing.setHouseNumber(address.getHouseNumber());
        existing.setLandmark(address.getLandmark());
        existing.setCity(address.getCity());
        existing.setState(address.getState());
        existing.setPincode(address.getPincode());

        return addressRepository.save(existing);
    }

    @Override
    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    @Override
    public List<Address> getAddressesByCustomer(Long customerId) {
        return addressRepository.findByCustomerUserId(customerId);
    }

    @Override
    public Address getUserAddress(Long userId) {
        List<Address> addresses = addressRepository.findByCustomerUserId(userId);
        if (addresses.isEmpty()) {
            throw new RuntimeException("No address found");
        }
        return addresses.get(0);
    }
}
