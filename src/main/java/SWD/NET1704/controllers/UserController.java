package SWD.NET1704.controllers;

import SWD.NET1704.dtos.request.*;
import SWD.NET1704.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import SWD.NET1704.dtos.request.*;


@RestController
@RequestMapping("/zoo-server/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/getAllTrainers")
    public ResponseEntity<?>getAllTrainers() {
        return userService.getAllTrainers();
    }
    @GetMapping("/getAllStaffs")
    public ResponseEntity<?>getAllStaffs() {
        return userService.getAllStaffs();
    }


    @GetMapping("/getUserById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") int userId) throws Exception {
        return userService.getUserById(userId);
    }



    @PostMapping("/createNewUser")
    public ResponseEntity<?> createNewUser(@RequestBody UserCreatePayload userCreatePayload) {
        return userService.createNewUser(userCreatePayload);
    }

    @PostMapping("/registerNewUser")
    public  ResponseEntity<?> registerNewUser(@RequestBody UserRegisterPayload userRegisterPayload) {
        return userService.registerNewUser(userRegisterPayload);
    }

    @PostMapping("/login")
    public  ResponseEntity<?> login(@RequestBody UserRegisterPayload userRegisterPayload) throws Exception {
        return userService.login(userRegisterPayload);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") int userId, @RequestBody UserUpdatePayload userUpdatePayload) {
        return userService.updateUser(userId, userUpdatePayload);
    }

    @PutMapping("/updateProfile/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable("id") int userId, @RequestBody ProfilePayload profilePayload) {
        return userService.updateProfile(userId, profilePayload);
    }

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<?> changePassword(@PathVariable("id") int userId, @RequestBody ChangePasswordPayload changePasswordPayload) {
        return userService.changePassword(userId, changePasswordPayload);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int userId) {
        return userService.deleteUser(userId);
    }
}
