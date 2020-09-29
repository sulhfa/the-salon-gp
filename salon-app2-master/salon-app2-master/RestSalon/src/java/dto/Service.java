package dto;

import java.util.ArrayList;
import java.util.Objects;


public class Service {
    private int id;
    private String Title;
    private int ownerId;
    private String salonname;
    private String Details;
    private double cost;
    private int status;
    private double avgRate;
    private int picCount;
    private ArrayList<String> imageList;
    
    public Service(){
    }
    
    public Service(int id){
        this.id = id;
    }
    
    public Service(int id , String title, int ownerId, String details, double cost,int status){
        this.id = id;
        this.Title = title;
        this.ownerId = ownerId;
        this.Details = details;
        this.cost = cost;
        this.status = status;
    }
    
    public Service(String title, int ownerId, String details, double cost){
        this.Title = title;
        this.ownerId = ownerId;
        this.Details = details;
        this.cost = cost;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * @param Title the Title to set
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     * @return the Details
     */
    public String getDetails() {
        return Details;
    }

    /**
     * @param Details the Details to set
     */
    public void setDetails(String Details) {
        this.Details = Details;
    }

    /**
     * @return the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(double cost) {
        this.cost = cost;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Service other = (Service) obj;
        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + this.getOwnerId();
        return hash;
    }

    /**
     * @return the ownerId
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId the ownerId to set
     */
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the salonname
     */
    public String getSalonName() {
        return salonname;
    }

    /**
     * @param salonname the salonname to set
     */
    public void setSalonName(String salonname) {
        this.salonname = salonname;
    }

    /**
     * @return the avgRate
     */
    public double getAvgRate() {
        return avgRate;
    }

    /**
     * @param avgRate the avgRate to set
     */
    public void setAvgRate(double avgRate) {
        this.avgRate = avgRate;
    }

    /**
     * @return the picCount
     */
    public int getPicCount() {
        return picCount;
    }

    /**
     * @param picCount the picCount to set
     */
    public void setPicCount(int picCount) {
        this.picCount = picCount;
    }

    /**
     * @return the imageList
     */
    public ArrayList<String> getImageList() {
        return imageList;
    }

    /**
     * @param imageList the imageList to set
     */
    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    
    
}
