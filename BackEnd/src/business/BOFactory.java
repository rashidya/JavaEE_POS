package business;


import business.custom.Impl.CustomerBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBOFactory(){
        if (boFactory==null){
            boFactory=new BOFactory();
        }
        return boFactory;
    }


    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:return new CustomerBOImpl();
            /*case ORDER:return new ManageOrderBOImpl();
            case PURCHASE_ORDER:return new PurchaseOrderBOImpl();
            case SYSTEM_REPORTS:return new SystemReportsBOImpl();*/
            default:return null;
        }
    }

    public enum BOTypes{
        CUSTOMER,ORDER,PURCHASE_ORDER,SYSTEM_REPORTS
    }
}
