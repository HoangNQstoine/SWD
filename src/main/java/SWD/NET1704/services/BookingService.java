package SWD.NET1704.services;

import org.springframework.http.ResponseEntity;
import SWD.NET1704.dtos.request.BookingRequest;
import SWD.NET1704.dtos.response.ApiResponse;


public interface BookingService {
    ResponseEntity<ApiResponse> getAllBooking();
    ResponseEntity<ApiResponse> getAllBookingOf4thQuarter();

    ResponseEntity<ApiResponse> getBookingById(int bookingId);

    ResponseEntity<ApiResponse> updateBooking(int bookingId, BookingRequest bookingRequest);

    ResponseEntity<ApiResponse> createNewBooking(BookingRequest bookingRequest);
    ResponseEntity<ApiResponse> getBookingByAccount(int userId);
    ResponseEntity<ApiResponse> getBookingByProduct(int productId);
    ResponseEntity<ApiResponse> deleteBooking(int bookingId);
}
