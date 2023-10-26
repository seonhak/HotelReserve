import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
}
