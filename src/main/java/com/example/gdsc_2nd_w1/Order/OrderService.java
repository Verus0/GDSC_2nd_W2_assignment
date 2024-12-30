package com.example.gdsc_2nd_w1.Order;

import com.example.gdsc_2nd_w1.Food.Food;
import com.example.gdsc_2nd_w1.Food.FoodService;
import com.example.gdsc_2nd_w1.User.User;
import com.example.gdsc_2nd_w1.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final FoodService foodService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserService userService, FoodService foodService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.foodService = foodService;
    }

    @Transactional
    public r_Order saveOrder(OrderRequestDTO.DTO orderRequestDTO, Integer userId) {

        Optional<Food> optionalFood = foodService.findFoodByFoodName(orderRequestDTO.getFoodName());
        Food food = optionalFood.orElseThrow(() -> new IllegalArgumentException("해당 음식을 찾을 수 없습니다."));

        User user = userService.findUserById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));


        r_Order order = r_Order.builder()
                .user(user)
                .food(food)
                .quantity(orderRequestDTO.getQuantity())
                .total(food.getFoodPrice() * orderRequestDTO.getQuantity())
                .build();

        return orderRepository.save(order);
    }

    public List<OrderResponseDTO> orderFindByUserId(Integer id) {
        List<r_Order> orders = orderRepository.findByUser_Id(id);
        return orders.stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public r_Order orderUpdate(Integer id, OrderRequestDTO.DTO orderRequestDTO, Integer userId) {
        r_Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));


        if (!order.getUser().getId().equals(userId)) {              // 권한 체크 -> 사용자가 자신의 주문만 수정할 수 있도록 만듦
            throw new SecurityException("자신의 주문만 수정할 수 있습니다.");
        }


        Food food = foodService.findFoodByFoodName(orderRequestDTO.getFoodName())
                .orElseThrow(() -> new IllegalArgumentException("해당 음식을 찾을 수 없습니다."));


        order.setFood(food);
        order.setQuantity(orderRequestDTO.getQuantity());
        order.setTotal(food.getFoodPrice() * orderRequestDTO.getQuantity());

        return orderRepository.save(order);
    }


    @Transactional
    public void orderDelete(Integer id, Integer userId) {
        r_Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));


        if (!order.getUser().getId().equals(userId)) {              // 권한 체크 -> 사용자가 자신의 주문만 삭제할 수 있도록 만듦
            throw new SecurityException("자신의 주문만 삭제할 수 있습니다.");
        }

        orderRepository.delete(order);
    }
}