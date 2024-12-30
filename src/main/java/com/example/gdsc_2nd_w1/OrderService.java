package com.example.gdsc_2nd_w1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
    public r_Order saveOrder(OrderRequestDTO orderRequestDTO) {
        String userName = orderRequestDTO.getUserName();
        String phoneNum = orderRequestDTO.getPhoneNum();
        String foodName = orderRequestDTO.getFoodName();
        int quantity = orderRequestDTO.getQuantity();

        User user = userService.createUser(userName, phoneNum);

        Optional<Food> optionalFood = foodService.findFoodByFoodName(foodName);
        Food food = optionalFood.orElseThrow(() -> new IllegalArgumentException("해당 음식을 찾을 수 없습니다."));

        r_Order order = r_Order.builder()
                .user(user)
                .food(food)
                .quantity(quantity)
                .total(food.getFoodPrice() * quantity)
                .build();

        return orderRepository.save(order);
    }

    public String normalizePhoneNumber(String phoneNum) {
        return phoneNum.replace("-", "");  // 하이픈 제거해줌
    }



    public List<OrderResponseDTO> orderFindByUserName(String userName) {
        try {
            List<r_Order> orders = orderRepository.findByUser_UserName(userName);

            if (orders == null) {
                return Collections.emptyList();
            }

            return orders.stream()
                    .map(OrderResponseDTO::new)
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            System.err.println("주문 조회 중 오류 발생: " + e.getMessage());
            return Collections.emptyList();
        }
    }


    public r_Order orderFindById(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
    }

    @Transactional
    public r_Order orderUpdate(Integer id, OrderRequestDTO orderRequestDTO) {
        r_Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        Food food = order.getFood();

        order.setFood(food);
        order.setQuantity(orderRequestDTO.getQuantity());
        order.setTotal(food.getFoodPrice() * orderRequestDTO.getQuantity());

        return orderRepository.save(order);
    }


    @Transactional
    public void orderDelete(Integer id) {
        r_Order order = orderFindById(id);
        orderRepository.delete(order);
    }
}
