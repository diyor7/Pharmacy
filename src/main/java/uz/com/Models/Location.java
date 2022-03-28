package uz.com.Models;


import lombok.*;

import java.util.ArrayList;

@Getter @Setter @ToString
@NoArgsConstructor

public class Location {
    private ArrayList<Row> rows = new ArrayList<>();
}
