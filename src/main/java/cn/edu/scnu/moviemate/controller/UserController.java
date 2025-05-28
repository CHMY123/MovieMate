package cn.edu.scnu.moviemate.controller;

import cn.edu.scnu.moviemate.entity.User;
import cn.edu.scnu.moviemate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        boolean success = userService.register(user);
        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("success", true);
            response.put("message", "注册成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "用户名已存在");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        String token = userService.login(username, password);
        
        Map<String, Object> response = new HashMap<>();
        if (token != null) {
            response.put("success", true);
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "用户名或密码错误");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestParam Long userId) {
        User user = userService.getUserInfo(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/upgrade")
    public ResponseEntity<?> upgradeToVip(@RequestParam Long userId) {
        boolean success = userService.upgradeToVip(userId);
        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("success", true);
            response.put("message", "升级成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "升级失败");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
