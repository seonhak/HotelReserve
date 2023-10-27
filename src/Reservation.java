import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Reservation {
    private Room room;
    private Customer customer;
    private String reserved_date;
    private List<Reservation> reserved_list;

    String reservation_id;

    public Reservation(){
        reserved_list = new ArrayList<>();
    }

    public Reservation(String reservation_id, Room room, Customer customer, String reserved_date){
        this.reservation_id = reservation_id;
        this.room = room;
        this.customer = customer;
        this.reserved_date = reserved_date;
    }

    public String reserve(Room room, Customer customer){
        String id = UUID.randomUUID().toString();
        String now = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+09:00")).toString();
        this.reserved_list.add(new Reservation(id, room, customer, now));
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
                    hotel.setAsset(hotel.getAsset() + hotel.getReservation().reserved_list.get(i).getRoom().getPrice());
                    hotel.getReservation().reserved_list.remove(i);
                }
            }
            customer.setAsset(customer.getAsset() + customer.getReservation().reserved_list.get(input-1).getRoom().getPrice());
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
            System.out.printf("예약 일시 : " + reservation.reserved_date + " | ");
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
