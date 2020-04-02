package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Param;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartMapper {
    public List<CartItem> getCartItemList(String username);

    public void updateCart(@Param ("cartItem") CartItem cartItem,@Param ("account") Account account);

    public void addItemToCart(@Param ("account") Account account,@Param ("cartItem") CartItem cartItem);

    public void removeCart(Account account);

    public void removeItemFromCart(@Param("account") Account account,@Param ("itemId") String itemId);
}
