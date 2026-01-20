package com.example.services;

import java.util.List;

import com.example.model.Address;

public interface AddressService {

    Address addAddress(int userId, Address address);

    Address updateAddress(Long addressId, Address address);

    void deleteAddress(Long addressId);

    Address getUserAddress(Long userId);

	List<Address> getAddressesByCustomer(Long customerId);
}
