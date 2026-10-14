import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("1:Сложение, 2:Умножение, 3:Деление, 4:Транспонирование, 5:Определитель, 0:Выход\n: ");
            int ch;
            try {
                ch = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                continue;
            }
            if (ch == 0) {
                break;
            }

            try {
                switch (ch) {
                    case 1 -> {
                        readM(sc, "A").add(readM(sc, "B")).print();
                    }
                    case 2 -> {
                        readM(sc, "A").mul(readM(sc, "B")).print();
                    }
                    case 3 -> {
                        readM(sc, "A").div(readM(sc, "B")).print();
                    }
                    case 4 -> {
                        readM(sc, "").tr().print();
                    }
                    case 5 -> {
                        System.out.println(readM(sc, "").det().getText());
                    }
                    default -> System.out.println("Неверный выбор.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
        sc.close();
    }

    private static Matrix readM(Scanner sc, String name) {
        String label = name.isEmpty() ? "" : " " + name;
        System.out.println("Матрица" + label + ":");
        System.out.print("Строк: ");
        int r = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Столбцов: ");
        int c = Integer.parseInt(sc.nextLine().trim());
        
        Matrix m = new Matrix(r, c);
        System.out.println("Элементы (действительная часть [мнимая часть]):");
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print("[" + i + "," + j + "]: ");
                String[] p = sc.nextLine().trim().split("\\s+");
                double re = Double.parseDouble(p[0]);
                double im = p.length > 1 ? Double.parseDouble(p[1]) : 0.0;
                m.set(i, j, new Complex(re, im));
            }
        }
        return m;
    }
}
