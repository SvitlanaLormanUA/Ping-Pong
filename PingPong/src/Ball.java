import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle{
    int xVelocity, yVelocity;
    Random random;
    int speed = 3;
    Ball(int x, int y, int width, int height) {
        super(x,y,width,height);
        random = new Random();
        int randomXDirection = random.nextInt(2,3);
     if(randomXDirection == 2) {
         randomXDirection--;
         setXDirection(randomXDirection*speed);
     }
        int randomYDirection = random.nextInt(2);
        if(randomYDirection == 2) {
            randomYDirection--;
            setYDirection(randomYDirection*speed);
        }
        }


    public void setYDirection(int randomYDirection) {
        yVelocity = randomYDirection;

    }
    public void setXDirection(int randomXDirection) {
xVelocity = randomXDirection;
    }
    public void move(){
x += xVelocity;
y += yVelocity;
    }
    public void draw(Graphics g){
g.setColor(Color.white);
g.fillOval(x, y, width, height);
    }
}
