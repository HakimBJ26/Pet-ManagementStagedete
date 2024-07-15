//package com.example.PetgoraBackend.service;
//
//import com.example.PetgoraBackend.dto.ReqRes;
//import com.example.PetgoraBackend.entity.OurUsers;
//import com.example.PetgoraBackend.entity.User;
//import com.example.PetgoraBackend.repository.UsersRepo;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import io.jsonwebtoken.security.SignatureException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Optional;
//
//
//@Service
//public class UsersManagementService {
//
//    @Autowired
//    private UsersRepo usersRepo;
//    @Autowired
//    private JWTUtils jwtUtils;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public ReqRes register(ReqRes registrationRequest) {
//        ReqRes resp;
//
//        try {
//            Optional<User> existingUser = usersRepo.findByEmail(registrationRequest.email());
//            if (existingUser.isPresent()) {
//            }
//            User ourUsers = new User();
//            ourUsers.setEmail(registrationRequest.email());
//            ourUsers.setCity(registrationRequest.city());
//            ourUsers.setRole(registrationRequest.role());
//            ourUsers.setName(registrationRequest.name());
//            ourUsers.setPassword(passwordEncoder.encode(registrationRequest.password()));
//            User ourUsersResult = usersRepo.save(ourUsers);
//            if (ourUsersResult.getId() > 0) {
//                return new ReqRes(200, "User Saved Successfully", null, null, null, null, null, null, null, null, null, ourUsersResult, null);
//            }
//        } catch (Exception e) {
//            return new ReqRes(500, e.getMessage(), null, null, null, null, null, null, null, null, null, null, null);
//        }
//        return new ReqRes(500, "Unknown Error", null, null, null, null, null, null, null, null, null, null, null);
//    }
//
//    public ReqRes login(ReqRes loginRequest){
//        ReqRes response = new ReqRes();
//        try {
//            authenticationManager
//                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
//                            loginRequest.getPassword()));
//            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
//            var jwt = jwtUtils.generateToken(user);
//            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
//             user.setPassword(response.password());
//
//            response.(200);
//            response.setToken(jwt);
//            response.setRole(user.getRole());
//            response.setRefreshToken(refreshToken);
//            response.setExpirationTime("24Hrs");
//            response.setMessage("Successfully Logged In");
//
//        }catch (Exception e){
//            response.setStatusCode(500);
//            response.setMessage(e.getMessage());
//        }
//        return response;
//    }
//
//
//
//
//
//    public String refreshToken(String token) {
//        try {
//            String userEmail = jwtUtils.extractUsername(token);
//            Optional<User> optionalUser = usersRepo.findByEmail(userEmail);
//
//            if (optionalUser.isPresent()) {
//                User user = optionalUser.get();
//
//                if (jwtUtils.isTokenValid(token, user)) {
//                    String newJwt = jwtUtils.generateToken(user);
//                    return "Successfully Refreshed Token";
//                } else {
//                    throw new RuntimeException("Invalid token");
//                }
//            } else {
//                throw new RuntimeException("User not found");
//            }
//        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
//            throw new RuntimeException("Invalid token format or structure");
//        } catch (SignatureException e) {
//            throw new RuntimeException("Invalid token signature");
//        } catch (ExpiredJwtException e) {
//            throw new RuntimeException("Token expired");
//        } catch (Exception e) {
//            throw new RuntimeException("Token refresh failed: " + e.getMessage());
//        }
//    }
//}
//
//
//
//
//
//
//    public ReqRes getAllUsers() {
//        ReqRes reqRes = new ReqRes();
//
//        try {
//            List<User> result = usersRepo.findAll();
//            if (!result.isEmpty()) {
//                reqRes.setOurUsersList(result);
//                reqRes.setStatusCode(200);
//                reqRes.setMessage("Successful");
//            } else {
//                reqRes.setStatusCode(404);
//                reqRes.setMessage("No users found");
//            }
//            return reqRes;
//        } catch (Exception e) {
//            reqRes.setStatusCode(500);
//            reqRes.setMessage("Error occurred: " + e.getMessage());
//            return reqRes;
//        }
//    }
//
//
//    public ReqRes getUsersById(Integer id) {
//        ReqRes reqRes = new ReqRes();
//        try {
//            User usersById = usersRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
//            reqRes.setOurUsers(usersById);
//            reqRes.statusCode(200);
//            reqRes.setMessage("Users with id '" + id + "' found successfully");
//        } catch (Exception e) {
//            reqRes.setStatusCode(500);
//            reqRes.setMessage("Error occurred: " + e.getMessage());
//        }
//        return reqRes;
//    }
//
//
//    public ReqRes deleteUser(Integer userId) {
//        ReqRes reqRes = new ReqRes();
//        try {
//            Optional<User> userOptional = usersRepo.findById(userId);
//            if (userOptional.isPresent()) {
//                usersRepo.deleteById(userId);
//                reqRes.setStatusCode(200);
//                reqRes.setMessage("User deleted successfully");
//            } else {
//                reqRes.setStatusCode(404);
//                reqRes.setMessage("User not found for deletion");
//            }
//        } catch (Exception e) {
//            reqRes.setStatusCode(500);
//            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
//        }
//        return reqRes;
//    }
//
//    public ReqRes updateUser(Integer userId, OurUsers updatedOurUsers) {
//        ReqRes reqRes = new ReqRes();
//        try {
//            Optional<User> userOptional = usersRepo.findById(userId);
//            if (userOptional.isPresent()) {
//                OurUsers existingOurUsers = userOptional.get();
//                existingOurUsers.setEmail(updatedOurUsers.getEmail());
//                existingOurUsers.setName(updatedOurUsers.getName());
//                existingOurUsers.setCity(updatedOurUsers.getCity());
//                existingOurUsers.setRole(updatedOurUsers.getRole());
//
//                if (updatedOurUsers.getPassword() != null && !updatedOurUsers.getPassword().isEmpty()) {
//
//                    existingOurUsers.setPassword(passwordEncoder.encode(updatedOurUsers.getPassword()));
//                }
//
//                OurUsers savedOurUsers = usersRepo.save(existingOurUsers);
//                reqRes.setOurUsers(savedOurUsers);
//                reqRes.setStatusCode(200);
//                reqRes.setMessage("User updated successfully");
//            } else {
//                reqRes.setStatusCode(404);
//                reqRes.setMessage("User not found for update");
//            }
//        } catch (Exception e) {
//            reqRes.setStatusCode(500);
//            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
//        }
//        return reqRes;
//    }
//
//
//    public ReqRes getMyInfo(String email){
//        ReqRes reqRes = new ReqRes();
//        try {
//            Optional<User> userOptional = usersRepo.findByEmail(email);
//            if (userOptional.isPresent()) {
//                reqRes.setOurUsers(userOptional.get());
//                reqRes.setStatusCode(200);
//                reqRes.setMessage("successful");
//            } else {
//                reqRes.setStatusCode(404);
//                reqRes.setMessage("User not found for update");
//            }
//
//        }catch (Exception e){
//            reqRes.setStatusCode(500);
//            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
//        }
//        return reqRes;
//    }
//
//    public ReqRes AdminupdateUser(Integer userId, OurUsers updatedOurUsers) {
//        ReqRes reqRes = new ReqRes();
//        try {
//            Optional<User> userOptional = usersRepo.findById(userId);
//            if (userOptional.isPresent()) {
//                OurUsers existingOurUsers = userOptional.get();
//                existingOurUsers.setEmail(updatedOurUsers.getEmail());
//                existingOurUsers.setName(updatedOurUsers.getName());
//                existingOurUsers.setCity(updatedOurUsers.getCity());
//                existingOurUsers.setRole(updatedOurUsers.getRole());
//
//                if (updatedOurUsers.getPassword() != null && !updatedOurUsers.getPassword().isEmpty()) {
//                    existingOurUsers.setPassword(passwordEncoder.encode(updatedOurUsers.getPassword()));
//                }
//
//                User savedOurUsers = usersRepo.save(existingOurUsers);
//                reqRes.ourUsers(savedOurUsers);
//                reqRes.statusCode(200);
//                reqRes.message("User updated successfully");
//            } else {
//                reqRes.setStatusCode(404);
//                reqRes.setMessage("User not found for update");
//            }
//        } catch (Exception e) {
//            reqRes.setStatusCode(500);
//            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
//        }
//        return reqRes;
//    }
//
//
//}
//
//
