package com.example.services;

import java.util.List;

import com.example.model.Address;

public interface AddressService {

    Address addAddress(int userId, Address address);

    Address updateAddress(int addressId, Address address);

    void deleteAddress(int addressId);

    Address getUserAddress(Integer userId);

    List<Address> getAddressesByCustomer(Integer customerId);
}
