package dto;


public class Owner {
    private int owner_id;
    private String salonname;

    public Owner(int ownerid, String salonName) {
        this.owner_id= ownerid;
        this.salonname = salonName;
    }

    public Owner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the owner_id
     */
    public int getOwner_id() {
        return owner_id;
    }

    /**
     * @param owner_id the owner_id to set
     */
    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    /**
     * @return the salonname
     */
    public String getSalonname() {
        return salonname;
    }

    /**
     * @param salonname the salonname to set
     */
    public void setSalonname(String salonname) {
        this.salonname = salonname;
    }
}
