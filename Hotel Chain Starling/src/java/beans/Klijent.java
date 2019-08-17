/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author dulet
 */
public class Klijent {
    private int id;
    private String name="";
    private String username="";
    private String password="";
    private String email="";
    private String phone="";
    private String address="";
    private String city="";
    private String state="";
    private int zip;
    private String power="";
    private int points;
    private int hotelID;
    
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getEmail()
    {
       return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    public String getState()
    {
        return state;
    }
    public void setState(String state)
    {
        this.state= state;
    }
    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city= city;
    }
    public int getZip()
    {
        return zip;
    }
    public void setZip(int zip)
    {
        this.zip = zip;
    }
    public String getPower()
    {
        return power;
    }
    public void setPower(String power)
    {
        this.power = power;
    }
    public int getPoints()
    {
        return points;
    }
    public void setPoints(int points)
    {
        this.points = points;
    }
    public int getHotelID()
    {
        return hotelID;
    }
    public void setHotelID(int hotelID)
    {
        this.hotelID = hotelID;
    }
}
