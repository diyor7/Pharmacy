package uz.com.Models;

import lombok.*;

import java.util.ArrayList;

@Getter @Setter @ToString
@NoArgsConstructor

public class Row {
    private ArrayList<Column> columns = new ArrayList<>();
}
