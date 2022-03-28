package uz.com.Models;

import lombok.*;

import java.util.ArrayList;

@Setter @Getter @ToString
@NoArgsConstructor

public class Column {
    private ArrayList<String> branchIds = new ArrayList<>(0);
    private ArrayList<String> OrgIds = new ArrayList<>(0);

    public void addIds(String branchId, String orgId){
        this.branchIds.add(branchId);
        this.OrgIds.add(orgId);
    }
}
