package uz.com.Service;

import uz.com.Models.Branch;
import uz.com.Models.Drug;
import uz.com.Models.User;

import java.util.ArrayList;

import static utils.BaseUtil.checkChoice;
import static utils.Color.*;
import static utils.Input.*;
import static utils.Print.*;
import static uz.com.MyDb.db.*;
import static uz.com.Service.BranchService.branchList;

public class PharmacyService {
    private static ArrayList<Drug> drugs = null;

    public static void enterDrugs() {
        branchList();
        int index = getNum("Choose branch: ") - 1;
        if (checkChoice(index, 0, session.getBranchesID().size() - 1)) {
            String branchId = session.getBranchesID().get(index);
            String drugName = getStr("Drugname: ");
            String description = getStr("Description: ");
            long code = getLong("Barcode: ");
            double price = getDouble("Price: ");
            int amount = getNum("Amount: ");
            Branch branch = UserService.getBranch(branchId, sessionOrg);
            drugs = branch.getDrugs();
            for (Drug drug : drugs) {
                if (code == drug.getCode() && drugName.equals(drug.getName())) {
                    drug.setAmount(drug.getAmount() + amount);
                    drug.setDescription(description);
                    drug.setPrice(price);
                    println(YELLOW, "Drugs with this code exist in this branch and updated successfully");
                    return;
                }
            }
            drugs.add(new Drug(drugName, description, price, branchId, amount, code));
            println(GREEN, "Drug successfully added!");
        } else {
            println(RED, "Wrong choice!");
        }
    }

    private static double getDouble(String text) {
        boolean isDigit = false;
        String temp = null;
        while (!isDigit) {
            temp = getStr(text);
            int dotCount = 0;
            isDigit = true;
            for (int i = 0; i < temp.length(); i++) {
                if (!Character.isDigit(temp.charAt(i)) && temp.charAt(i) != '.') {
                    isDigit = false;
                    break;
                }
                if (temp.charAt(i) == '.') {
                    dotCount++;
                    if (dotCount == 2) {
                        isDigit = false;
                        break;
                    }
                }
            }
            if (!isDigit) println(RED, "Wrong choice! Please enter a number");
        }
        return Double.parseDouble(temp);

    }

    private static long getLong(String text) {
        String temp;
        while (true) {
            temp = getStr(text);
            boolean isLong = true;
            for (int i = 0; i < temp.length(); i++) {
                if (!Character.isDigit(temp.charAt(i))) {
                    isLong = false;
                    println(RED, "Wrong choice! Please enter a number");
                    break;
                }
            }
            if (isLong) break;
        }
        return Long.parseLong(temp);

    }

    public static void removeDrugs() {
        drugList();
        int index = getNum("Choose drug: ") - 1;
        if (checkChoice(index, 0, drugs.size() - 1)) {
            Drug drug = drugs.get(index);
            drugs.remove(drug);
            println(GREEN, "Drug is deleted!");
        } else {
            println(RED, "Wrong choice!");
        }
    }

    public static void drugList() {
        Branch branch = null;
        int index = 0;
        if (session.getLevel() == 3) {
            branchList();
            index = getNum("Choose branch: ") - 1;
            if (!checkChoice(index, 0, session.getBranchesID().size() - 1)) {
                println(RED, "Wrong choice!");
                return;
            }
        }
        String branchId = session.getBranchesID().get(index);
        branch = UserService.getBranch(branchId, sessionOrg);
        drugs = branch.getDrugs();
        int i = 1;
        for (Drug drug : drugs) {
            println(YELLOW, String.format("%s. Name: %s, " +
                            "Price: %s, " +
                            "Amount: %s," +
                            "\n\tDescription: %s\n ",
                    i++,
                    drug.getName(),
                    drug.getPrice(),
                    drug.getAmount(),
                    drug.getDescription()));
        }
    }

    public static void searchDrugOut() {
        String fragment = getStr("Search: ");
        while (true) {
            if (fragment.length() < 6) {
                println(RED, "Please enter at least 6 characters");
                fragment = getStr("Search: ");
            } else break;
        }
        for (Branch branch : sessionOrg.getBranches()) {
            drugs = branch.getDrugs();
            for (Drug drug : drugs) {
                int i = 1;
                boolean doesExist = false;
                if (String.valueOf(drug.getCode()).contains(fragment)) doesExist = true;
                if (drug.getName().contains(fragment)) doesExist = true;
                if (drug.getDescription().contains(fragment)) doesExist = true;
                if (doesExist) {
                    println(YELLOW, String.format("%s. Branch: %s" +
                                    "Drugname: %s, " +
                                    "Price: %s, " +
                                    "Amount: %s," +
                                    "\n\tDescription: %s\n ",
                            i++,
                            branch.getName(),
                            drug.getName(),
                            drug.getPrice(),
                            drug.getAmount(),
                            drug.getDescription()));
                }
            }
        }


    }

    public static void sellDrug() {
        drugList();
        int index = getNum("Choose drug: ") - 1;
        if (checkChoice(index, 0, drugs.size() - 1)) {
            Drug drug = drugs.get(index);
            drug.setAmount(drug.getAmount() - 1);
            if (drug.getAmount() == 0) {
                drugs.remove(drug);
            }
            println(GREEN, "Drug is sold");
        } else {
            println(RED, "Wrong choice!");
        }
    }

    public static void searchDrug() {
        Branch branch = UserService.getBranch(session.getBranchesID().get(0), sessionOrg);
        drugs = branch.getDrugs();
        String fragment = getStr("Search: ");
        while (true) {
            if (fragment.length() < 6) {
                println(RED, "Please enter at least 6 characters");
                fragment = getStr("Search: ");
            } else break;
        }
        for (Drug drug : drugs) {
            int i = 1;
            boolean doesExist = false;
            if (String.valueOf(drug.getCode()).contains(fragment)) doesExist = true;
            if (drug.getName().contains(fragment)) doesExist = true;
            if (drug.getDescription().contains(fragment)) doesExist = true;
            if (doesExist) {
                println(YELLOW, String.format("%s. Name: %s, " +
                                "Price: %s, " +
                                "Amount: %s," +
                                "\n\tDescription: %s\n ",
                        i++,
                        drug.getName(),
                        drug.getPrice(),
                        drug.getAmount(),
                        drug.getDescription()));
            }
        }
    }
}
