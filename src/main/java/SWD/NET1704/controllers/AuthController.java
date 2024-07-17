package SWD.NET1704.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import SWD.NET1704.dtos.UserPrincipal;
import SWD.NET1704.dtos.request.LoginRequest;
import SWD.NET1704.dtos.request.UserRegisterPayload;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.dtos.response.JwtAuthenticationResponse;
import SWD.NET1704.jwts.JwtTokenProvider;
import SWD.NET1704.repositories.RoleRepository;
import SWD.NET1704.repositories.UserRepository;
import SWD.NET1704.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

import static SWD.NET1704.utils.Constants.LOGIN_ERROR;
import static SWD.NET1704.utils.Constants.STATUS_FALSE;

@RestController
@RequestMapping("/zoo-server/api/v1/auth")
public class AuthController {
    final
    AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    final UserService userService;
    final
    UserRepository userRepository;
    final
    RoleRepository roleRepository;
    final
    PasswordEncoder passwordEncoder;
    final
    JwtTokenProvider tokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            //set authentication to security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // generate token
            String jwt = tokenProvider.generateToken(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            List<String> roles = userPrincipal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt,
                    userPrincipal.getId(),
                    userPrincipal.getUsername()
                    , roles.get(0)));
        } catch (Exception e) {
            logger.error(LOGIN_ERROR);
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, LOGIN_ERROR));
        }
    }

    @PostMapping("/registerNewUser")
    public  ResponseEntity<?> registerNewUser(@RequestBody UserRegisterPayload userRegisterPayload) {
        return userService.registerNewUser(userRegisterPayload);
    }
    @GetMapping("/forgotPassword/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable("email") String email)  {
        return userService.forgotPassword(email);
    }
}
