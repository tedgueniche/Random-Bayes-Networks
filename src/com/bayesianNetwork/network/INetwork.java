package com.bayesianNetwork.network;

import java.util.List;


public interface INetwork {

	public void processCase(Condition condition);
	public void processAllCases(List<Condition> conditions);
	public Double prob(Condition condition);
}
