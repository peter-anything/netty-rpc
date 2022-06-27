package com.peter.netty.rpc.client.route.impl;

import com.peter.netty.rpc.client.handler.RpcClientHandler;
import com.peter.netty.rpc.client.route.RpcLoadBalance;
import com.peter.netty.rpc.protocol.RpcProtocol;

import java.util.*;

/**
 * Random load balance
 * Created by luxiaoxun on 2020-08-01.
 */
public class RpcLoadBalanceRandom extends RpcLoadBalance {
    private Random random = new Random();

    public RpcProtocol doRoute(List<RpcProtocol> addressList) {
        int size = addressList.size();
        // Random
        return addressList.get(random.nextInt(size));
    }

    @Override
    public RpcProtocol route(String serviceKey, Map<RpcProtocol, RpcClientHandler> connectedServerNodes) throws Exception {
        Map<String, List<RpcProtocol>> serviceMap = getServiceMap(connectedServerNodes);
        List<RpcProtocol> addressList = serviceMap.get(serviceKey);
        if (addressList != null && addressList.size() > 0) {
            return doRoute(addressList);
        } else {
            throw new Exception("Can not find connection for service: " + serviceKey);
        }
    }
}
