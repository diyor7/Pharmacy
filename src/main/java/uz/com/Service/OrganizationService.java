package uz.com.Service;

import uz.com.Models.*;

import java.util.ArrayList;

import static utils.BaseUtil.checkChoice;
import static utils.Color.*;
import static utils.Input.getNum;
import static utils.Input.getStr;
import static utils.Print.print;
import static utils.Print.println;
import static uz.com.MyDb.db.*;
import static uz.com.Service.BranchService.branchList;
import static uz.com.Service.UserService.checkUserAlreadyExist;
import static uz.com.Service.UserService.getOrganization;


public class OrganizationService {

    public static void removeOrganization() {
        int choi = choice();
        if (choi < 0) return;
        Organization organization = organizations.get(choi-1);
        organizations.remove(organization);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if(user.getOrganizationID().equals(organization.getId())){
                users.remove(user);
            }
        }
        println(GREEN, "Organization removed 🪓");

    }

    public static void blockOrganization() {
        int index = 1;
        if (!organizations.isEmpty()){
            for (Organization organization : organizations) {
                if (!organization.isBlock()) {
                    println((index++) + ". " + organization.getName());
                }
            }
        }else {
            println(RED,"\n----------- Organizations not created yet! -----------\n");
            return;
        }

        if (index == 1){
            println(RED,"\n----------- Unblocked organizations not founded -----------\n");
            return;
        }

        int choi = choice();
        if (choi < 0) return;
        organizations.get(choi - 1).setBlock(true);
        println(GREEN, "Organization blocked 🔐");
    }

    public static void createOrganization() {
        println(BLUE, "\n------------- 🔨🔨 Create Organization 🔨🔨 -------------\n");
        String orgName = getStr("Organization name: ");
        if (checkFindOrgName(orgName)) {
            println(RED,"this username all ready token 🤨");
            return;
        }
        Organization organization = new Organization(orgName);
        organization.setColorInMap(colors.get(0));
        colors.remove(0);

        String ceoUsername = getStr("Username for CEO: ");

        User user = checkUserAlreadyExist(ceoUsername);

        if (user != null) {
            println(RED, "\n----------------- This username already taken!!! -----------------\n");
            return;
        }

        String ceoPassword = getStr("Password for CEO: ");


        user = new User(ceoUsername, ceoPassword, 2, organization.getId());

        users.add(user);
        organizations.add(organization);

        println(GREEN, "\n---------------- ↪ Created😊 ↩ ----------------\n");
    }

    private static boolean checkFindOrgName(String orgName) {
        for (Organization organization : organizations) {
            if (organization.getName().equals(orgName)) {
                return true;
            }
        }
        return false;
    }

    public static void unblockOrganization() {
        int index = 1;
        if (!organizations.isEmpty()){

            for (Organization organization : organizations) {
                if (organization.isBlock()) {
                    println((index++) + ". " + organization.getName());
                }
            }
        }else {
            println(RED,"\n----------- Organizations not created yet! -----------\n");
            return;
        }

        if (index == 1){
            println(RED,"\n----------- Blocked organizations not founded -----------\n");
            return;
        }

        int choi = choice();

        organizations.get(choi - 1).setBlock(false);
        println(GREEN, "\n---------- Organization unblocked 🔓 ------------\n");

    }


    public static void organizationList() {
        String choice = getStr("Without branch -> 0\nWith branch -> 1");
        print("--> ");
        organizationList(choice);
    }

    public static void organizationList(String choice) {
        for (int i = 0; i < organizations.size(); i++) {
            println((i + 1) + "." + organizations.get(i).getName());
            ArrayList<Branch> branches = organizations.get(i).getBranches();
            for (int j = 0; choice.equals("1") && j < branches.size(); j++) {
                println(" " + (i + 1) + "." + (j + 1) + " " + branches.get(j).getName());
            }
        }
    }

    public static void organizationLocation() {
        for (Row row : location.getRows()) {
            for (Column column : row.getColumns()) {
                if(column.getOrgIds().isEmpty()){
                    print("...\t");
                    continue;
                }
                for (String orgId : column.getOrgIds()) {
                    String color = getOrganization(orgId).getColorInMap();
                    print(color, "🔴");
                } print("\t");
            }
            println("");
        }

    }

    private static int choice() {
        organizationList("0");
        int choice = getNum("Which one ->  ");
        if (choice > organizations.size() || choice < 1) {
            println(RED,"\n---------------- Wrong choice🤕 ----------------\n");
            return -1;
        }
        return choice;
    }
}
