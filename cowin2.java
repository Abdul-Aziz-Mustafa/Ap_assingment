import java.util.*;

class vaccine {

    String name;
    int doses;
    int gap;

    public vaccine(String name, int doses, int gap) {
        this.name = name;
        this.doses = doses;
        this.gap = gap;
        System.out.println("Vaccine Name: " + name + ", Number of Doses: " + doses + ", Gap Between Doses: " + gap);

    }

    public vaccine(String name, int doses) {
        this.name = name;
        this.doses = doses;
        this.gap = 0;
        System.out.println("Vaccine Name: " + name + ", Number of Doses: " + doses + ", Gap Between Doses: " + gap);
    }
}

class hospital {
    String name;
    long pincode;
    long uid;

    public hospital(String name, long pincode) {
        this.name = name;
        this.pincode = pincode;
        Random rnd = new Random();
        int max = 999999;
        int min = 99999;
        this.uid = rnd.nextInt((max - min) + 1) + min;
        System.out.println("Hospital Name: " + name + " ,PinCode: " + pincode + " ,Unique ID: " + uid);
    }

}

class citizen {
    String name;
    int age;
    String userid;
    int duedate;
    String Vaccinegiven;
    int number_of_doses_given = 0;
    int number_of_doses_remaining;

    public citizen(String name, int age, String userid) {
        this.name = name;
        this.age = age;
        this.userid = userid;
        System.out.println("Citizen Name:" + name + " Age:" + age + " Unique ID:" + userid);

    }

}

class slot {
    int dayno;
    int quantity;
    String vaccinename;

    slot(int dayno, int quantity, String vaccinename) {
        this.dayno = dayno;
        this.quantity = quantity;
        this.vaccinename = vaccinename;

    }

}

public class cowin2 {

    static ArrayList<hospital> hospitalArrayList = new ArrayList<>();
    static TreeMap<Integer, vaccine> vaccinelist = new TreeMap<Integer, vaccine>();
    static TreeMap<String, citizen> citizenMap = new TreeMap<String, citizen>();
    static ArrayList<slot> slotdata = new ArrayList<>();
    static TreeMap<Long, ArrayList<slot>> slotMap = new TreeMap<Long, ArrayList<slot>>();
    static Set<String> vaccines = new HashSet<String>();
    static int i = 0;
    static Scanner sci = new Scanner(System.in);
    static Scanner scs = new Scanner(System.in);
    static Scanner scl = new Scanner(System.in);

    static void add_vaccine() {
        // int i = 0;

        System.out.print("vaccine name: ");
        String name = scs.nextLine();
        System.out.print("number of dose: ");
        int dose = sci.nextInt();
        if (dose > 1) {

            System.out.print("Gap between doses: ");

            int gap = sci.nextInt();
            vaccines.add(name);
            vaccinelist.put(i, new vaccine(name, dose, gap));
        } else {
            vaccines.add(name);
            vaccinelist.put(i, new vaccine(name, dose));

        }
        // System.out.println(vaccinelist);
        i++;

    }

    static void register_hospital() {

        System.out.print("hopital name: ");

        String hospitalname = scs.nextLine();
        System.out.print("PinCode: ");

        long pincode = scl.nextLong();

        hospitalArrayList.add(new hospital(hospitalname, pincode));

    }

    static void register_citizen() {

        System.out.print("Citizen name:");

        String cname = scs.nextLine();
        System.out.print("age:");

        int age = sci.nextInt();
        System.out.println("enter user id");

        String userid = scs.nextLine();
        citizenMap.put(userid, new citizen(cname, age, userid));
        if (age < 18) {
            System.out.println("Only above 18 are allowed");
        }

    }

    static void add_slot() {

        System.out.print("Enter Hospital ID:");
        long hid = scl.nextLong();// hospital id

        for (int i = 0; i < hospitalArrayList.size(); i++) {
            if (hid == hospitalArrayList.get(i).uid) {

                System.out.print("Enter number of Slots to be added:");

                int number_of_slots = sci.nextInt();

                for (int it = 0; it < number_of_slots; it++) {
                    System.out.print("Enter Day Number: ");
                    int dayno = sci.nextInt();
                    System.out.print("enter quantity:");

                    int quantity = sci.nextInt();
                    if (!vaccinelist.isEmpty()) {
                        System.out.println("Select Vaccine ");

                        for (Map.Entry<Integer, vaccine> entry : vaccinelist.entrySet()) {
                            Integer k = entry.getKey();
                            vaccine v = entry.getValue();
                            System.out.println(k + ". " + v.name);
                        }
                        Integer choice = sci.nextInt();
                        String vaccinename = vaccinelist.get(choice).name;
                        if (vaccines.contains(vaccinename)) {

                            slotdata.add(new slot(dayno, quantity, vaccinename));
                            System.out.println("Slot added by Hospital " + hid + " for Day:" + dayno
                                    + ", Available Quantity: " + quantity + " of Vaccine: " + vaccinename);
                        } else {
                            System.out.println("false input");
                        }
                    } else {
                        System.out.println("no vaccine available");
                    }
                }

                slotMap.put(hid, slotdata);
                return;
            }
        }
        System.out.println("hospital number not in record");

    }

    static void checkstats() {

        System.out.print("Enter patient Unique ID:");
        String userid = scs.nextLine();
        for (Map.Entry<String, citizen> citizenentry : citizenMap.entrySet()) {
            String mankey = citizenentry.getKey();
            citizen manvalue = citizenentry.getValue();
            if (mankey.equals(userid)) {
                if (manvalue.number_of_doses_given == 0) {
                    System.out.println("citizen registered");

                } else if (manvalue.number_of_doses_remaining == 0) {
                    System.out.println("fully vaccinted");
                } else {
                    System.out.println("partailly vaccinated");
                    System.out.println("due date:" + manvalue.duedate);
                }
                System.out.println("Vaccine Given: " + manvalue.Vaccinegiven);
                System.out.println("Number of doses given " + manvalue.number_of_doses_given);

            }

        }

    }

    static void bookslot() {

        System.out.print("Enter patient Unique ID:");
        String adhar = scs.nextLine();
        boolean c = false;

        for (Map.Entry<String, citizen> rentry : citizenMap.entrySet()) {
            String rkey = rentry.getKey();
            citizen rvalue = rentry.getValue();
            if (adhar.equals(rkey)) {
                c = true;

                System.out.println("""
                        1. Search by area
                        2. Search by Vaccine
                        3. Exit""");
                System.out.print("Enter option:");
                int c2 = sci.nextInt();
                switch (c2) {
                    case 1:
                        System.out.print("enter PINcode:");
                        long pc = scl.nextLong();
                        boolean rc = false;
                        for (int iter = 0; iter < hospitalArrayList.size(); iter++) {
                            if (pc == (long) hospitalArrayList.get(iter).pincode) {
                                rc = true;
                                System.out.println(
                                        hospitalArrayList.get(iter).uid + " " + hospitalArrayList.get(iter).name);
                            }
                        }
                        if (rc) {
                            System.out.print("hospital id:");
                            long hosid = scl.nextLong();
                            boolean checker = false;
                            for (Map.Entry<Long, ArrayList<slot>> entry : slotMap.entrySet()) {
                                checker = true;
                                boolean slotc = false;
                                Long key = entry.getKey();
                                ArrayList<slot> value = entry.getValue();
                                // System.out.println(key + " " + value);
                                if ((long) key == (long) hosid) {

                                    for (int var = 0; var < slotdata.size(); var++) {
                                        if (value.get(var).quantity != 0 && key == (long) hosid) {
                                            System.out.println(var + "-> Day: " + value.get(var).dayno
                                                    + "  Available quantity: " + value.get(var).quantity + " vaccine:"
                                                    + value.get(var).vaccinename);
                                            slotc = true;
                                        }
                                    }
                                    if (!slotc) {
                                        System.out.println("no slots available");
                                    }
                                    if (slotc) {

                                        System.out.println("choose slot");
                                        int slotchoice = sci.nextInt();

                                        for (Map.Entry<Integer, vaccine> ventry : vaccinelist.entrySet()) {

                                            Integer vkey = ventry.getKey();
                                            vaccine vvalue = ventry.getValue();
                                            if (value.get(slotchoice).vaccinename.equals((String) vvalue.name)) {
                                                rvalue.number_of_doses_given = rvalue.number_of_doses_given + 1;
                                                rvalue.number_of_doses_remaining = vvalue.doses
                                                        - rvalue.number_of_doses_given;
                                                rvalue.duedate = value.get(slotchoice).dayno + vvalue.gap;
                                                rvalue.Vaccinegiven = value.get(slotchoice).vaccinename;

                                            }

                                            value.get(slotchoice).quantity = value.get(slotchoice).quantity - 1;

                                            System.out.println(rvalue.name + " vaccinated with "
                                                    + value.get(slotchoice).vaccinename);
                                        }
                                    }
                                }

                            }
                        }
                        if (!rc) {
                            System.out.println("wrong uid");
                        }

                        break;
                    case 2:
                        boolean rc2 = false;
                        System.out.println("enter vaccine name");
                        String givevaccinename = scs.nextLine();
                        for (Map.Entry<Long, ArrayList<slot>> entry : slotMap.entrySet()) {
                            Long key = entry.getKey();
                            ArrayList<slot> value = entry.getValue();
                            for (int var = 0; var < slotdata.size(); var++) {
                                if (value.get(var).vaccinename.equals(givevaccinename)) {
                                    for (int iter = 0; iter < hospitalArrayList.size(); iter++) {
                                        if (key == (long) hospitalArrayList.get(iter).uid) {
                                            rc2 = true;
                                            System.out.println(hospitalArrayList.get(iter).uid + " "
                                                    + hospitalArrayList.get(iter).name);
                                        }
                                    }

                                }

                            }
                        }
                        if (rc2 == true) {
                            System.out.println("hospital id");
                            long hosid2 = scl.nextLong();
                            for (Map.Entry<Long, ArrayList<slot>> entry : slotMap.entrySet()) {
                                Long key = entry.getKey();
                                ArrayList<slot> value = entry.getValue();
                                System.out.println(key + " " + value);
                                boolean checker2 = false;
                                if ((long) key == (long) hosid2) {
                                    checker2 = true;
                                    boolean slotcc = false;
                                    for (int var = 0; var < slotdata.size(); var++) {
                                        if (value.get(var).vaccinename.equals(givevaccinename)) {

                                            if (value.get(var).quantity != 0 && key == (long) hosid2) {
                                                System.out.println(var + "-> Day:" + value.get(var).dayno
                                                        + "  Available quantity:" + value.get(var).quantity
                                                        + " vaccine:" + value.get(var).vaccinename);
                                                slotcc = true;
                                            }

                                        }

                                    }
                                    if (!slotcc) {
                                        System.out.println("NO SLOT AVAILABLE");

                                    }
                                    if (slotcc) {

                                        System.out.print("choose slot:");
                                        int slotchoice = sci.nextInt();
                                        if (rvalue.number_of_doses_remaining > 0) {
                                            for (Map.Entry<Integer, vaccine> ventry : vaccinelist.entrySet()) {
                                                Integer vkey = ventry.getKey();
                                                vaccine vvalue = ventry.getValue();
                                                if (value.get(slotchoice).vaccinename.equals((String) vvalue.name)) {
                                                    rvalue.number_of_doses_given = rvalue.number_of_doses_given + 1;
                                                    rvalue.number_of_doses_remaining = vvalue.doses
                                                            - rvalue.number_of_doses_given;
                                                    rvalue.duedate = value.get(slotchoice).dayno + vvalue.gap;
                                                    rvalue.Vaccinegiven = value.get(slotchoice).vaccinename;

                                                }
                                            }
                                            value.get(slotchoice).quantity = value.get(slotchoice).quantity - 1;
                                            System.out.println(rvalue.name + " vaccinated with "
                                                    + value.get(slotchoice).vaccinename);
                                        }
                                    }
                                }
                                if (!checker2) {
                                    System.out.println("wrong uid");
                                }

                            }
                        }
                        break;

                    default:
                        break;
                }
            }

        }
        if (c == false) {
            System.out.println("Not found in records");
        }

    }

    static void list_all_slots() {

        System.out.print("enter hospital id: ");
        long hid = scl.nextLong();
        boolean c = false;

        for (Map.Entry<Long, ArrayList<slot>> entry : slotMap.entrySet()) {
            Long k = entry.getKey();
            ArrayList<slot> v = entry.getValue();
            // System.out.println(k);
            // System.out.println(hid);
            if (k == hid) {
                c = true;
                // System.out.println("lol");
                for (int i = 0; i < v.size(); i++) {
                    System.out.println("Day:" + v.get(i).dayno + " vaccine: " + v.get(i).vaccinename + "  quantity: "
                            + v.get(i).quantity);

                }
            }

        }
        if (c == false) {
            System.out.println("wrong id or no record");
        }

    }

    public static void main(String[] args) {

        int c;
        while (true) {
            System.out.println("""

                        CoWin Portal initialized....
                    ---------------------------------
                    1. Add Vaccine
                    2. Register Hospital
                    3. Register Citizen
                    4. Add Slot for Vaccination
                    5. Book Slot for Vaccination
                    6. List all slots for a hospital
                    7. Check Vaccination Status
                    8. Exit
                    ---------------------------------
                            """);
            System.out.println("enter menu choice");
            c = sci.nextInt();

            switch (c) {
                case 1:
                    // add vaccine
                    add_vaccine();
                    break;

                case 2:
                    // register hospital
                    register_hospital();
                    break;
                case 3:

                    register_citizen();

                    break;
                case 4:
                    add_slot();

                    break;
                case 5:

                    bookslot();
                    break;
                case 6:
                    list_all_slots();
                    break;
                case 7:
                    checkstats();
                    break;

                case 8:
                    System.out.println("Exiting Program...");
                    System.exit(0);
                    break;
            }
        }

    }
}
