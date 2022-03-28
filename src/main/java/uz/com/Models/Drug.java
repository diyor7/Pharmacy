package uz.com.Models;

import lombok.*;

import static utils.BaseUtil.generateUniqueID;

@Getter @Setter @ToString


public class Drug {
    @Setter(AccessLevel.NONE)
    private String id;
    private String name;
    private String description;
    private double price;
    private String branchID;
    private Integer amount; //miqdori
    private long code; //shtrix kod

    public Drug() {
        this.id = generateUniqueID();
    }

    public Drug(String name,
                String description,
                double price,
                String branchID,
                Integer amount,
                long code) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.branchID = branchID;
        this.amount = amount;
        this.code = code;
    }
}
