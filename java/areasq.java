import java.util.Scanner;
public class areasq{
    public static void main(String[] args) {
    Scanner sc=new Scanner(System.in);
    int area,side;
System.out.println("Enter side");
side= sc.nextInt();
area= side*side;
System.out.println("Area of square="+area);
    }
}