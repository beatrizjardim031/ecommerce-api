package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.*;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ShoppingCartController {
    // a shopping cart controller depends on the service layer
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    @GetMapping
    public ShoppingCart getCart(Principal principal) {
        // get the currently logged-in username
        String userName = principal.getName();
        // find database user by username
        User user = userService.getByUserName(userName);
        int userId = user.getId();
        // use the shoppingCartService to get all items in the cart and return the cart
        return shoppingCartService.getByUserId(userId);
    }

    @PostMapping("/products/{productsId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCart addToCart(@PathVariable int productsId, Principal principal) {
        String username = principal.getName();

        User user = userService.getByUserName(username);
        int userId = user.getId();
        shoppingCartService.addProduct(userId, productsId);
        return shoppingCartService.getByUserId(userId);
    }
    // return the updated cart with status 201 Created


    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15  (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated; return the cart (200 OK)


    @DeleteMapping
    public ShoppingCart deleteCart (Principal principal) {
        String username = principal.getName();

        User user = userService.getByUserName(username);
        int userId = user.getId();
        shoppingCartService.deleteCart(userId);
        return shoppingCartService.getByUserId(userId);
    }

}
