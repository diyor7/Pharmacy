package uz.com.Service;

import uz.com.Models.Branch;
import uz.com.Models.Organization;
import uz.com.Models.User;

import java.util.ArrayList;
import java.util.Objects;

import static utils.Color.*;
import static utils.Input.getNum;
import static utils.Input.getStr;
import static utils.Print.print;
import static utils.Print.println;
import static uz.com.MyDb.db.*;

public class UserService {

    public static ArrayList<String> branchesId = new ArrayList<>();

    public static void Login() {
        println(YELLOW, "\n--------------- L O G I N ---------------\n");
        String username = getStr("Username -> ");
        String password = getStr("User password -> ");
        User user = checkUserAlreadyExist(username);
        if (user == null || !user.getPassword().equals(password)) {
            println(RED, "\n------ Wrong username or password -------\n");
            return;
        }

        Organization organization = getOrganizationByUserID(user);
        if (user.getLevel() != 1) {
            if (Objects.nonNull(organization) && organization.isBlock()) {
                println(RED, "\n--------------- Organization is blocked ---------------\n");
                return;
            }
        }

        if (user.isBlock()) {
            println(RED, "\n--------------- User is blocked ---------------\n");
            return;
        }

        session = user;
        sessionOrg = organization;
        if (session.getLevel() == 1) { // Super Admin
            print(GREEN, "\n--------------- W E L C O M E ");
            print(BLUE, "- Super Admin ");
            println(GREEN, " ---------------\n");
        } else if (session.getLevel() == 2) { // CEO
            print(GREEN, "\n--------------- W E L C O M E ");
            print(BLUE, "- CEO ");
            println(GREEN, " ---------------\n");
        } else if (session.getLevel() == 3) { // Manager
            print(GREEN, "\n--------------- W E L C O M E ");
            print(BLUE, "- Manager ");
            println(GREEN, " ---------------\n");
        } else { // Employee - (sotuvchi)
            print(GREEN, "\n--------------- W E L C O M E ");
            print(BLUE, "- Employee ");
            println(GREEN, " ---------------\n");
        }

    }

    public static void LogOut() {
        session = null;
        sessionOrg = null;

        println(GREEN, "\n--------------- 🔒🔒 You successfully Logged Out 🔒🔒 ---------------\n");

    }

    public static void managersList() {

        int index = 1;
        println("");
        for (User user : users) {
            if (user.getLevel() == 3) {
                if (user.isBlock()) {
                    print((index++) + ". " + user.getName());
                    println(RED, "\tBlocked");
                } else {
                    print((index++) + ". " + user.getName());
                    println(GREEN, "\tNon Blocked");
                }
            }
        }
        println("");

        if (index == 1) {
            println(RED, "\n----------- Managers not created yet! -----------\n");
        }
    }

    public static void createManager() {

        if (sessionOrg.getBranches().size() == 0) {
            println(RED, "\n--------------- Branches not created yet!! ---------------\n");
            return;
        }

        println(YELLOW, "\n--------------- Create Manager ---------------\n");

        String managerName = getStr("Enter name -> ");
        User user = checkUserAlreadyExist(managerName);

        if (user != null) {
            println(RED, "\n----------------- This username already taken!!! -----------------\n");
            return;
        }

        String managerPassword = getStr("Enter password -> ");

        int index = 1;

        for (int i = 0; i < sessionOrg.getBranches().size(); i++) {
            if (!sessionOrg.getBranches().get(i).isBlock()) {
                println((index++) + ". " + sessionOrg.getBranches().get(i).getName());
                String id = sessionOrg.getBranches().get(i).getId();
                branchesId.add(id);
            }
        }

        if (index == 1) {
            println(RED, "\n------------- Unblocked branches not available! -------------\n");
            return;
        }


        println("0. Nothing");

        ArrayList<String> ids = new ArrayList<>();
        int choice;
        int counter = 0;
        while (true) {
            choice = getNum("Choose branch -> ");
            if (choice == 0) {
                break;
            }
            if (choice > 0 && choice < index) {
//                users.add(new User(managerName, managerPassword, 3, branchesId, sessionOrg.getId()));
                ids.add(branchesId.get(choice - 1));
                println(GREEN, "\n---------- " + managerName + " successfully added to Branch!! ----------\n");
                counter++;
            } else println(RED, "\n🛢🛢-> Wrong choice <-🛢🛢\n");

//            if (counter == 0) {
//                println(RED, "\n🛢🛢-> Wrong choice <-🛢🛢\n");
//            }
        }
        users.add(new User(managerName, managerPassword, 3, ids, sessionOrg.getId()));

    }

    public static void removeManager() {
        println(YELLOW, "\n--------------- Remove Manager ---------------\n");

        int index = 1;

        for (User user : users) {
            if (user.getLevel() == 3) {
                println((index++) + ". " + user.getName());
            }
        }

        if (index == 1) {
            println(RED, "\n----------- Managers not created yet!!! -----------\n");
            return;
        }
        println("0. Back ");
        String choice = getStr("Choose to remove manager(name) -> ");

        if (choice.equals("0")) {
            return;
        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(choice)) {
                users.remove(users.get(i));
                println(GREEN, "\n---------- Manager successfully removed ----------\n");
                return;
            }
            if (i == users.size() - 1) {
                println(RED, "\n🛢🛢-> Wrong choice <-🛢🛢\n");
            }
        }
        println(RED, "\n🛢🛢-> Wrong choice <-🛢🛢\n");
    }

    public static void blockManager() {


        println(YELLOW, "\n--------------- Block Manager ---------------\n");

        int index = 1;
        for (User user : users) {
            if (!user.isBlock() && user.getLevel() == 3) {
                println((index++) + ". " + user.getName());
            }
        }

        if (index == 1) {
            println(RED, "\n-------------- Unblocked managers not founded! --------------\n");
            return;
        }

        String choice = getStr("Choose to block manager(name) -> ");

        for (User user : users) {
            if (user.getName().equals(choice)) {
                user.setBlock(true);
                println(GREEN, "\n---------- Manager successfully blocked ----------\n");
                return;
            }
        }

        println(RED, "\n🛢🛢-> Wrong choice <-🛢🛢\n");

    }

    public static void unblockManager() {

        println(YELLOW, "\n--------------- Unblock Manager ---------------\n");

        int index = 1;
        for (User user : users) {
            if (user.isBlock() && user.getLevel() == 3) {
                println((index++) + ". " + user.getName());
            }
        }

        if (index == 1) {
            println(RED, "\n-------------- Blocked managers not founded! --------------\n");
            return;
        }

        String choice = getStr("Choose to unblock manager(name) -> ");

        for (User user : users) {
            if (user.getName().equals(choice)) {
                user.setBlock(false);
                println(GREEN, "\n---------- Manager successfully unblocked ----------\n");
                return;
            }
        }
        println(RED, "\n🛢🛢-> Wrong choice <-🛢🛢\n");
    }

    public static void employeesList() { // employeeList for CEO
        if (sessionOrg.getBranches().isEmpty()) {
            println(RED, "\n--------------- Branches not created yet!! ---------------\n");
            return;
        }

        println(YELLOW, "\n--------------- Employees List ---------------\n");


        int index = 1;
        for (User user : users) {
            if (user.getOrganizationID().equals(sessionOrg.getId()) && user.getLevel() == 4) {
                println((index++) + ". " + user.getName());
            }
        }

    }

    public static void createEmployee() { // Manager Employee yaratishi uchun

        if (sessionOrg.getBranches().isEmpty()) {
            println(RED, "\n--------------- Branches not created yet!! ---------------\n");
            return;
        }

        println(YELLOW, "\n--------------- Create Employee ---------------\n");

        String employeeName = getStr("Enter name -> ");
        User user = checkUserAlreadyExist(employeeName);

        if (user != null) {
            println(RED, "\n----------------- This username already taken!!! -----------------\n");
            return;
        }

        String employeePassword = getStr("Enter password -> ");

        createEmployeeLogic(employeeName, employeePassword);
    }

    private static void createEmployeeLogic(String employeeName, String employeePassword) {
        int index = 1;
        if (!sessionOrg.getBranches().isEmpty()) {
            for (int i = 0; i < sessionOrg.getBranches().size(); i++) {
                println((index++) + ". " + sessionOrg.getBranches().get(i).getName());

                if (i == sessionOrg.getBranches().size() - 1) {
                    int choice = getNum("To which branch -> ");

                    if (choice > 0 && choice < index) {
                        String id = sessionOrg.getBranches().get(choice - 1).getId();
                        branchesId.add(id);
                        users.add(new User(employeeName, employeePassword, 4, branchesId, sessionOrg.getId()));
                        println(GREEN, employeeName + " successfully added to Branch!!");
                    } else {
                        println(RED, "Wrong option!!");
                        createEmployeeLogic(employeeName, employeePassword);
                    }
                }
            }

        } else {
            println(RED, "\n---------------- Branches not created yet ----------------\n");
        }
    }

    public static void removeEmployee() {

        if (sessionOrg.getBranches().isEmpty()) {
            println(RED, "\n--------------- Branches not created yet!! ---------------\n");
            return;
        }

        println(YELLOW, "\n--------------- Remove Employee ---------------\n");

        int index = 1;

        for (User user : users) {
            if (user.getOrganizationID().equals(sessionOrg.getId()) && user.getBranchesID().equals(session.getBranchesID()) && user.getLevel() == 4) {
                println((index++) + ". " + user.getName());
            }
        }

        if (index == 1) {
            println(RED, "\n----------- Employees not created yet!!! -----------\n");
            return;
        }
        println("0. Back ");
        int choice = getNum("Choose to remove employee -> ");

        if (choice == 0) {
            println("");
        } else if (choice > 0 && choice < index) {
            users.remove(choice - 1);
            println(GREEN, "\n---------- Employee successfully removed ----------\n");
        } else {
            println(RED, "\n---------- Wrong option!! ----------\n");
        }
    }

    public static void blockEmployee() {

        if (sessionOrg.getBranches().isEmpty()) {
            println(RED, "\n--------------- Branches not created yet!! ---------------\n");
            return;
        }

        println(YELLOW, "\n--------------- Block Employee ---------------\n");

        int index = 1;
        for (User user : users) {
            if (!user.isBlock() && user.getLevel() == 4 && user.getOrganizationID().equals(sessionOrg.getId()) && user.getBranchesID().equals(session.getBranchesID())) {
                println((index++) + ". " + user.getName());
            }
        }

        if (index == 1) {
            println(RED, "\n-------------- Unblocked employees not founded! --------------\n");
            return;
        }

        String choice = getStr("Choose to block employee(name) -> ");

        for (User user : users) {
            if (user.getName().equals(choice)) {
                user.setBlock(true);
                println(GREEN, "\n---------- Employee successfully blocked ----------\n");
                return;
            }
        }
        println(RED, "\n🛢🛢-> Wrong choice <-🛢🛢\n");
    }

    public static void unblockEmployee() {

        if (sessionOrg.getBranches().isEmpty()) {
            println(RED, "\n--------------- Branches not created yet!! ---------------\n");
            return;
        }

        println(YELLOW, "\n--------------- Unblock Employee ---------------\n");

        int index = 1;
        for (User user : users) {
            if (user.isBlock() && user.getLevel() == 4 && user.getOrganizationID().equals(sessionOrg.getId()) && user.getBranchesID().equals(session.getBranchesID())) {
                println((index++) + ". " + user.getName());
            }
        }

        if (index == 1) {
            println(RED, "\n-------------- Blocked employees not founded! --------------\n");
            return;
        }

        String choice = getStr("Choose to unblock employee(name) -> ");

        for (User user : users) {
            if (user.getName().equals(choice)) {
                user.setBlock(false);
                println(GREEN, "\n---------- Employee successfully unblocked ----------\n");
                return;
            }
        }
        println(RED, "\n🛢🛢-> Wrong choice <-🛢🛢\n");
    }

    public static User checkUserAlreadyExist(String username) {
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public static Organization getOrganizationByUserID(User user) {
        String OrgID = user.getOrganizationID();
        for (Organization organization : organizations) {
            if (organization.getId().equals(OrgID)) return organization;
        }
        return null;
    }

    public static Branch getBranch(String branchID, Organization organization) {
        for (Branch branch : organization.getBranches()) {
            if (branchID.equals(branch.getId())) return branch;
        }
        return null;
    }

    public static Organization getOrganization(String organizationID) {
        for (Organization organization : organizations) {
            if (organizationID.equals(organization.getId()))
                return organization;
        }
        return null;
    }

    public static User getUser(String username) {
        for (User user : users) {
            if (username.equalsIgnoreCase(user.getName()))
                return user;
        }
        return null;
    }

}
