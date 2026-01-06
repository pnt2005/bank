package com.pnt2005.bank.auth;
import com.pnt2005.bank.enums.UserRole;
import com.pnt2005.bank.model.dto.user.UserRequestDTO;
import com.pnt2005.bank.model.dto.user.UserResponseDTO;
import com.pnt2005.bank.model.entity.UserEntity;
import com.pnt2005.bank.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        //authenticate username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        //get user detail
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        //create jwt
        String accessToken = jwtService.generateToken(userDetails.getUser());
        String refreshToken = jwtService.generateRefreshToken(userDetails.getUser());
        return ResponseEntity.ok(new JwtResponse(
                accessToken,
                refreshToken,
                userDetails.getUsername(),
                userDetails.getUser().getRole().name(),
                userDetails.getUser().getId()));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO readerRequestDTO) {
        UserResponseDTO readerResponseDTO = userService.createUser(readerRequestDTO);
        URI location = URI.create("/readers/" + readerResponseDTO.getId());
        return ResponseEntity.created(location).body(readerResponseDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = userDetails.getUser();

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setId(user.getId());
        userResponseDTO.setRole(UserRole.valueOf(String.valueOf(user.getRole())));
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtService.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = jwtService.extractUsernameFromRefreshToken(refreshToken);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        UserEntity user = ((CustomUserDetails) userDetails).getUser();

        String newAccessToken = jwtService.generateToken(user);
        return ResponseEntity.ok(new JwtResponse(newAccessToken, refreshToken, username, user.getRole().name(), user.getId()));
    }
}
