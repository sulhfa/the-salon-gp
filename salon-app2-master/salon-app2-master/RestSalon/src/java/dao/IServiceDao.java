package dao;

import dto.Service;
import java.util.ArrayList;
import java.util.List;

public interface IServiceDao {
    public Service getServiceById(int service_id);
    public ArrayList<Service> getAllServiceByOwnerId(int owner_id);
    public ArrayList<Service> getAllService(boolean forpublic);
    public int addNewService(Service s);
    public int updateService(Service s);
    public int delService(int service_id);

    public int updateServiceStatus(int service_id, int new_status);

   
}
