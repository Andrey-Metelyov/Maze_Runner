import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        int hoursStart = scanner.nextInt();
        int minutesStart = scanner.nextInt();
        int secondsStart = scanner.nextInt();

        int hoursEnd = scanner.nextInt();
        int minutesEnd = scanner.nextInt();
        int secondsEnd = scanner.nextInt();

        System.out.println(
                (hoursEnd - hoursStart) * 3600 +
                        (minutesEnd - minutesStart) * 60 +
                        secondsEnd - secondsStart);
    }
}