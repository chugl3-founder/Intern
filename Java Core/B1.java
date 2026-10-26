class Shape {
    protected double width;
    protected double height;

    public Shape(double width, double height){
        this.width = width;
        this.height = height;
    }
}

class Rectangle extends Shape {
    public Rectangle(double width, double height){
        super(width, height);
    }

    public double getArea(){
        return width*height;
    }

    public double getPerimeter(){
        return 2*(width + height);
    }

    public void display(){
        System.out.println("--- Hinh chu nhat ---");
        System.out.println("chieu rong: " + width + " | chieu dai: " + height);
        System.out.println("dien tich: " + getArea() + " | Chu vi: " + getPerimeter());
    }
}
class Circle extends Shape {

    public Circle(double diameter) {
        super(diameter, diameter);
    }

    public double getRadius() {
        return width / 2.0;
    }

    public double getArea() {
        double r = getRadius();
        return Math.PI * r * r;
    }

    public double getCircumference() {
        return width * 3.14; 
    }

    public void display() {
        System.out.println("--- hinh tron ---");
        System.out.println("Duong kinh: " + width + " | Ban kinh: " + getRadius());
        System.out.println("Dien tich: " + getArea() + " | Chu vi: " + getCircumference());
    }
}
public class B1 {
    public static void main(String[] args) {
        Rectangle rect = new Rectangle(5.0, 10.0);
        rect.display();

        System.out.println();

        Circle circle = new Circle(8.0);
        circle.display();
    }
}