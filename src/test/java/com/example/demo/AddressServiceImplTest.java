// package com.example.demo;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import java.util.*;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import com.example.model.Address;
// import com.example.model.Customer;
// import com.example.repository.AddressRepository;
// import com.example.repository.CustomerRepository;
// import com.example.services.AddressServiceImpl;

// @ExtendWith(MockitoExtension.class)
// class AddressServiceImplTest {

// @Mock
// private AddressRepository addressRepository;

// @Mock
// private CustomerRepository customerRepository;

// @InjectMocks
// private AddressServiceImpl addressService;

// private Customer customer;
// private Address address;

// @BeforeEach
// void setUp() {
// customer = new Customer();
// customer.setUserId(1);
// address = new Address();
// address.setAddressId((long) 1);
// address.setHouseNumber("101");
// address.setLandmark("Near Mall");
// address.setCity("Mumbai");
// address.setState("MH");
// address.setPincode("400001");
// address.setCustomer(customer);
// }

// // ------------------ addAddress ------------------

// @Test
// void testAddAddress_Success() {
// when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
// when(addressRepository.save(any(Address.class))).thenReturn(address);

// Address saved = addressService.addAddress(1, address);

// assertNotNull(saved);
// assertEquals("Mumbai", saved.getCity());
// verify(addressRepository, times(1)).save(address);
// }

// @Test
// void testAddAddress_CustomerNotFound() {
// when(customerRepository.findById(1)).thenReturn(Optional.empty());

// RuntimeException ex = assertThrows(RuntimeException.class, () ->
// addressService.addAddress(1, address));

// assertEquals("Customer not found", ex.getMessage());
// }

// // ------------------ updateAddress ------------------

// @Test
// void testUpdateAddress_Success() {
// when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
// when(addressRepository.save(any(Address.class))).thenReturn(address);

// Address updated = addressService.updateAddress(1L, address);

// assertNotNull(updated);
// assertEquals("Mumbai", updated.getCity());
// verify(addressRepository).save(address);
// }

// @Test
// void testUpdateAddress_NotFound() {
// when(addressRepository.findById(1L)).thenReturn(Optional.empty());

// RuntimeException ex = assertThrows(RuntimeException.class, () ->
// addressService.updateAddress(1L, address));

// assertEquals("Address not found", ex.getMessage());
// }

// // ------------------ deleteAddress ------------------

// @Test
// void testDeleteAddress() {
// doNothing().when(addressRepository).deleteById(1L);

// addressService.deleteAddress(1L);

// verify(addressRepository, times(1)).deleteById(1L);
// }

// // ------------------ getAddressesByCustomer ------------------

// @Test
// void testGetAddressesByCustomer() {
// List<Address> addresses = Arrays.asList(address);

// when(addressRepository.findByCustomerUserId(1L)).thenReturn(addresses);

// List<Address> result = addressService.getAddressesByCustomer(1L);

// assertEquals(1, result.size());
// assertEquals("Mumbai", result.get(0).getCity());
// }

// // ------------------ getUserAddress ------------------

// @Test
// void testGetUserAddress_Success() {
// when(addressRepository.findByCustomerUserId(1L)).thenReturn(Arrays.asList(address));

// Address result = addressService.getUserAddress(1L);

// assertNotNull(result);
// assertEquals("Mumbai", result.getCity());
// }

// @Test
// void testGetUserAddress_NotFound() {
// when(addressRepository.findByCustomerUserId(1L)).thenReturn(Collections.emptyList());

// RuntimeException ex = assertThrows(RuntimeException.class, () ->
// addressService.getUserAddress(1L));

// assertEquals("No address found", ex.getMessage());
// }
// }
