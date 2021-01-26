package com.bin.serverapi.order.constant;

import com.bin.serverapi.order.bo.OrderOperTypeBo;
import com.bin.serverapi.order.bo.OrderTypeBo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bin
 * @ClassName OrderTypeConstant
 * @Description TODO
 * @date 2021/1/4 10:01
 */
public class OrderCommonsConstant {
    private static List<OrderTypeBo> orderTypeBoList = new ArrayList<>();
    private static List<OrderOperTypeBo> operTypeBoList = new ArrayList<>();
    static {
        OrderTypeBo orderTypeBo1 = new OrderTypeBo();
        orderTypeBo1.setValue(1);
        orderTypeBo1.setLabel("回收单");
        OrderTypeBo orderTypeBo2 = new OrderTypeBo();
        orderTypeBo2.setValue(2);
        orderTypeBo2.setLabel("出库单");
        OrderTypeBo orderTypeBo3 = new OrderTypeBo();
        orderTypeBo3.setValue(3);
        orderTypeBo3.setLabel("转移单");
        OrderTypeBo orderTypeBo4 = new OrderTypeBo();
        orderTypeBo4.setValue(4);
        orderTypeBo4.setLabel("报废单");
        orderTypeBoList.add(orderTypeBo1);
        orderTypeBoList.add(orderTypeBo2);
        orderTypeBoList.add(orderTypeBo3);
        orderTypeBoList.add(orderTypeBo4);

        OrderOperTypeBo operTypeBo1 = new OrderOperTypeBo();
        operTypeBo1.setValue(1);
        operTypeBo1.setLabel("上井回收");
        OrderOperTypeBo operTypeBo2 = new OrderOperTypeBo();
        operTypeBo2.setValue(2);
        operTypeBo2.setLabel("上井发出");
        OrderOperTypeBo operTypeBo3 = new OrderOperTypeBo();
        operTypeBo3.setValue(3);
        operTypeBo3.setLabel("场地分检");
        OrderOperTypeBo operTypeBo4 = new OrderOperTypeBo();
        operTypeBo4.setValue(4);
        operTypeBo4.setLabel("队间转移");
        OrderOperTypeBo operTypeBo5 = new OrderOperTypeBo();
        operTypeBo5.setValue(5);
        operTypeBo5.setLabel("外部转移");
        OrderOperTypeBo operTypeBo6 = new OrderOperTypeBo();
        operTypeBo6.setValue(6);
        operTypeBo6.setLabel("报废处理");
//        OrderOperTypeBo operTypeBo7 = new OrderOperTypeBo();
//        operTypeBo7.setValue(7);
//        operTypeBo7.setLabel("其他报废");
        operTypeBoList.add(operTypeBo1);
        operTypeBoList.add(operTypeBo2);
        operTypeBoList.add(operTypeBo3);
        operTypeBoList.add(operTypeBo4);
        operTypeBoList.add(operTypeBo5);
        operTypeBoList.add(operTypeBo6);
//        operTypeBoList.add(operTypeBo7);
    }
    public static List<OrderTypeBo> OrderTypeList(){
        return orderTypeBoList;
    }
    public static List<OrderOperTypeBo> OrderOperTypeList(){
        return operTypeBoList;
    }
}
