package gouvo;

import java.util.Date;

/**
 *
 * @author gouvo
 */
public class Orders{
    
    private int unitsCount;
    private String appID,orderID,clientName,itemName,orderDate;
    private float netPrice,taxPercentage;

    public Orders(){
        
    } 
    public Orders(String appID, String orderID, String orderDate, String clientName, String itemName, int unitsCount, float netPrice, float taxPercentage) {
        this.appID = appID;
        this.unitsCount = unitsCount;
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.clientName = clientName;
        this.itemName = itemName;
        this.netPrice = netPrice;
        this.taxPercentage = taxPercentage;
    }
    
    public String getAppID() {
        return appID;
    }
    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getOrderID() {
        return orderID;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
 
    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }  
    
    public int getUnitsCount() {
        return unitsCount;
    }
    public void setUnitsCount(int unitsCount) {
        this.unitsCount = unitsCount;
    }   
    
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }  
   
    public float getNetPrice() {
        return netPrice;
    }
    public void setNetPrice(float netPrice) {
        this.netPrice = netPrice;
    }
    
    public float getTaxPercentage() {
        return taxPercentage;
    }
    public void setTaxPercentage(float taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    @Override
     public String toString() {                                 // One function that returns in type string all the values of the object Orders apart from appID
        return (this.getOrderID() +"  "+ this.getOrderDate()
             +"  "+ this.clientName +"  "+ this.itemName +"  "+ this.getUnitsCount() 
             +"  "+ this.getNetPrice()+"$"+"  "+ this.getTaxPercentage()+"%");
     }
     
     public String toString1(){                                 // One function that returns in type string all the values of the object Orders
         return (this.getAppID()+";"+this.getOrderID() +";"+ this.getOrderDate()
                +";"+ this.clientName +";"+ this.itemName +";"+ this.getUnitsCount() 
                +";"+ this.getNetPrice()+";"+ this.getTaxPercentage());
     }
}
