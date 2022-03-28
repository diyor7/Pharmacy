package uz.com.Service;

import uz.com.Models.Branch;
import uz.com.Models.User;
import uz.com.MyDb.db;

import java.util.ArrayList;
import java.util.Objects;

import static utils.BaseUtil.checkChoice;
import static utils.Color.GREEN;
import static utils.Color.RED;
import static utils.Input.getNum;
import static utils.Input.getStr;
import static utils.Print.print;
import static utils.Print.println;
import static uz.com.MyDb.db.*;

public class BranchService {


    public static void createBranch() {
        String branchName = getStr("Branch's name: ");
        String organizationId = session.getOrganizationID();
        int row;
        int column;
        label:
        while (true) {
            row = getNum(String.format("Location (Latitude, [1;%s]):", mapSize)) - 1;
            column = getNum(String.format("Location (Longitude, [1;%s]):", mapSize)) - 1;
            if (!checkChoice(row, 0, mapSize - 1) || !checkChoice(column, 0, mapSize - 1)) {
                println(RED, "Wrong choice! Try again!");
                continue;
            }
            for (Branch branch : sessionOrg.getBranches()) {
                if (branch.getLocation()[0] == row && branch.getLocation()[1] == column) {
                    println(RED, "This location is taken before, choose another one");
                    continue label;
                }
            }
            break;
        }
        int[] location = new int[]{row, column};
        Branch branch = new Branch(branchName, location, organizationId);
        sessionOrg.getBranches().add(branch);
        db.location.getRows().get(row).getColumns().get(column).addIds(branch.getId(), organizationId);
        println(GREEN, "Branch successfully added");

    }

    public static void removeBranch() {
        branchList();
        int choice = getNum("Which branch do you want to remove") - 1;
        if (!checkChoice(choice, 0, sessionOrg.getBranches().size() - 1)) {
            println(RED, "Wrong choice! Try again!");
            return;
        }
        Branch branch = sessionOrg.getBranches().get(choice);
        String branchId = branch.getId();
        ArrayList<User> del = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            ArrayList<String> branchesID = users.get(i).getBranchesID();
            for (int j = 0; j < branchesID.size(); j++) {
                if(branchId.equals(branchesID.get(j))){
                    branchesID.remove(j);
                }
            }
            users.get(i).setBranchesID(branchesID);
            if (users.get(i).getBranchesID().isEmpty()) del.add(users.get(i));
        }

        users.removeAll(del);

        sessionOrg.getBranches().remove(choice);
        println(GREEN,"Branch is removed");







        /*for (User user : users) {
            ArrayList<String> branchesID = user.getBranchesID();
            for (int i = 0; i < branchesID.size(); i++) {
                String id = branchesID.get(i);
                if (branchId.equals(id)) {
                    user.getBranchesID().remove(branchId);
                }
                if (user.getBranchesID().size() == 0 && user.getLevel() != 2) users.remove(user);
            }
        }*/

    }

    public static void blockBranch() {

        if (sessionOrg.getBranches().isEmpty()){
            println(RED, "\n------------ Branches not created yet!! ------------\n");
            return;
        }

        int index = 1;
        for (int i = 0; i < sessionOrg.getBranches().size(); i++) {
            if (!sessionOrg.getBranches().get(i).isBlock()){
                println( (index++) + ". " + sessionOrg.getBranches().get(i).getName() );
            }
        }
        if (index == 1){
            println(RED,"\n------------- Unblocked branches not founded -------------\n");
            return;
        }

        String choice = getStr("Choose to block branch(name) -> ");

        for (int i = 0; i < sessionOrg.getBranches().size(); i++) {
            if (sessionOrg.getBranches().get(i).getName().equals(choice)) {
                sessionOrg.getBranches().get(i).setBlock(true);
                println(GREEN, "\n---------- Branch successfully blocked ----------\n");
                return;
            }
        }

        println(RED,"\n🛢🛢-> Wrong choice <-🛢🛢\n");

    }

    public static void unblockBranch() {
        if (sessionOrg.getBranches().isEmpty()){
            println(RED, "\n------------ Branches not created yet!! ------------\n");
            return;
        }

        int index = 1;
        for (int i = 0; i < sessionOrg.getBranches().size(); i++) {
            if (sessionOrg.getBranches().get(i).isBlock()){
                println( (index++) + ". " + sessionOrg.getBranches().get(i).getName() );
            }
        }

        if (index == 1){
            println(RED,"\n------------- Blocked branches not founded -------------\n");
            return;
        }

        String choice = getStr("Choose to unblock branch(name) -> ");

        for (int i = 0; i < sessionOrg.getBranches().size(); i++) {

            if (sessionOrg.getBranches().get(i).getName().equals(choice)) {
                sessionOrg.getBranches().get(i).setBlock(false);
                println(GREEN, "\n---------- Branch successfully unblocked ----------\n");
                return;
            }
        }

        println(RED,"\n🛢🛢-> Wrong choice <-🛢🛢\n");
    }

    public static void branchList() {
        int index = 1;
        if (session.getLevel() == 2) {
            if (Objects.nonNull(sessionOrg.getBranches())) {
                for (int i = 0; i < sessionOrg.getBranches().size(); i++) {
                    println((index++) + ". " + sessionOrg.getBranches().get(i).getName());
                }
            } else {
                println(RED, "\n---------------- Branches not created yet ----------------\n");
            }
        } else {
            ArrayList<String> branchesID = session.getBranchesID();
            ArrayList<Branch> branches = sessionOrg.getBranches();
            for (String s : branchesID) {
                for (Branch branch : branches) {
                    if (s.equals(branch.getId())) {
                        println(String.format("%s. %s", index++, branch.getName()));
                        break;
                    }
                }
            }
            if (index == 1) println(RED, "\n---------------- Branches not created yet ----------------\n");
        }

    }

}
