package com.banking.frombean;

public class UserWithdrawForm {

    private Long userId;
    private double accbalance;

    public UserWithdrawForm() {

    }

	public UserWithdrawForm(Long userId, double accbalance) {
		super();
		this.userId = userId;
		this.accbalance = accbalance;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public double getAccbalance() {
		return accbalance;
	}

	public void setAccbalance(double accbalance) {
		this.accbalance = accbalance;
	}
}