package dto;

import java.sql.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account {

    private int user_id;
    private String password;
    private String email;
    private String fullname;
    private int userType;
    private int status;
    private int securityQuestionId;
    private String securityAnswer;
    private Date createdAt;
    private String address;
    private String phone;
    private String location;
    private String notes;
    private String salonName;

    public Account() {

    }

    public Account(int id) {
        this.user_id = id;
    }

    public Account(int id, String email, String fullname, 
            int userType,int status,
            int securityQuestionId, String securityAnswer,
            String address, String phone, String location, String notes) {
        this.user_id = id;
        this.email = email;
        this.fullname = fullname;
        this.userType = userType;
        this.securityQuestionId = securityQuestionId;
        this.securityAnswer = securityAnswer;
        this.address = address;
        this.status = status;
        this.phone = phone;
        this.location = location;
        this.notes = notes;
    }

    public Account(String password, String email, String fullname,
            int userType,
            int securityQuestionId, String securityAnswer,
            String address, String phone, String location, String notes) {
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.userType = userType;
        this.securityQuestionId = securityQuestionId;
        this.securityAnswer = securityAnswer;
        this.address = address;
        this.phone = phone;
        this.location = location;
        this.notes = notes;
    }

    public Account(int Id, String password, String email, String fullname,
            int userType,
            int securityQuestionId, String securityAnswer,
            String address, String phone, String location, String notes) {
        this.user_id = Id;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.userType = userType;
        this.securityQuestionId = securityQuestionId;
        this.securityAnswer = securityAnswer;
        this.address = address;
        this.phone = phone;
        this.location = location;
        this.notes = notes;
    }
    
    /**
     * @return the user_id
     */
    public int getUser_id() {
        return user_id;
    }


    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        return Objects.equals(this.getUser_id(), other.getUser_id());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + this.getUser_id();
        hash = 73 * hash + (this.getEmail() != null ? this.getEmail().hashCode() : 0);
        return hash;
    }
    
    public boolean IsValid() {
        return  this.getPassword()!= null && 
                this.getEmail()!= null &&
                !"".equals(this.password) &&
                !"".equals(this.email) &&
                this.getEmail().length()>=6 && 
                this.getPassword().length()>=6 &&
                isValidEmail(this.getEmail()) ;
    }

    private boolean isValidEmail(String email) 
    { 
        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
                       
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        Matcher matcher = pat.matcher(email);
        return matcher.matches(); 
    } 
    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the userType
     */
    public int getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(int userType) {
        this.userType = userType;
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
     * @return the securityQuestionId
     */
    public int getSecurityQuestionId() {
        return securityQuestionId;
    }

    /**
     * @param securityQuestionId the securityQuestionId to set
     */
    public void setSecurityQuestionId(int securityQuestionId) {
        this.securityQuestionId = securityQuestionId;
    }

    /**
     * @return the securityAnswer
     */
    public String getSecurityAnswer() {
        return securityAnswer;
    }

    /**
     * @param securityAnswer the securityAnswer to set
     */
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the salonName
     */
    public String getSalonName() {
        return salonName;
    }

    /**
     * @param salonName the salonName to set
     */
    public void setSalonName(String salonName) {
        this.salonName = salonName;
    }

}
