package uz.com.Models;

import lombok.*;

import java.util.ArrayList;

import static utils.BaseUtil.generateUniqueID;

@Getter @Setter @ToString

public class User {
    @Setter (AccessLevel.NONE)
    private String id;
    private String name;
    private String password;
    private boolean Block;
    private int level; //1-super, 2-ceo, 3-manager, 4-worker
    private ArrayList<String> branchesID = new ArrayList<>(0);
    private String organizationID;

    public User() {
        this.id = generateUniqueID();
    }

    //Super Admin
    public User(String name, String password, int level) {
        this();
        this.name = name;
        this.password = password;
        this.level = level;
    }

    //CEO
    public User(String name, String password, int level, String organizationID) {
        this();
        this.name = name;
        this.password = password;
        this.level = level;
        this.organizationID = organizationID;
    }

    //Manager & Worker
    public User(String name, String password, int level, ArrayList<String> branchesID, String organizationID) {
        this();
        this.name = name;
        this.password = password;
        this.level = level;
        this.branchesID = branchesID;
        this.organizationID = organizationID;
    }
}
