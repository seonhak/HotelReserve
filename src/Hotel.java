import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hotel {
    private List<Room> room_list;
    private String name;
    private Double asset;
    private Reservation reservation;
    private List<Customer> customer;
    private Customer reserving_customer;


    public Hotel(){
        this.name = "신라호텔";
        this.asset = 100.0;
        this.reservation = new Reservation();
        this.room_list = new ArrayList<>();
        this.customer = new ArrayList<>();
        room_list.add(new Room("Room_A", 21.4, 7.0));
        room_list.add(new Room("Room_B", 14.3, 5.5));
        room_list.add(new Room("Room_C", 32.8, 8.5));
    }
    public Hotel(String name, Double asset){
        this.reservation = new Reservation();
        this.name = name;
        this.asset = asset;
    }
    public void start(){
        Scanner sc = new Scanner(System.in);
        int input;
        if(this.reserving_customer == null){
            this.logIn(sc);
        }
        System.out.println(reserving_customer.getName() + "님 " + this.name + "에 오신 것을 환영합니다!");
        this.showRoomList();
        while(true){
            System.out.printf("1.예약진행 2.예약조회 3.호텔예약현황조회 \n로그아웃을 원하시면 0을 입력해주세요\n");
            input = sc.nextInt();
            switch (input){
                case 0 :
                    this.reserving_customer = null;
                    logIn(sc);
                    start();
                    break;
                case 1 :
                    this.selectRoom(sc);
                    break;
                case 2 :
                    this.reserving_customer.getReservation().showReservedList(this, this.reserving_customer, sc);
                    break;
                case 3 :
                    this.reservation.showReservedList(this, sc);
                    break;
                default :
                    System.out.println("올바른 번호를 입력해주세요");
            }
        }

    }

    public void selectRoom(Scanner sc){
        System.out.println("예약할 방 번호를 선택해주세요(0을 입력하면 처음으로 돌아갑니다)");
        while(true){
            int input = sc.nextInt();
            if(input > room_list.size()){
                System.out.println("올바른 번호를 입력해주세요");
            }else if(input == 0){
                this.start();
            }else{
                this.reservation.addReservation(this, reserving_customer, input, sc);
            }
        }
    }

    private void logIn(Scanner sc){
        String id, password;
        System.out.printf("아이디를 입력해주세요 : ");
        id = sc.next();
        System.out.printf("비밀번호를 입력해주세요 : ");
        password = sc.next();
        for(Customer c : this.customer){
            if(c.logInAccess(id, password)){
                this.reserving_customer = c;
                return;
            }
        }
        System.out.println("일치하는 회원 정보가 없습니다. 가입을 진행해주세요.");
        this.signUp(sc);
        return;
    }

    private void signUp(Scanner sc){
        String id, password, name, phone_number;
        Double asset;
        System.out.printf("가입하실 아이디를 입력해주세요 : ");
        id = sc.next();
        System.out.printf("비밀번호를 입력해주세요 : ");
        password = sc.next();
        System.out.printf("이름을 입력해주세요 : ");
        name = sc.next();
        System.out.printf("전화번호를 입력해주세요 ex)010-xxxx-xxxx : ");
        phone_number = sc.next();
        System.out.printf("보유자산을 입력해주세요 : ");
        asset = sc.nextDouble();
        Customer c = new Customer(id, password, name, asset, phone_number);
        this.reserving_customer = c;
        this.customer.add(c);
        return;
    }
    public void showRoomList(){
        System.out.println("====== " + this.name + "의 방 목록 ======");
        int idx = 1;
        for(Room room : room_list){
            System.out.println(idx++ + "번 방 : " + room.getName() + " | " + room.getSize() + " | " + room.getPrice());
        }
        return;
    }


    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public Double getAsset(){
        return this.asset;
    }
    public void setAsset(Double asset){
        this.asset = asset;
    }
    public List getRoomList(){
        return this.room_list;
    }

    public Reservation getReservation(){
        return this.reservation;
    }
    public List getCustomer(){
        return this.customer;
    }

    public Customer getReserving_customer() {
        return this.reserving_customer;
    }
}