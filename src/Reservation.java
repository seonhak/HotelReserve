import java.time.LocalDate;
import java.util.*;

public class Reservation {
    private Room room;
    private Customer customer;
    private LocalDate now;
    private List<Reservation> reserved_list;
    String reservation_id;

    public Reservation(){
        reserved_list = new ArrayList<>();
    }

    public Reservation(String reservation_id, Room room, Customer customer){
        this.reservation_id = reservation_id;
        this.now = LocalDate.now();
        this.room = room;
        this.customer = customer;
    }

    public String reserve(Room room, Customer customer){
        String id = UUID.randomUUID().toString();
        this.reserved_list.add(new Reservation(id, room, customer));
        return id;
    }
    public void addReservation(Hotel hotel, Customer customer, int room_number, Scanner sc){
        System.out.println("예약하시겠습니까?");
        System.out.println("1. 예" + "2. 아니오");
        if (sc.nextInt()==1){
            System.out.println("방이 예약됐습니다.");
            ((Room)hotel.getRoomList().get(room_number-1)).showRoom(room_number);
            this.reserve((Room) hotel.getRoomList().get(room_number-1), customer);
            customer.getReservation().reserve((Room) hotel.getRoomList().get(room_number-1), customer);
            hotel.start();
        }else{
            hotel.selectRoom(sc);
        }
    }
    public void cancleReservation(Hotel hotel, Customer customer, Scanner sc){
        System.out.println("취소할 번호를 선택해주세요.");
        int input = sc.nextInt();

        if(input > customer.getReservation().reserved_list.size()){
            System.out.print("올바른 번호를 입력해주세요. ");
        }else{
            customer.getReservation().reserved_list.get(input-1).getRoom().showRoom(input);
            System.out.println("위 방이 취소됐습니다.");
            for(int i = 0; i < hotel.getReservation().reserved_list.size(); i ++){
                if(hotel.getReservation().reserved_list.get(i).getRoom().getName()
                        == customer.getReservation().reserved_list.get(input-1).getRoom().getName()){
                    hotel.getReservation().reserved_list.remove(i);
                }
            }
            customer.getReservation().reserved_list.remove(input-1);
            hotel.start();
        }
    }

    public void showReservedList(Hotel hotel, Customer customer, Scanner sc){
        int input;
        customer.getReservation().showReservedList(hotel, sc);
        System.out.println("예약취소를 하시겠습니까?");
        System.out.println("1.확인 2.취소");
        while(true){
            input = sc.nextInt();
            switch(input){
                case 1 :
                    this.cancleReservation(hotel, customer, sc);
                    break;
                case 2 :
                    hotel.start();
                default:
                    System.out.println("올바른 번호를 입력해주세요.");
            }
        }
    }

    public void showReservedList(Hotel hotel, Scanner sc){
        int idx = 1;
        if(this.reserved_list.size() == 0){
            System.out.println("예약현황이 없습니다. 처음으로 돌아갑니다.");
            hotel.start();
        }
        for (Reservation reservation : this.reserved_list) {
            reservation.getRoom().showRoom(idx++);
        }
    }


    public List getReservedList(){
        return this.reserved_list;
    }

    public Room getRoom(){
        return this.room;
    }
}
