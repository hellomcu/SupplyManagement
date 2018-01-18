package com.supply.management.module.order.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supply.contant.OrderStatus;
import com.supply.entity.PageInfo;
import com.supply.entity.po.OrderDetailPo;
import com.supply.entity.po.OrderPo;
import com.supply.entity.po.ProductPo;
import com.supply.entity.po.StorePo;
import com.supply.exception.SupplyException;
import com.supply.management.module.cart.repository.CartRepository;
import com.supply.management.module.order.repository.OrderRepository;
import com.supply.management.module.order.service.OrderService;
import com.supply.management.module.product.repository.ProductRepository;
import com.supply.management.module.store.repository.StoreRepository;

@Service
public class OrderServiceImpl implements OrderService
{
	private OrderRepository mOrderRepository;
	
	private ProductRepository mProductRepository;
	
	private StoreRepository mStoreRepository;
	
	private CartRepository mCartRepository;
	
	@Override
	public PageInfo<OrderPo> findOrders(PageInfo page, OrderStatus status)
	{
		PageInfo<OrderPo> result = new PageInfo<>();
		List<OrderPo> list = mOrderRepository.findAll(page, status);
		long count = mOrderRepository.count(status);
		result.setList(list);
		result.setTotalNum(count);
		result.setItemNum(page.getItemNum());
		result.setTotalPage(result.calcTotalPage());
		result.setCurrentPage(page.getCurrentPage());
		return result;
	}

	@Override
	public void updateOrderStatus(OrderStatus status, long id)
	{
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("order_status", status.ordinal());
		int effectedRows = mOrderRepository.update(fields, id);
		if (effectedRows != 1)
		{
			throw new SupplyException("更新订单状态失败,请稍后重试");
		}
		
	}
	
	@Transactional
	@Override
	public void createOrder(OrderPo order, List<OrderDetailPo> detailParams)
	{
		createOneOrder(order, detailParams);
	}
	
	@Transactional
	@Override
	public void createOrder(Set<Long> storeIds, List<OrderDetailPo> detailParams)
	{
		List<StorePo> stores = mStoreRepository.findByIds(storeIds);
		if (stores.size() != storeIds.size())
		{
			throw new SupplyException("下单失败,门店不再存在");
		}
		for (StorePo storePo : stores)
		{
			createOneOrder(storePo, detailParams);
		}
		//扣款
		updateStoreBalance(stores);
		
		//TODO 清空购物车
	}
	
	private void createOneOrder(StorePo storePo, List<OrderDetailPo> detailParams)
	{
		Set<Long> productIds = new HashSet<>();
		for (OrderDetailPo detail : detailParams)
		{
			long productId = detail.getProductId();
			productIds.add(productId);
		}
		// 根据商品id查询所购买的商品
		List<ProductPo> myProducts = mProductRepository.findByIds(productIds);
		int num = myProducts.size();
		if (myProducts == null || myProducts.size() != num)
		{
			throw new SupplyException("下单失败,购买的产品已下架");
		}
		
		//计算订单总价，计算商品总数，检查库存,设置订单详情的商品信息,商品剩余数量
		OrderPo tmp = processOrderProduct(detailParams, myProducts);
		BigDecimal orderTotalPrice = tmp.getTotalPrice();
		BigDecimal storeBalance = storePo.getBalance().subtract(orderTotalPrice);
		if (storeBalance.compareTo(new BigDecimal("0.00")) == -1)
		{
			throw new SupplyException(String.format("下单失败,门店[%s]的余额不足", storePo.getStoreName()));
		}
		storePo.setBalance(storeBalance);
		OrderPo order = new OrderPo();
		order.setStoreId(storePo.getId());
		order.setReceiver(storePo.getContacts());
		order.setContacts(storePo.getContactWay());
		order.setReceivingAddress(storePo.getStorePlace());
		order.setProductNum(num);
		order.setTotalPrice(tmp.getTotalPrice());
		order.setTotalNum(tmp.getTotalNum());
		order.setOrderStatus(OrderStatus.STATUS_RECEIVED);
		// 保存订单信息
		int row = mOrderRepository.save(order);
		if (row != 1)
		{
			throw new SupplyException("创建订单失败,请稍后重试");
		}
		
		//保存订单明细信息
		int rows[] = mOrderRepository.saveDetails(order.getId(), detailParams);
		if (rows == null || rows.length != num)
		{
			throw new SupplyException("创建订单失败,请稍后重试");
		}

		// 修改库存
		updateProductStock(tmp.getDetails());
	}
	
	
	private void createOneOrder(OrderPo order, List<OrderDetailPo> detailParams)
	{
		Set<Long> productIds = new HashSet<>();
		for (OrderDetailPo detail : detailParams)
		{
			long productId = detail.getProductId();
			productIds.add(productId);
		}
		// 根据商品id查询所购买的商品
		List<ProductPo> myProducts = mProductRepository.findByIds(productIds);
		int num = myProducts.size();
		if (myProducts == null || myProducts.size() != num)
		{
			throw new SupplyException("下单失败,购买的产品已下架");
		}
		
		//计算订单总价，计算商品总数，检查库存,设置订单详情的商品信息,商品剩余数量
		OrderPo tmp = processOrderProduct(detailParams, myProducts);
		
		order.setProductNum(num);
		order.setTotalPrice(tmp.getTotalPrice());
		order.setTotalNum(tmp.getTotalNum());
		order.setOrderStatus(OrderStatus.STATUS_UNDER);
		// 保存订单信息
		int row = mOrderRepository.save(order);
		if (row != 1)
		{
			throw new SupplyException("创建订单失败,请稍后重试");
		}
		
		//保存订单明细信息
		int[] rows = mOrderRepository.saveDetails(order.getId(), detailParams);
		if (rows == null || rows.length != num)
		{
			throw new SupplyException("创建订单失败,请稍后重试");
		}

		// 修改库存
		updateProductStock(tmp.getDetails());
	}
	

	private void updateProductStock(List<ProductPo> products) throws SupplyException
	{
		if (products == null || products.isEmpty())
		{
			throw new SupplyException("更新库存失败");
		}
		int[] rows = mProductRepository.updateNum(products);
		if (rows == null || rows.length != products.size())
		{
			throw new SupplyException("更新库存失败");
		}
	}
	
	private void updateStoreBalance(List<StorePo> stores)
	{
		int[] rows = mStoreRepository.updateBalance(stores);
		if (rows == null || rows.length != stores.size())
		{
			throw new SupplyException("扣款失败");
		}
	}
	
	/**
	 * 处理订单商品包括计算订单总价，计算商品总数，检查库存,设置订单详情的商品信息
	 * 
	 * @param productParams
	 *            前台传过来的商品参数，包含商品id和购买数量
	 * @param myProducts
	 *            实际从库中查询的商品详情
	 */
	private OrderPo processOrderProduct(List<OrderDetailPo> productParams, List<ProductPo> myProducts)
			throws SupplyException
	{
		BigDecimal totalPrice = new BigDecimal("0.00");
		int totalNum = 0;
		for (ProductPo productItem : myProducts)
		{
			OrderDetailPo detailPo = getOrderDetailByProductId(productParams, productItem.getId());
			if (detailPo == null)
			{
				throw new SupplyException("下单失败,请重试");
			}
			int buyNum = detailPo.getProductNum();
			int actuallyNum = productItem.getProductNum();
			if (buyNum > actuallyNum)
			{
				throw new SupplyException(
						String.format("下单失败,您购买的 [%s] 库存只剩 %d 件", productItem.getProductName(), actuallyNum));
			}
			productItem.setProductNum(actuallyNum - buyNum);
			detailPo.setProductName(productItem.getProductName());
			detailPo.setProductUnit(productItem.getProductUnit());
			BigDecimal productPrice = productItem.getProductPrice();
			detailPo.setUnitPrice(productPrice);
			totalPrice = totalPrice.add(productPrice.multiply(new BigDecimal(buyNum)));
			totalNum += buyNum;
		}
		OrderPo order = new OrderPo();
		order.setTotalPrice(totalPrice);
		order.setTotalNum(totalNum);
		order.setDetails(myProducts);
		return order;
	}
	
	
	/**
	 * 根据商品id从List中获取所对应的商品信息(购买数量)
	 * @param productParams
	 * @param productId
	 * @return
	 */
	private OrderDetailPo getOrderDetailByProductId(List<OrderDetailPo> productParams, long productId)
	{
		for (OrderDetailPo detailPo : productParams)
		{
			if (productId == detailPo.getProductId())
			{
				return detailPo;
			}
		}
		return null;
	}
	
	@Override
	public List<OrderDetailPo> findOrderDetail(long orderId)
	{
		return mOrderRepository.findOrderDetail(orderId);
	}
	
	@Resource(name="orderRepository")
	public void setOrderRepository(OrderRepository orderRepository)
	{
		this.mOrderRepository = orderRepository;
	}
	
	@Resource(name="productRepository")
	public void setProductRepository(ProductRepository productRepository)
	{
		this.mProductRepository = productRepository;
	}
	
	@Resource(name="storeRepository")
	public void setStoreRepository(StoreRepository storeRepository)
	{
		this.mStoreRepository = storeRepository;
	}
	
	@Resource(name="cartRepository")
	public void setCartRepository(CartRepository cartRepository)
	{
		this.mCartRepository = cartRepository;
	}

	
	
}
