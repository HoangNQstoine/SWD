package SWD.NET1704.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.request.BookingRequest;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.dtos.response.BookingDTO;
import SWD.NET1704.entities.BookingEntity;
import SWD.NET1704.entities.ProductEntity;
import SWD.NET1704.entities.TransactionEntity;
import SWD.NET1704.repositories.BookingRepository;
import SWD.NET1704.repositories.ProductRepository;
import SWD.NET1704.repositories.TransactionRepository;
import SWD.NET1704.repositories.UserRepository;
import SWD.NET1704.utils.Constants;
import SWD.NET1704.utils.EntityToDTOMapper;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static SWD.NET1704.utils.Constants.*;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ProductRepository productRepository;
    private final EntityToDTOMapper entityToDTOMapper;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    @Override
    public ResponseEntity<ApiResponse> getAllBooking() {
        try {
            List<BookingEntity> bookingEntityList = bookingRepository.findBookingEntities();
            if (bookingEntityList == null)
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.NOT_FOUND));
            List<BookingDTO> bookingDTOList = bookingEntityList.stream().map(entityToDTOMapper::mapBookingEntityToDTO).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.SUCCESS, bookingDTOList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getAllBookingOf4thQuarter() {
        try {
            List<BookingEntity> bookingEntityListDecember = bookingRepository.getAllOrderOnDecember();
            List<BookingEntity> bookingEntityListNovember = bookingRepository.getAllOrderOnNovember();
            List<BookingEntity> bookingEntityListOctober = bookingRepository.getAllOrderOnOctober();

            List<BookingDTO> bookingDTOListDecember = bookingEntityListDecember.stream().map(entityToDTOMapper::mapBookingEntityToDTO).collect(Collectors.toList());
            List<BookingDTO> bookingDTOListNovember = bookingEntityListNovember.stream().map(entityToDTOMapper::mapBookingEntityToDTO).collect(Collectors.toList());
            List<BookingDTO> bookingDTOListOctober = bookingEntityListOctober.stream().map(entityToDTOMapper::mapBookingEntityToDTO).collect(Collectors.toList());
            Map<String,List<BookingDTO>> responseData = new HashMap<>();
            responseData.put("December",bookingDTOListDecember);
            responseData.put("November",bookingDTOListNovember);
            responseData.put("October",bookingDTOListOctober);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.SUCCESS, responseData));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getBookingById(int bookingId) {
        try {
            if (bookingRepository.findById(bookingId).isEmpty())
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.NOT_FOUND));
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.SUCCESS,
                    entityToDTOMapper.mapBookingEntityToDTO(bookingRepository.getOne(bookingId))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateBooking(int bookingId, BookingRequest bookingRequest) {
        try {
            if (bookingRepository.findById(bookingId).isEmpty())
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.NOT_FOUND));
            BookingEntity bookingEntity = bookingRepository.getOne(bookingId);

            if (userRepository.findById(bookingRequest.getUserId()).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));
            bookingEntity.setUserEntity(userRepository.getOne(bookingRequest.getUserId()));
            bookingEntity.setPaymentMethod(bookingRequest.getPaymentMethod());
            bookingRepository.save(bookingEntity);
            saveTransaction(bookingRequest, bookingEntity);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.SUCCESS, bookingRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> createNewBooking(BookingRequest bookingRequest) {
        try {
            if (userRepository.findById(bookingRequest.getUserId()).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));
            BookingEntity bookingEntity = new BookingEntity();
            bookingEntity.setUserEntity(userRepository.getOne(bookingRequest.getUserId()));
            bookingEntity.setPaymentMethod(bookingRequest.getPaymentMethod());
            bookingEntity.setOrderDate(new Date(System.currentTimeMillis()));
            bookingEntity.setStatus(STATUS_TRUE);
            bookingRepository.saveAndFlush(bookingEntity);

            saveTransaction(bookingRequest, bookingEntity);

            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.SUCCESS, bookingRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    private void  saveTransaction(BookingRequest bookingRequest, BookingEntity bookingEntity) {

        bookingRequest.getProductList().forEach(orderProductRequest -> {
            ProductEntity productEntity = productRepository.getOne(orderProductRequest.getProductId());
            if (productEntity.getQuantity() >= orderProductRequest.getQuantity()) {
                int quantity =productEntity.getQuantity() - orderProductRequest.getQuantity();
                productEntity.setQuantity(quantity);
                productRepository.save(productEntity);
                TransactionEntity transactionEntity = new TransactionEntity();
                transactionEntity.setQuantity(orderProductRequest.getQuantity());
                transactionEntity.setProductEntity(productEntity);
                transactionEntity.setBookingEntity(bookingEntity);
                transactionRepository.save(transactionEntity);
            }
        });

    }

    @Override
    public ResponseEntity<ApiResponse> getBookingByAccount(int userId) {
        try {
            List<BookingEntity> bookingEntityList = bookingRepository.getBookingByAccountEntity(userId);
            if (bookingEntityList == null)
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.NOT_FOUND));
            List<BookingDTO> bookingDTOList = bookingEntityList.stream().map(entityToDTOMapper::mapBookingEntityToDTO).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.SUCCESS, bookingDTOList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getBookingByProduct(int productId) {
        try {
            List<BookingEntity> bookingEntityList = transactionRepository.getTransactionByProductEntity(productId).
                    stream().map(TransactionEntity::getBookingEntity).collect(Collectors.toList());
            if (bookingEntityList.isEmpty())
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.NOT_FOUND));
            List<BookingDTO> bookingDTOList = bookingEntityList.stream().map(entityToDTOMapper::mapBookingEntityToDTO).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.SUCCESS, bookingDTOList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteBooking(int bookingId) {
        try {
            if (bookingRepository.findById(bookingId).isEmpty())
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.NOT_FOUND));
            BookingEntity bookingEntity = bookingRepository.getOne(bookingId);
            bookingEntity.setStatus(STATUS_FALSE);
            bookingRepository.save(bookingEntity);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }
}
