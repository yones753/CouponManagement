package Job;

import java.util.ArrayList;

import DataAccessObjects.CouponsDAO;
import DataAccessObjects.CouponsDBDAO;
import JavaBeans.Coupon;


public class CouponExpiraitionDailyJob implements Runnable {
	
	
	private boolean keepRunning = true;
	private CouponsDAO couponsDAO;
	
	public CouponExpiraitionDailyJob() {

		couponsDAO = new CouponsDBDAO();
	}
	
	public void stop() {
		keepRunning = false;
	}
	
	
	public synchronized void deleteExpiraiedCoupon() {
		
		System.out.println("deleteExpiraiedCoupon");
		ArrayList<Coupon> coupons = couponsDAO.getAllCoupons();
		
		long millis=System.currentTimeMillis(); 
		java.sql.Date NowDate = new java.sql.Date(millis);

		System.out.println("coupon: "+ coupons.size());
		for (int i = 0; i < coupons.size(); i++)
		{
			if(coupons.get(i).getEndDate().before(NowDate))
			{
				Coupon currCoupon = coupons.get(i);
				couponsDAO.deletePurchasedCoupon(currCoupon.getId());
				couponsDAO.deleteCoupon(currCoupon.getId());
				System.out.println("delete coupon: "+currCoupon.getId());
			}
		}
		stop();

	}
	
	@Override
	public void run() {

		while(keepRunning) {
				deleteExpiraiedCoupon();
		}
	}
	
	
	
}
