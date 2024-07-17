package SWD.NET1704.services;


import SWD.NET1704.dtos.request.*;
import org.springframework.http.ResponseEntity;
import SWD.NET1704.dtos.request.*;

public interface UserService {
    ResponseEntity<?> getAllUsers();
    ResponseEntity<?> getAllTrainers();
    ResponseEntity<?> getAllStaffs();
    ResponseEntity<?> getUserById(int userId) throws Exception;
    ResponseEntity<?> login(UserRegisterPayload userRegisterPayload) throws Exception;
    ResponseEntity<?> createNewUser(UserCreatePayload userCreatePayload);
    ResponseEntity<?> registerNewUser(UserRegisterPayload userRegisterPayload);
    ResponseEntity<?> updateProfile(int userId, ProfilePayload profilePayload);
    ResponseEntity<?> changePassword(int userId, ChangePasswordPayload changePasswordPayload);
    ResponseEntity<?> updateUser(int userId, UserUpdatePayload userUpdatePayload);
    ResponseEntity<?> deleteUser(int userId);
    ResponseEntity<?> forgotPassword(String email);
}
