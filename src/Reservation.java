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
    public void addReservation(Hotel hotel, Customer customer, int room_number, Scanner sc){
        System.out.println("예약하시겠습니까?");
        System.out.println("1. 예" + "2. 아니오");
        // 보유자산이 예약할 호텔보다 적을때 && 예약 번호가 할당이 되었을경우
        if (sc.nextInt()==1){
            for (Reservation reservation :this.reserved_list){
                if (reservation.room.getName().equals(((Room)hotel.getRoomList().get(room_number-1)).getName())){
                    System.out.println("이미 예약된 방입니다.");
                    hotel.showRoomList();
                    hotel.selectRoom(sc);
                }
            }
            if (((Room)hotel.getRoomList().get(room_number-1)).getPrice() < customer.getAsset()){
                System.out.println("방이 예약됐습니다.");
                ((Room)hotel.getRoomList().get(room_number-1)).showRoom(room_number);
                this.reserve((Room) hotel.getRoomList().get(room_number-1), customer);
                customer.getReservation().reserve((Room) hotel.getRoomList().get(room_number-1), customer);
                customer.setAsset(customer.getAsset() - ((Room) hotel.getRoomList().get(room_number - 1)).getPrice());
                hotel.start();
            }else{
                System.out.println("소지금이 부족합니다.");
                hotel.showRoomList();
                hotel.selectRoom(sc);
            }
        }
    }
}
