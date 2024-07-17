package SWD.NET1704.controllers;

import SWD.NET1704.dtos.request.BookingRequest;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.services.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zoo-server/api/v1/order")
@AllArgsConstructor
public class OrderController {
    private final BookingService bookingService;

    @GetMapping("/getAllBooking")
    public ResponseEntity<ApiResponse> getAllBooking() {
        return bookingService.getAllBooking();
    }

    @GetMapping("/getAllBookingOf4thQuarter")
    public ResponseEntity<ApiResponse> getAllBookingOf4thQuarter() {
        return bookingService.getAllBookingOf4thQuarter();
    }


    @GetMapping("/getBookingById{id}")
    public ResponseEntity<ApiResponse> getBookingById(@PathVariable("id") int bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @GetMapping("/getBookingByAccount{id}")
    public ResponseEntity<ApiResponse> getBookingByAccount(@PathVariable("id") int accountId) {
        return bookingService.getBookingByAccount(accountId);
    }

    @GetMapping("/getBookingByProduct{id}")
    public ResponseEntity<ApiResponse> getBookingByProduct(@PathVariable("id") int productId) {
        return bookingService.getBookingByProduct(productId);
    }

    @PostMapping("/createNewBooking")
    public ResponseEntity<ApiResponse> createNewBooking(@RequestBody BookingRequest bookingRequest) {
        return bookingService.createNewBooking(bookingRequest);
    }

    @PutMapping("/updateBooking/{id}")
    public ResponseEntity<ApiResponse> updateBooking(@PathVariable("id") int bookingId, @RequestBody BookingRequest bookingRequest) {
        return bookingService.updateBooking(bookingId, bookingRequest);
    }

    @DeleteMapping("/deleteBooking/{id}")
    public ResponseEntity<ApiResponse> deleteBooking(@PathVariable("id") int bookingId) {
        return bookingService.deleteBooking(bookingId);
    }
}
