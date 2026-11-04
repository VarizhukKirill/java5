import java.io.*;
import java.util.*;

// базовий абстрактний клас Worker
abstract class Worker implements Comparable<Worker> {
    protected String name;
    protected double salary;

    public Worker(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void show() {
        System.out.println("Ім'я: " + name + " | Зарплата: " + salary + " грн");
    }

    // За замовчуванням порівняння за зарплатою (Завдання 1)
    @Override
    public int compareTo(Worker o) {
        return Double.compare(this.salary, o.salary);
    }

    // Для зручного збереження у файл
    public abstract String toFileString();
}

// Похідні класи 
class Personnel extends Worker {
    private String department;

    public Personnel(String name, double salary, String department) {
        super(name, salary);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public void show() {
        System.out.println("[Кадри] Ім'я: " + name + " | Зарплата: " + salary + " грн | Відділ: " + department);
    }

    @Override
    public String toFileString() {
        return name + "," + salary + "," + department;
    }
}

class Engineer extends Worker {
    private String specialization;

    public Engineer(String name, double salary, String specialization) {
        super(name, salary);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public void show() {
        System.out.println("[Інженер] Ім'я: " + name + " | Зарплата: " + salary + " грн | Спеціалізація: " + specialization);
    }

    @Override
    public String toFileString() {
        return name + "," + salary + "," + specialization;
    }
}

class Administration extends Worker {
    private String position;

    public Administration(String name, double salary, String position) {
        super(name, salary);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public void show() {
        System.out.println("[Адміністрація] Ім'я: " + name + " | Зарплата: " + salary + " грн | Посада: " + position);
    }

    @Override
    public String toFileString() {
        return name + "," + salary + "," + position;
    }
}

//  компаратор для comparator завдання 1 
// Порівнює об'єкти Worker за ім'ям за алфавітом (якщо імена однакові — за зарплатою)
class WorkerNameComparator implements Comparator<Worker> {
    @Override
    public int compare(Worker w1, Worker w2) {
        int nameCompare = w1.getName().compareToIgnoreCase(w2.getName());
        if (nameCompare != 0) {
            return nameCompare;
        }
        return Double.compare(w1.getSalary(), w2.getSalary());
    }
}

// головний клас для виконання завдань
public class Main {
    private static final String PERSONNEL_FILE = "personnel.txt";
    private static final String ENGINEER_FILE = "engineers.txt";
    private static final String ADMIN_FILE = "admins.txt";
    private static final String ALL_SORTED_FILE = "sorted_workers.txt";

    public static void main(String[] args) {
        // Створюємо тестові файли перед запуском, якщо їх немає
        createSampleFiles();

        Scanner scanner = new Scanner(System.in);

        // завдання 1
        System.out.println("================ ЗАВДАННЯ 1 ================");

        // 1 Читання даних з окремих файлів у колекції
        ArrayList<Personnel> personnelList = readPersonnelFromFile(PERSONNEL_FILE);
        ArrayList<Engineer> engineerList = readEngineersFromFile(ENGINEER_FILE);
        ArrayList<Administration> adminList = readAdminsFromFile(ADMIN_FILE);

        System.out.println("\n---> Прочитані дані з файлів:");
        printCollection("Кадри:", personnelList);
        printCollection("Інженери:", engineerList);
        printCollection("Адміністрація:", adminList);

        // 2 Сортування за допомогою Comparable (за зростанням зарплати)
        Collections.sort(personnelList);
        Collections.sort(engineerList);
        Collections.sort(adminList);

        System.out.println("\n---> Відсортовані дані (за зарплатою через Comparable):");
        printCollection("Кадри (сорт.):", personnelList);
        printCollection("Інженери (сорт.):", engineerList);
        printCollection("Адміністрація (сорт.):", adminList);

        // 3 Додавання елементів з клавіатури
        System.out.println("\n---> Додавання нових записів з клавіатури:");
        
        System.out.print("Введіть ім'я кадровика: ");
        String pName = scanner.nextLine();
        System.out.print("Введіть зарплату кадровика: ");
        double pSalary = Double.parseDouble(scanner.nextLine());
        System.out.print("Введіть відділ: ");
        String pDept = scanner.nextLine();
        personnelList.add(new Personnel(pName, pSalary, pDept));

        System.out.print("\nВведіть ім'я інженера: ");
        String eName = scanner.nextLine();
        System.out.print("Введіть зарплату інженера: ");
        double eSalary = Double.parseDouble(scanner.nextLine());
        System.out.print("Введіть спеціалізацію: ");
        String eSpec = scanner.nextLine();
        engineerList.add(new Engineer(eName, eSalary, eSpec));

        System.out.print("\nВведіть ім'я адміністратора: ");
        String aName = scanner.nextLine();
        System.out.print("Введіть зарплату адміністратора: ");
        double aSalary = Double.parseDouble(scanner.nextLine());
        System.out.print("Введіть посаду: ");
        String aPos = scanner.nextLine();
        adminList.add(new Administration(aName, aSalary, aPos));

        // 4. Повторне сортування та виведення
        Collections.sort(personnelList);
        Collections.sort(engineerList);
        Collections.sort(adminList);

        System.out.println("\n---> Дані після додавання та повторного сортування:");
        printCollection("Кадри:", personnelList);
        printCollection("Інженери:", engineerList);
        printCollection("Адміністрація:", adminList);

        // 5. Опис ArrayList на основі абстрактного класу Worker
        ArrayList<Worker> allWorkers = new ArrayList<>();
        allWorkers.addAll(personnelList);
        allWorkers.addAll(engineerList);
        allWorkers.addAll(adminList);

        // 6. Сортування списку абстрактного класу за допомогою Comparator (за ім'ям)
        Collections.sort(allWorkers, new WorkerNameComparator());

        System.out.println("\n---> Об'єднаний список усіх працівників (відсортований за ім'ям через Comparator):");
        for (Worker w : allWorkers) {
            w.show();
        }

        // 7. Запис відсортованого ArrayList у файл
        saveWorkersToFile(ALL_SORTED_FILE, allWorkers);
        System.out.println("Загальний відсортований список збережено у файл: " + ALL_SORTED_FILE);


        // завадання 2
        System.out.println("\n================ ЗАВДАННЯ 2 ================");
        
        // Створення стеку з номерами записів (індексами від 0 до N-1)
        Stack<Integer> indexStack = new Stack<>();
        for (int i = 0; i < allWorkers.size(); i++) {
            indexStack.push(i);
        }

        System.out.println("Стек номерів записів створено. Розмір стеку: " + indexStack.size());

        // Організація прямого доступу до елементів запису за номерами зі стеку
        System.out.println("\n---> Прямий доступ до елементів за допомогою номерів зі стеку:");
        
        // Витягуємо номери зі стеку та одразу звертаємося напряму до списку за індексом (allWorkers.get(index))
        while (!indexStack.isEmpty()) {
            int recordIndex = indexStack.pop();
            System.out.print("Запис №" + recordIndex + " -> ");
            allWorkers.get(recordIndex).show(); // Прямий доступ за індексом O(1)
        }
    }

    // допоміжні методи

    private static void printCollection(String title, List<? extends Worker> list) {
        System.out.println(title);
        for (Worker w : list) {
            w.show();
        }
    }

    private static ArrayList<Personnel> readPersonnelFromFile(String fileName) {
        ArrayList<Personnel> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    list.add(new Personnel(parts[0].trim(), Double.parseDouble(parts[1].trim()), parts[2].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Помилка читання " + fileName + ": " + e.getMessage());
        }
        return list;
    }

    private static ArrayList<Engineer> readEngineersFromFile(String fileName) {
        ArrayList<Engineer> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    list.add(new Engineer(parts[0].trim(), Double.parseDouble(parts[1].trim()), parts[2].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Помилка читання " + fileName + ": " + e.getMessage());
        }
        return list;
    }

    private static ArrayList<Administration> readAdminsFromFile(String fileName) {
        ArrayList<Administration> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    list.add(new Administration(parts[0].trim(), Double.parseDouble(parts[1].trim()), parts[2].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Помилка читання " + fileName + ": " + e.getMessage());
        }
        return list;
    }

    private static void saveWorkersToFile(String fileName, List<Worker> list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Worker w : list) {
                writer.println(w.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Помилка збереження у файл " + fileName + ": " + e.getMessage());
        }
    }

    // Створення тестових текстових файлів, якщо вони не існують
    private static void createSampleFiles() {
        try {
            File f1 = new File(PERSONNEL_FILE);
            if (!f1.exists()) {
                PrintWriter p1 = new PrintWriter(f1);
                p1.println("Олена, 18000, HR");
                p1.println("Марія, 15000, Рекрутинг");
                p1.close();
            }

            File f2 = new File(ENGINEER_FILE);
            if (!f2.exists()) {
                PrintWriter p2 = new PrintWriter(f2);
                p2.println("Олександр, 35000, Java Dev");
                p2.println("Дмитро, 28000, DevOps");
                p2.close();
            }

            File f3 = new File(ADMIN_FILE);
            if (!f3.exists()) {
                PrintWriter p3 = new PrintWriter(f3);
                p3.println("Василь, 45000, Директор");
                p3.println("Ірина, 30000, Менеджер");
                p3.close();
            }
        } catch (Exception e) {
            System.out.println("Помилка створення початкових файлів.");
        }
    }
}