package com.bayesianNetwork.network;


public interface INetwork {

	public void processCase(Condition condition);
	public Double prob(Condition condition);
}
