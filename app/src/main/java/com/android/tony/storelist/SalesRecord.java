package com.android.tony.storelist;

public class SalesRecord {

    private String region,country,itemType,salesChannel,orderPriority,orderDate,orderId,shipDate,unitsSold,unitPrice,unitCost,totalRevenue;
    SalesRecord(String region, String country,String itemType,String salesChannel,String orderPriority,String orderDate,String orderId,String shipDate,String unitsSold,String unitPrice,String unitCost,String totalRevenue)
    {
        this.region = region;
        this.country = country;
        this.itemType = itemType;
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.orderPriority = orderPriority;
        this.salesChannel = salesChannel;
        this.shipDate = shipDate;
        this.unitsSold = unitsSold;
        this.unitPrice = unitPrice;
        this.unitCost = unitCost;
        this.totalRevenue = totalRevenue;
    }
}
