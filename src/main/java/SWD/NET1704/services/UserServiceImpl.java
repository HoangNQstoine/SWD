package SWD.NET1704.services;

import SWD.NET1704.dtos.request.*;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.request.*;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.dtos.response.UserDTO;
import SWD.NET1704.entities.ProfileEntity;
import SWD.NET1704.entities.RoleEntity;
import SWD.NET1704.entities.UserEntity;
import SWD.NET1704.repositories.RoleRepository;
import SWD.NET1704.repositories.UserRepository;
import SWD.NET1704.utils.EntityToDTOMapper;
import SWD.NET1704.utils.HelperUtil;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static SWD.NET1704.utils.Constants.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EntityToDTOMapper entityToDTOMapper;
    private final RoleRepository roleRepository;
    private final HelperUtil helperUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    @Override
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();
        try {
            List<UserEntity> userEntityList = userRepository.getAllByStatusIsTrue();
            if (!userEntityList.isEmpty()) {
                userEntityList.forEach(userEntity -> userDTOList.add(entityToDTOMapper.mapUserEntityToDTO(userEntity)));
            }
            return ResponseEntity.ok(new ApiResponse(true, SUCCESS, userDTOList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> getAllTrainers() {
        List<UserDTO> userDTOList = new ArrayList<>();
        try {
            List<UserEntity> userEntityList = userRepository.findAllTrainers();
            if (!userEntityList.isEmpty()) {
                userEntityList.forEach(userEntity -> userDTOList.add(entityToDTOMapper.mapUserEntityToDTO(userEntity)));
            }
            return ResponseEntity.ok(new ApiResponse(true, SUCCESS, userDTOList));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> getAllStaffs() {
        List<UserDTO> userDTOList = new ArrayList<>();
        try {
            List<UserEntity> userEntityList = userRepository.findAllStaffs();
            if (!userEntityList.isEmpty()) {
                userEntityList.forEach(userEntity -> userDTOList.add(entityToDTOMapper.mapUserEntityToDTO(userEntity)));
            }
            return ResponseEntity.ok(new ApiResponse(true, SUCCESS, userDTOList));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> getUserById(int userId) {
        try {
            if (userRepository.findById(userId).isEmpty())
                return ResponseEntity.ok(new ApiResponse(true, NOT_FOUND));
            UserEntity userEntity = userRepository.getOne(userId);
            UserDTO userDTO = entityToDTOMapper.mapUserEntityToDTO(userEntity);
            return ResponseEntity.ok(new ApiResponse(true, SUCCESS, userDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> login(UserRegisterPayload userRegisterPayload) {
        try {
            String errorMessage = validateUserCreatePayload(userRegisterPayload.getEmail(), userRegisterPayload.getPassword());
            if (errorMessage != null)
                return ResponseEntity.badRequest().body(new ApiResponse(false, errorMessage));

            String password = Base64.getEncoder().encodeToString(userRegisterPayload.getPassword().getBytes(StandardCharsets.UTF_8));
            UserEntity userEntity = userRepository.findByEmailAndPassword(userRegisterPayload.getEmail(), password);
            if (userEntity == null)
                return ResponseEntity.badRequest().body(new ApiResponse(false, LOGIN_ERROR));

            return ResponseEntity.ok().body(new ApiResponse(true, SUCCESS, entityToDTOMapper.mapUserEntityToDTO(userEntity)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    private String validateUserCreatePayload(String email, String password) {
        if (Strings.isNullOrEmpty(email))
            return EMAIL_NULL_OR_EMPTY_ERROR;
        if (Strings.isNullOrEmpty(password))
            return PASSWORD_NULL_OR_EMPTY_ERROR;
        if (!helperUtil.validateEmail(email))
            return INVALID_EMAIL;
        if (userRepository.existsByEmail(email) != null)
            return EXIST_EMAIL_ERROR;
        return null;
    }

    @Override
    public ResponseEntity<?> createNewUser(UserCreatePayload userCreatePayload) {
        try {

            String errorMessage = validateUserCreatePayload(userCreatePayload.getEmail(), userCreatePayload.getPassword());
            if (errorMessage != null)
                return ResponseEntity.badRequest().body(new ApiResponse(false, errorMessage));
            if (roleRepository.findById(userCreatePayload.getRoleId()).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(false, ROLE_NULL_ERROR));
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(userCreatePayload.getEmail());
            String password = passwordEncoder.encode(userCreatePayload.getPassword());
            userEntity.setPassword(password);
            ProfileEntity profileEntity = new ProfileEntity();
            profileEntity.setFirstName(userCreatePayload.getFirstName());
            profileEntity.setLastName(userCreatePayload.getLastName());
            profileEntity.setPhoneNumber(userCreatePayload.getPhoneNumber());
            profileEntity.setIsDeleted(false);
            RoleEntity roleEntity = roleRepository.findById(userCreatePayload.getRoleId()).get();
            userEntity.setRoleEntity(roleEntity);
            userEntity.setStatus(true);
            userEntity.setProfileEntity(profileEntity);
            userRepository.save(userEntity);
            return ResponseEntity.ok().body(new ApiResponse(true, SUCCESS, userCreatePayload));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }

    }

    @Override
    public ResponseEntity<?> registerNewUser(UserRegisterPayload userRegisterPayload) {
        try {
            String errorMessage = validateUserCreatePayload(userRegisterPayload.getEmail(), userRegisterPayload.getPassword());
            if (errorMessage != null)
                return ResponseEntity.badRequest().body(new ApiResponse(false, errorMessage));
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(userRegisterPayload.getEmail());
            String password = passwordEncoder.encode(userRegisterPayload.getPassword());
            userEntity.setPassword(password);

            RoleEntity roleEntity = roleRepository.getRoleCustomer();
            userEntity.setRoleEntity(roleEntity);
            userEntity.setStatus(true);

            ProfileEntity profileEntity = new ProfileEntity();
            profileEntity.setFirstName(userRegisterPayload.getFirstName());
            profileEntity.setLastName(userRegisterPayload.getLastName());
            profileEntity.setPhoneNumber(userRegisterPayload.getPhoneNumber());
            profileEntity.setIsDeleted(false);
            userEntity.setProfileEntity(profileEntity);
            userRepository.save(userEntity);
            return ResponseEntity.ok().body(new ApiResponse(true, SUCCESS, userRegisterPayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> updateProfile(int userId, ProfilePayload profilePayload) {
        try {
            if (userRepository.findById(userId).isEmpty())
                return ResponseEntity.ok().body(new ApiResponse(true, NOT_FOUND));
            UserEntity userEntity = userRepository.getOne(userId);
            ProfileEntity profileEntity = userEntity.getProfileEntity();
            profileEntity.setFirstName(profilePayload.getFirstName());
            profileEntity.setLastName(profilePayload.getLastName());
            profileEntity.setAddress(profilePayload.getAddress());
            profileEntity.setPhoneNumber(profilePayload.getPhoneNumber());
            profileEntity.setGender(profilePayload.getGender());
            profileEntity.setAvatar(profilePayload.getAvatar());
            profileEntity.setIsDeleted(false);
            userEntity.setProfileEntity(profileEntity);
            userRepository.save(userEntity);
            return ResponseEntity.ok(new ApiResponse(true, SUCCESS, profilePayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> changePassword(int userId, ChangePasswordPayload changePasswordPayload) {
        try {
            if (userRepository.findById(userId).isPresent()) {
                UserEntity userEntity = userRepository.getOne(userId);

                if (passwordEncoder.matches(changePasswordPayload.getCurrentPassword(),userEntity.getPassword())) {
                    String newPassword = passwordEncoder.encode(changePasswordPayload.getNewPassword());
                    userEntity.setPassword(newPassword);
                    userRepository.save(userEntity);
                } else {
                    return ResponseEntity.badRequest().body(new ApiResponse(false, INCORRECT_PASSWORD_ERROR));
                }
            }
            return ResponseEntity.ok(new ApiResponse(true, SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }

    }

    @Override
    public ResponseEntity<?> updateUser(int userId, UserUpdatePayload userUpdatePayload) {
        try {
            if (userRepository.findById(userId).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(false, NOT_FOUND));
            UserEntity userEntity = userRepository.getOne(userId);
            ProfileEntity profileEntity = new ProfileEntity();
            profileEntity.setFirstName(userUpdatePayload.getProfileDTO().getFirstName());
            profileEntity.setLastName(userUpdatePayload.getProfileDTO().getLastName());
            profileEntity.setAddress(userUpdatePayload.getProfileDTO().getAddress());
            profileEntity.setPhoneNumber(userUpdatePayload.getProfileDTO().getPhoneNumber());
            profileEntity.setGender(userUpdatePayload.getProfileDTO().getGender());
            profileEntity.setAvatar(userUpdatePayload.getProfileDTO().getAvatar());
            userEntity.setProfileEntity(profileEntity);
            if (roleRepository.findById(userUpdatePayload.getRoleId()).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(false, ROLE_NULL_ERROR));
            RoleEntity roleEntity = roleRepository.getOne(userUpdatePayload.getRoleId());
            userEntity.setRoleEntity(roleEntity);
            userEntity.setPassword(userUpdatePayload.getPassword());
            userRepository.save(userEntity);
            return ResponseEntity.ok(new ApiResponse(true, SUCCESS, userUpdatePayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> deleteUser(int userId) {
        try {
            if (userRepository.findById(userId).isPresent()) {
                UserEntity userEntity = userRepository.getOne(userId);
                userEntity.setStatus(false);
                userRepository.save(userEntity);
            } else {
                return ResponseEntity.badRequest().body(new ApiResponse(false, NOT_FOUND));
            }
            return ResponseEntity.ok(new ApiResponse(true, SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> forgotPassword(String email) {
        try {
            if (userRepository.existsByEmail(email) == null)
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Email is not exist !!!"));
            UserEntity userEntity = userRepository.existsByEmail(email);
            String randomPassword = RandomStringUtils.randomAlphanumeric(10);
            userEntity.setPassword(passwordEncoder.encode(randomPassword));
            userRepository.save(userEntity);
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(email);
            emailDetails.setSubject("FORGO_PASSWORD");
            String message = " Dear " + userEntity.getProfileEntity().getFirstName() + " " + userEntity.getProfileEntity().getLastName()+ ", \n" + " Your new password is : " + randomPassword;
            emailDetails.setMsgBody(message);
            String result =  emailService.sendSimpleMail(emailDetails);
            return ResponseEntity.ok(new ApiResponse(true,result));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
}
