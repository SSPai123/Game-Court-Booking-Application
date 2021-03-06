package dev.sukanya.gamecourtbooking.model.courts;

import dev.sukanya.gamecourtbooking.model.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="booking")
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String bookingName;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToOne(fetch = FetchType.EAGER) // This will  load roles along with users --> by default fetchType is Lazy for Many to Many
    private Court court;

    @OneToOne(fetch = FetchType.EAGER)
    private TimeSlot timeSlot;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Date bookedDate;

    private long cost;

}
