package notworry.rj.edu.notworry.Entity;

import java.sql.Timestamp;


public class Orders {
	private int orderId;
	private int ispay;
	private Timestamp riseTime;
	private Users users;
	private Orderdetails orderdetails;
	private Spots spot;


	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getIspay() {
		return ispay;
	}

	public void setIspay(int ispay) {
		this.ispay = ispay;
	}

	public Timestamp getRiseTime() {
		return riseTime;
	}

	public void setRiseTime(Timestamp riseTime) {
		this.riseTime = riseTime;
	}
	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Orderdetails getOrderdetails() {
		return orderdetails;
	}

	public void setOrderdetails(Orderdetails orderdetails) {
		this.orderdetails = orderdetails;
	}

	public Spots getSpot() {
		return spot;
	}

	public void setSpot(Spots spot) {
		this.spot = spot;
	}
}
