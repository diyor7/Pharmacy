package uz.com.Models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

import static utils.BaseUtil.generateUniqueID;

@Getter @Setter @ToString

public class Branch {
    @Setter (AccessLevel.NONE)
    private String  id;
    private String name;
    private int[] location;
    /**
     * location array'da 2 ta raqam saqlanadi,
     * birinchisi row'ning indexi
     * ikkinchisi column'ning indexi
     */
    private String organizationID;
    private boolean Block;
    private ArrayList<Drug> drugs = new ArrayList<>(0);

    public Branch() {
        this.id = generateUniqueID();
    }

    public Branch(String name, int[] location, String organizationID) {
        this();
        this.name = name;
        this.location = location;
        this.organizationID = organizationID;
    }

}
