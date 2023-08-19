package com.pavel.socialweb.Controller;

import com.pavel.socialweb.Entity.UserEntity;
import com.pavel.socialweb.Model.JwtRequest;
import com.pavel.socialweb.Model.JwtResponse;
import com.pavel.socialweb.Repository.UserRepository;
import com.pavel.socialweb.Security.UserSecurity;
import com.pavel.socialweb.Service.UserService;
import com.pavel.socialweb.Util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "token")
public class TokenController {
    private static final String TOKEN = "/token";

    private final JwtUtil jwtUtil;

    private final UserSecurity userSecurity;

    private final UserRepository userRepository;


    public TokenController(JwtUtil jwtUtil, UserSecurity userSecurity, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userSecurity = userSecurity;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = TOKEN, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get token from user",
            description = "Get token from user",
            parameters = {@Parameter(name = "username", description = "username for user", example = "test"),
                    @Parameter(name = "password", description = "password for user", example = "Test1230*")},
    responses = @ApiResponse(
            description = "Success",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = JwtResponse.class))
            ))
    public ResponseEntity<?> GetToken(@RequestBody JwtRequest jwtRequest){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        final UserDetails userDetails = userSecurity.loadUserByUsername(jwtRequest.getUsername());
        UserEntity user = userRepository.findByUsername(jwtRequest.getUsername()).orElseThrow();
        final String token = jwtUtil.generateToken(userDetails);
        JwtResponse tokens = new JwtResponse(token);
        return new ResponseEntity(tokens, httpHeaders, HttpStatus.OK);
    }
}
