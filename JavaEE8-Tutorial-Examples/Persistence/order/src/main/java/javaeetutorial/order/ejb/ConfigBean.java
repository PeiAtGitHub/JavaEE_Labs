package javaeetutorial.order.ejb;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton @Startup
public class ConfigBean {

    @EJB
    private RequestBean requestBean;

    @PostConstruct
    public void createData() {
        requestBean.createPart("1234-5678-01", 1, "ABC PART", new Date(), "PARTQWERTYUIOPASXDCFVGBHNJMKL", null);
        requestBean.createPart("9876-4321-02", 2, "DEF PART", new Date(), "PARTQWERTYUIOPASXDCFVGBHNJMKL", null);
        requestBean.createPart("5456-6789-03", 3, "GHI PART", new Date(), "PARTQWERTYUIOPASXDCFVGBHNJMKL", null);
        requestBean.createPart("ABCD-XYZW-FF", 5, "XYZ PART", new Date(), "PARTQWERTYUIOPASXDCFVGBHNJMKL", null);
        requestBean.createPart("SDFG-ERTY-BN", 7, "BOM PART", new Date(), "PARTQWERTYUIOPASXDCFVGBHNJMKL", null);

        requestBean.addPartToBillOfMaterial("SDFG-ERTY-BN", 7, "1234-5678-01", 1);
        requestBean.addPartToBillOfMaterial("SDFG-ERTY-BN", 7, "9876-4321-02", 2);
        requestBean.addPartToBillOfMaterial("SDFG-ERTY-BN", 7, "5456-6789-03", 3);
        requestBean.addPartToBillOfMaterial("SDFG-ERTY-BN", 7, "ABCD-XYZW-FF", 5);

        requestBean.createVendor(100, "WidgetCorp", "111 Main St., Anytown, KY 99999", "Mr. Jones", "888-777-9999");
        requestBean.createVendor(200, "Gadget, Inc.", "123 State St., Sometown, MI 88888", "Mrs. Smith",
                "866-345-6789");

        requestBean.createVendorPart("1234-5678-01", 1, "PART1", 100.00, 100);
        requestBean.createVendorPart("9876-4321-02", 2, "PART2", 10.44, 200);
        requestBean.createVendorPart("5456-6789-03", 3, "PART3", 76.23, 200);
        requestBean.createVendorPart("ABCD-XYZW-FF", 5, "PART4", 55.19, 100);
        requestBean.createVendorPart("SDFG-ERTY-BN", 7, "PART5", 345.87, 100);

        Integer orderId = new Integer(1111);
        requestBean.createOrder(orderId, 'N', 10, "333 New Court, New City, CA 90000");
        requestBean.addLineItem(orderId, "1234-5678-01", 1, 3);
        requestBean.addLineItem(orderId, "9876-4321-02", 2, 5);
        requestBean.addLineItem(orderId, "ABCD-XYZW-FF", 5, 7);

        orderId = new Integer(4312);
        requestBean.createOrder(orderId, 'N', 0, "333 New Court, New City, CA 90000");
        requestBean.addLineItem(orderId, "SDFG-ERTY-BN", 7, 1);
        requestBean.addLineItem(orderId, "ABCD-XYZW-FF", 5, 3);
        requestBean.addLineItem(orderId, "1234-5678-01", 1, 15);
    }

    @PreDestroy
    public void deleteData() {
    }
}
