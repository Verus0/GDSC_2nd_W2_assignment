package com.example.gdsc_2nd_w1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final FoodService foodService;


    public OrderController(OrderService orderService, UserService userService, FoodService foodService) {
        this.orderService = orderService;
        this.userService = userService;
        this.foodService = foodService;
    }

    @PostMapping("/AddOrder")
    public ResponseEntity<?> addOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            String normalizedPhoneNum = orderService.normalizePhoneNumber(orderRequestDTO.getPhoneNum());
            orderRequestDTO.setPhoneNum(normalizedPhoneNum);

            if (orderRequestDTO.getUserName() == null || orderRequestDTO.getUserName().isEmpty()) {
                return new ResponseEntity<>(Map.of("message", "사용자 이름은 필수입니다."), HttpStatus.BAD_REQUEST);
            }
            if (orderRequestDTO.getPhoneNum() == null || orderRequestDTO.getPhoneNum().isEmpty()) {
                return new ResponseEntity<>(Map.of("message", "휴대폰 번호는 필수입니다."), HttpStatus.BAD_REQUEST);
            }
            if (orderRequestDTO.getFoodName() == null || orderRequestDTO.getFoodName().isEmpty()) {
                return new ResponseEntity<>(Map.of("message", "음식 이름은 필수입니다."), HttpStatus.BAD_REQUEST);
            }
            if (orderRequestDTO.getQuantity() == null || orderRequestDTO.getQuantity() <= 0) {
                return new ResponseEntity<>(Map.of("message", "수량은 1 이상이어야 합니다."), HttpStatus.BAD_REQUEST);
            }

            r_Order savedOrder = orderService.saveOrder(orderRequestDTO);

            return new ResponseEntity<>(Map.of("message", "주문이 저장되었습니다."), HttpStatus.CREATED);

        }
        catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "주문 저장 중 오류가 발생했습니다. " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }










    @GetMapping("/search")
    public String searchPage() {
        return "orderSearch";
    }

    @PostMapping("/search")
    public String orderList(@RequestParam("username") String username, Model model) {
        try {
            List<OrderResponseDTO> orderDTOList = orderService.orderFindByUserName(username);

            System.out.println("Returned orderDTOList: " + orderDTOList);

            if (!orderDTOList.isEmpty()) {
                model.addAttribute("orders", orderDTOList);
            }
            else {
                model.addAttribute("message", "해당 사용자의 주문이 없습니다.");
            }
        }
        catch (Exception e) {
            model.addAttribute("message", "주문 조회 중 오류가 발생했습니다: " + e.getMessage());
        }

        return "orderList";
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Integer id, @RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            r_Order updatedOrder = orderService.orderUpdate(id, orderRequestDTO);

            return ResponseEntity.ok(Map.of("message", "주문이 변경되었습니다.", "order", updatedOrder));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "주문 수정 중 오류가 발생했습니다.", "error", e.getMessage()));
        }
    }




    @RequestMapping ("/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id, Model model) {
        try {
            orderService.orderDelete(id);

            return new ResponseEntity<>("주문이 성공적으로 삭제되었습니다.", HttpStatus.OK);
        }
        catch (Exception e) {
            model.addAttribute("errorMessage", "주문 삭제 중 오류가 발생했습니다. " + e.getMessage());

            return new ResponseEntity<>("주문 삭제 중 오류가 발생했습니다. " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
