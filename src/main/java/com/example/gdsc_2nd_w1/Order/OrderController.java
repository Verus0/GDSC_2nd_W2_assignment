package com.example.gdsc_2nd_w1.Order;

import com.example.gdsc_2nd_w1.Login.LoginRequestDTO;
import com.example.gdsc_2nd_w1.Login.LoginResponseDTO;
import com.example.gdsc_2nd_w1.Login.LoginService;
import com.example.gdsc_2nd_w1.User.User;
import com.example.gdsc_2nd_w1.User.UserRequestDTO;
import com.example.gdsc_2nd_w1.User.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class OrderController {
    private final static String LOGIN_SESSION_KEY = "yr_approved";
    private final OrderService orderService;
    private final LoginService loginService;
    private final UserService userService;
    private final HttpSession session;


    @Autowired
    public OrderController(OrderService orderService, LoginService loginService, UserService userService, HttpSession session) {
        this.orderService = orderService;
        this.loginService = loginService;
        this.userService = userService;
        this.session = session;
    }


    @GetMapping("/login-form")
    public String loginPage() {
        return "LoginPage";
    }


    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO.DTO user = loginService.login(loginRequestDTO);
        session.setAttribute(LOGIN_SESSION_KEY, user);
        return "redirect:/orders";
    }


    @GetMapping("/signup-form")
    public String signupPage() {
        return "SignupPage";
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            User newUser = userService.createUser(userRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "회원가입이 완료되었습니다.", "user", newUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "회원가입 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }


    @PostMapping("/logout")
    public String logout(HttpSession session) {
        ResponseEntity<String> response = loginService.logout(session);

        if (response.getStatusCode() == HttpStatus.OK) {
            return "redirect:/login-form";
        }
        else {

            return "redirect:/orders/addition";  // 로그아웃 실패시 orders/addition으로 보내기
        }
    }


    @GetMapping("/orders")
    public String addOrderPage(){
        return "OrderAddPage";
    }


    @PostMapping("/orders/addition")
    public ResponseEntity<?> addOrder(@RequestBody OrderRequestDTO.DTO orderRequestDTO) {
        LoginResponseDTO.DTO sessionUser = (LoginResponseDTO.DTO) session.getAttribute("yr_approved");

        if (sessionUser.getUserId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        try {
            r_Order savedOrder = orderService.saveOrder(orderRequestDTO, sessionUser.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "주문이 저장되었습니다.", "order", savedOrder));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "주문 저장 중 오류가 발생했습니다.", "error", e.getMessage()));
        }
    }


    @GetMapping("/orders/list")
    public String getUserOrders(HttpSession session, Model model) {
        LoginResponseDTO.DTO sessionUser = (LoginResponseDTO.DTO) session.getAttribute("yr_approved");

        if (sessionUser.getUserId() == null) {
            model.addAttribute("message", "로그인이 필요한 페이지입니다.");
        }

        List<OrderResponseDTO> orders = orderService.orderFindByUserId(sessionUser.getUserId());

        model.addAttribute("orders", orders);

        return "OrderListPage";
    }


    @PutMapping("/orders/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Integer id, @RequestBody OrderRequestDTO.DTO orderRequestDTO, Model model) {
        LoginResponseDTO.DTO sessionUser = (LoginResponseDTO.DTO) session.getAttribute("yr_approved");

        if (sessionUser.getUserId() == null) {
            model.addAttribute("message", "로그인이 필요한 페이지입니다.");
        }

        try {
            r_Order updatedOrder = orderService.orderUpdate(id, orderRequestDTO, sessionUser.getUserId());
            return ResponseEntity.ok(Map.of("message", "주문이 변경되었습니다.", "order", updatedOrder));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "주문 수정 중 오류가 발생했습니다.", "error", e.getMessage()));
        }
    }


    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id) {
        LoginResponseDTO.DTO sessionUser = (LoginResponseDTO.DTO) session.getAttribute("yr_approved");

        if (sessionUser.getUserId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        try {
            orderService.orderDelete(id, sessionUser.getUserId());
            return ResponseEntity.ok(Map.of("message", "주문이 성공적으로 삭제되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "주문 삭제 중 오류가 발생했습니다.", "error", e.getMessage()));
        }
    }
}