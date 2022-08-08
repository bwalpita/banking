package com.banking.frombean;

public class UserBalanceForm {

    private Long userId;
    private double accbalance;

    public UserBalanceForm() {

    }

	public UserBalanceForm(Long userId, double accbalance) {
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