package repository;


import repository.custom.Impl.CustomerDAOImpl;
import repository.custom.Impl.ItemDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getDaoFactory(){
        if (daoFactory==null){
            daoFactory=new DAOFactory();
        }
        return daoFactory;
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case ITEM:return new ItemDAOImpl();
            case CUSTOMER:return new CustomerDAOImpl();
           /* case ORDER:return new OrderDAOImpl();
            case ORDER_DETAILS:return new OrderDetailDAOImpl();*/
            default:return null;


        }
    }

    public enum DAOTypes{
        CUSTOMER,ITEM,ORDER,ORDER_DETAILS
    }
}
