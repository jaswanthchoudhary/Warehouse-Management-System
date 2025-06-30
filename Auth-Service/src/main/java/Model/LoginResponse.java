package Model;
public class LoginResponse {
 private String token;
 private Long authId;

 public LoginResponse(String token, Long authId) {
     this.token = token;
     this.authId = authId;
 }

 public String getToken() {
     return token;
 }

 public Long getAuthId() {
     return authId;
 }

 public void setToken(String token) {
     this.token = token;
 }

 public void setAuthId(Long authId) {
     this.authId = authId;
 }
}
