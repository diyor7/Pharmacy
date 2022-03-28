package uz.com.Models;

import lombok.*;

import java.util.ArrayList;

import static utils.BaseUtil.generateUniqueID;

@Getter @Setter @ToString

public class Organization {
    @Setter (AccessLevel.NONE)
    private String id;
    private String name;
    private ArrayList<Branch> branches = new ArrayList<>(0);
    private boolean Block;
    private String colorInMap; // mapda belgilash uchun kerak

    public Organization(String name) {
        this.id = generateUniqueID();
        this.name = name;
    }
}
