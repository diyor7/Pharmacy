package uz.com;

import uz.com.Models.User;
import uz.com.Service.BranchService;
import uz.com.Service.OrganizationService;
import uz.com.Service.PharmacyService;
import uz.com.Service.UserService;

import java.util.Objects;
import java.util.Scanner;

import static utils.Color.*;
import static utils.Input.getStr;
import static utils.Print.print;
import static utils.Print.println;
import static uz.com.MyDb.db.*;


public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static User user = new User();
    public static void main(String[] args) {


        setMap(20, 20);
        supadmin();
        run();
    }

    private static void run() {
        menu();
        String choice = getStr("Select any option ✍ -> ");
        if(Objects.isNull(session) && choice.equals("1")) UserService.Login();
            //Super Admin
        else if(Objects.nonNull(session) && choice.equals("1") && session.getLevel() == 1) OrganizationService.createOrganization();
        else if(Objects.nonNull(session) && choice.equals("2") && session.getLevel() == 1) OrganizationService.blockOrganization();
        else if(Objects.nonNull(session) && choice.equals("3") && session.getLevel() == 1) OrganizationService.unblockOrganization();
        else if(Objects.nonNull(session) && choice.equals("4") && session.getLevel() == 1) OrganizationService.removeOrganization();
        else if(Objects.nonNull(session) && choice.equals("5") && session.getLevel() == 1) OrganizationService.organizationLocation();
        else if(Objects.nonNull(session) && choice.equals("6") && session.getLevel() == 1) OrganizationService.organizationList();
            //CEO
        else if(Objects.nonNull(session) && choice.equals("1") && session.getLevel() == 2) BranchService.createBranch();
        else if(Objects.nonNull(session) && choice.equals("2") && session.getLevel() == 2) BranchService.removeBranch();
        else if(Objects.nonNull(session) && choice.equals("3") && session.getLevel() == 2) BranchService.blockBranch();
        else if(Objects.nonNull(session) && choice.equals("4") && session.getLevel() == 2) BranchService.unblockBranch();
            //qo'shildi
        else if(Objects.nonNull(session) && choice.equals("5") && session.getLevel() == 2) UserService.managersList();
        else if(Objects.nonNull(session) && choice.equals("6") && session.getLevel() == 2) UserService.createManager();
        else if(Objects.nonNull(session) && choice.equals("7") && session.getLevel() == 2) UserService.removeManager();
        else if(Objects.nonNull(session) && choice.equals("8") && session.getLevel() == 2) UserService.blockManager();
        else if(Objects.nonNull(session) && choice.equals("9") && session.getLevel() == 2) UserService.unblockManager();
            //qo'shildi
        else if(Objects.nonNull(session) && choice.equals("10") && session.getLevel() == 2) UserService.employeesList();
        else if(Objects.nonNull(session) && choice.equals("11") && session.getLevel() == 2) UserService.createEmployee();
        else if(Objects.nonNull(session) && choice.equals("12") && session.getLevel() == 2) UserService.removeEmployee();
        else if(Objects.nonNull(session) && choice.equals("13") && session.getLevel() == 2) UserService.blockEmployee();
        else if(Objects.nonNull(session) && choice.equals("14") && session.getLevel() == 2) UserService.unblockEmployee();
            //Manager
        else if(Objects.nonNull(session) && choice.equals("1") && session.getLevel() == 3) UserService.createEmployee();
        else if(Objects.nonNull(session) && choice.equals("2") && session.getLevel() == 3) UserService.removeEmployee();
        else if(Objects.nonNull(session) && choice.equals("3") && session.getLevel() == 3) UserService.blockEmployee();
        else if(Objects.nonNull(session) && choice.equals("4") && session.getLevel() == 3) UserService.unblockEmployee();
        else if(Objects.nonNull(session) && choice.equals("5") && session.getLevel() == 3) PharmacyService.enterDrugs();
        else if(Objects.nonNull(session) && choice.equals("6") && session.getLevel() == 3) PharmacyService.removeDrugs();
        else if(Objects.nonNull(session) && choice.equals("7") && session.getLevel() == 3) PharmacyService.drugList();
        else if(Objects.nonNull(session) && choice.equals("8") && session.getLevel() == 3) BranchService.branchList();
        else if(Objects.nonNull(session) && choice.equals("9") && session.getLevel() == 3) PharmacyService.searchDrugOut();// filiallardan qidirish
            //Employee
        else if(Objects.nonNull(session) && choice.equals("1") && session.getLevel() == 4) PharmacyService.sellDrug();
        else if(Objects.nonNull(session) && choice.equals("2") && session.getLevel() == 4) PharmacyService.drugList();
        else if(Objects.nonNull(session) && choice.equals("3") && session.getLevel() == 4) PharmacyService.searchDrug();
        else if(Objects.nonNull(session) && choice.equals("4") && session.getLevel() == 4) PharmacyService.searchDrugOut();

        else  if(Objects.nonNull(session) && choice.equals("0")) UserService.LogOut();
        else if(choice.equals("100")) {
            print(PURPLE,"\n---------- 🙌🙌🙌 ");
            print(BLUE,"Come Back Anytime");
            print(PURPLE," 🙌🙌🙌 ----------\n");
            return;
        }
        else {
            println(RED,"\n🛢🛢-> Wrong choice <-🛢🛢\n");
        }

        run();
    }

    private static void menu() {
        if(Objects.nonNull(session)){
            if(session.getLevel() == 1){ // 1 - Super Admin
                println(CYAN,"1. Create Organization ");
                println(CYAN,"2. Block Organization ");
                println(CYAN,"3. Unblock Organization ");
                println(BLUE,"4. Remove Organization ");
                println(BLUE,"5. See Organizations Location ");
                println(BLUE,"6. Organization List ");
            }
            else if(session.getLevel() == 2){ // 2 - CEO
                println(CYAN,"1. Create Branch ");
                println(CYAN,"2. Remove Branch ");
                println(CYAN,"3. Block Branch ");
                println(CYAN,"4. Unblock Branch ");
                println(GREEN,"======▶ Manager Service ◀======");
                println(PURPLE,"5. Managers List ");
                println(PURPLE,"6. Create Manager ");
                println(PURPLE,"7. Remove Manager ");
                println(PURPLE,"8. Block Manager ");
                println(PURPLE,"9. Unblock Manager ");
                println(GREEN,"=====▶ Employee Service ◀=====");
                println(BLUE,"10. Employees List ");
                println(BLUE,"11. Create Employee ");
                println(BLUE,"12. Remove Employee ");
                println(BLUE,"13. Block Employee ");
                println(BLUE,"14. Unblock Employee ");
            }
            else if(session.getLevel() == 3){ // 3 - Manager
                println(CYAN,"1. Create Employee ");
                println(CYAN,"2. Remove Employee ");
                println(CYAN,"3. Block Employee ");
                println(CYAN,"4. Unblock Employee ");
                println(BLUE,"5. Enter Drugs ");
                println(BLUE,"6. Remove Drugs ");
                println(BLUE,"7. Drug List ");
                println(BLUE,"8. Branch List ");
                println(BLUE,"9. Search Drug  ");

            }
            else if(session.getLevel() == 4){ // 4 - Employee
                println(CYAN,"1. Sell Drug ");
                println(CYAN,"2. Drug List ");
                println(CYAN,"3. Search Drug ");
                println(CYAN,"4. Search Drug from Branches ");
            }
            println(YELLOW,"0. Logout ");

        }else {
            println("1. Login ...");
        }
        print(YELLOW,"100. Exit ");
        println(RED,"🚩...");

    }

}