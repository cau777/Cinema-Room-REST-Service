package cinema.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class SeatsController {
    private final HashMap<String, Seat> purchases = new HashMap<>();
    private final SeatsInfo seatsInfo;

    public SeatsController() {
        seatsInfo = new SeatsInfo();
    }

    @GetMapping("/seats")
    public SeatsInfo GetSeatsInfo() {
        return seatsInfo;
    }

    @PostMapping("/purchase")
    public Object postPurchase(@RequestBody Seat seat) {
        int row = seat.getrow();
        int column = seat.getcolumn();

        if (!seatsInfo.isInBounds(row, column)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "The number of a row or a column is out of bounds!"));
        }

        if (!seatsInfo.isAvailable(row, column)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "The ticket has been already purchased!"));
        }

        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        purchases.put(token, seat);
        seatsInfo.purchaseSeat(row, column);
        return Map.of("token", token, "ticket", seat);
    }

    @PostMapping("/return")
    public Object postReturn(@RequestBody Map<String, String> map) {
        String token = map.get("token");
        Seat seat = purchases.remove(token);
        if (seat == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Wrong token!"));
        }

        seatsInfo.returnSeat(seat.getrow(), seat.getcolumn());
        return Map.of("returned_ticket", seat);
    }

    @PostMapping("/stats")
    public Object postStats(@RequestParam(required = false) String password) {
        final String correctPassword = "super_secret";

        if (!correctPassword.equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "The password is wrong!"));
        }

        int income = 0;
        int sold = 0;

        for (Seat seat : purchases.values()) {
            income += seat.getprice();
            sold++;
        }

        return Map.of("current_income", income,
                "number_of_available_seats", seatsInfo.calcTotalSeats() - sold,
                "number_of_purchased_tickets", sold);
    }
}
