public class Customer extends Hotel{
    private String phone_number;
    private String id;
    private String password;

    public Customer(String id, String password, String name, Double asset, String phone_number){
        super(name, asset);
        this.id = id;
        this.password = password;
        this.phone_number = phone_number;
    }

    public String getPhone_number(){
        return this.phone_number;
    }
    public void setPhone_number(String phone_number){
        this.phone_number = phone_number;
    }

    public Boolean logInAccess(String id, String password){
        System.out.println("/" + this.id + "/" + id + "/");
        System.out.println("/" + this.password + "/" + password + "/");
        if(id.equals(this.id) && password.equals(this.password)){
            return true;
        }else{
            return false;
        }
    }
}
