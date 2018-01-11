package com.supply.management.module.cart.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
 
import com.supply.entity.po.CartDetailPo;
import com.supply.entity.po.CartPo;
import com.supply.entity.po.ProductPo;
import com.supply.exception.SupplyException;
import com.supply.management.module.cart.repository.CartRepository;
import com.supply.management.module.cart.service.CartService;
import com.supply.management.module.product.repository.ProductRepository;
import com.supply.management.module.store.repository.StoreRepository;
import com.supply.management.module.user.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService
{
	private CartRepository mCartRepository;

	private ProductRepository mProductRepository;

	private UserRepository mUserRepository;

	private StoreRepository mStoreRepository;

	@Override
	public void addCart(CartPo cartPo)
	{
		long userId = cartPo.getUserId();
		List<CartDetailPo> cartDetails = cartPo.getDetails();
		for (CartDetailPo cartDetailPo : cartDetails)
		{
			int rows = mCartRepository.save(userId, cartDetailPo.getProductId(), cartDetailPo.getProductNum());
			if (rows != 1)
			{
				throw new SupplyException("创建购物车失败");
			}
		}
		
//		CartPo exist = mCartRepository.findByUserId(cartPo.getUserId());
//		if (exist == null)
//		{
//			int rows = mCartRepository.save(cartPo);
//			if (rows != 1)
//			{
//				throw new SupplyException("创建购物车失败");
//			}
//		}
//		else
//		{
//			exist
//		}
	}

	@Override
	public CartPo findMyCart(long userId)
	{
		Map<Object, Object> map = mCartRepository.findByUserId(userId);
		CartPo cartPo = new CartPo();
		cartPo.setUserId(userId);
		Set<Long> ids = new HashSet<>();
		for (Object key : map.keySet())
		{
			ids.add((Long)key);
		}
		//查询购物车中的商品详情
		List<ProductPo> products = mProductRepository.findByIds(ids);
		if (products == null || products.size() != ids.size())
		{
			throw new SupplyException("购物车中商品已失效,请重新选择商品");
		}
		List<CartDetailPo> details = new ArrayList<>();
		for (ProductPo product : products)
		{
			long productId = product.getId();
			CartDetailPo detailPo = new CartDetailPo();
			detailPo.setProductId(productId);
			detailPo.setProductName(product.getProductName());
			detailPo.setUnitPrice(product.getProductPrice());
			detailPo.setProductUnit(product.getProductUnit());
			detailPo.setProductNum((Long)map.get(new Long(productId)));
			details.add(detailPo);
		}
		cartPo.setDetails(details);
		return cartPo;
	}

	
	@Override
	public CartDetailPo updateCart(long userId, long productId, long productNum)
	{
		if (productNum > 0)
		{
			int rows = mCartRepository.update(userId, productId, productNum);
			if (rows != 1)
			{
				throw new SupplyException("更新商品数量失败");
			}
		}
		else 
		{
			int rows = mCartRepository.remove(userId, productId);
			if (rows != 1)
			{
				throw new SupplyException("更新商品数量失败");
			}
		}
		CartDetailPo cartDetailPo = new CartDetailPo();
		cartDetailPo.setProductId(productId);
		cartDetailPo.setProductNum(productNum);
		
		return cartDetailPo;
	}

	@Override
	public void deleteCart(long userId, long productId)
	{
		if (1 != mCartRepository.remove(userId, productId))
		{
			throw new SupplyException("删除商品失败");
		}
	}

	@Override
	public void clearCart(long userId)
	{
		if (1 != mCartRepository.remove(userId))
		{
			throw new SupplyException("清空购物车失败");
		}
	}
	
	@Resource(name = "cartRepository")
	public void setCartRepository(CartRepository cartRepository)
	{
		this.mCartRepository = cartRepository;
	}

	@Resource(name = "productRepository")
	public void setProductRepository(ProductRepository productRepository)
	{
		this.mProductRepository = productRepository;
	}

	@Resource(name = "userRepository")
	public void setUserRepository(UserRepository userRepository)
	{
		this.mUserRepository = userRepository;
	}

	@Resource(name = "storeRepository")
	public void setStoreRepository(StoreRepository storeRepository)
	{
		this.mStoreRepository = storeRepository;
	}

	

}
