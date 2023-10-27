import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

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
    public void addReservation(Hotel hotel){
        System.out.println("예약하시겠습니까?");
        System.out.println("1. 예" + "2. 아니오");
        Scanner sc  = new Scanner(System.in);
        if (sc.nextInt()==1){
            System.out.println("예약하실 방 번호를 입력해주세요");
            int input = sc.nextInt();
            ((Room) hotel.getRoomList().get(input-1)).showRoom(input);
            reserve((Room) hotel.getRoomList().get(input-1),hotel.getReserving_customer());
            System.out.println(hotel.getReserving_customer().getName());
            hotel.start();
        }
    }
}
