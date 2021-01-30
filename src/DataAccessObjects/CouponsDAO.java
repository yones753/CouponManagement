package DataAccessObjects;

import java.util.ArrayList;
import JavaBeans.Coupon;

public interface CouponsDAO {

	public void addCoupon(Coupon coupon);
	public void updateCoupon(Coupon coupon);
	public void deleteCoupon(int couponID);
	public ArrayList<Coupon> getAllCoupons();
	public Coupon getOneCoupon(int couponID); 
	public void addCouponPurchase(int customerID ,int couponID);	
	public void deleteCouponPurchase(int customerID ,int couponID);
	
	public void deletePurchasedCoupon(int couponId) ;
	public int getCouponIdByCompanyIDandTitle(int companyID , String title);
	ArrayList<Coupon> getAllCouponsByCompany(int companyID);
	public void deleteAllPurByCouponId(int couponID);
	public void deleteAllPurByCustomerId(int customerID);
}


