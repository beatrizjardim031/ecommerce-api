package org.yearup.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService {
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId) {
        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);
        ShoppingCart shoppingCart = new ShoppingCart();

        for(CartItem cartItem : cartItems) {
            Product product = productService.getById(cartItem.getProductId());
            cartItem.setProduct(product);
            shoppingCart.add(cartItem);
        }

        return shoppingCart;
    }

    public void addProduct(int userId, int productId) {
        CartItem existing = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        if (existing == null) {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(1);

            shoppingCartRepository.save(cartItem);

        } else {
            existing.setQuantity(existing.getQuantity() + 1);
            shoppingCartRepository.save(existing);
        }
    }

    public void updateQuantity(int userId, int productId, int quantity) {
        CartItem existing = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        if (existing != null) {
            existing.setQuantity(quantity);

            shoppingCartRepository.save(existing);
        }
    }



    @Transactional
    public ShoppingCart deleteCart(int userId) {
        shoppingCartRepository.deleteByUserId(userId);
        return null;
    }
}
