public class Room {
    private String name;
    private Double size;
    private Double price;
    private Boolean reservated;

    public Room(String name, Double size, Double price){
        this.name = name;
        this.size = size;
        this.price = price;
        this.reservated = false;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public Double getPrice(){
        return this.price;
    }
    public void setPrice(Double price){
        this.price = price;
    }
    public Double getSize(){
        return this.size;
    }
    public void setSize(Double size){
        this.size = size;
    }
    public Boolean getReservated(){
        return this.reservated;
    }
    public void setReservated(Boolean reservated){
        this.reservated = reservated;
    }
}
