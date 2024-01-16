import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    private static final int GAME_WIDTH = 1000;
    private static final int GAME_HEIGHT = (int) (GAME_WIDTH * 0.55555);
    private static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    private static final int BALL_DIAM = 20;
    private static final int PADDLE_WIDTH = 25;
    private static final int PADDLE_HEIGHT = 100;

    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddles paddle1, paddle2;
    Ball ball;
    Score score;
    GamePanel(){
newPaddles();
newBall();
score = new Score(GAME_WIDTH, GAME_HEIGHT);
this.setFocusable(true);
this.addKeyListener(new Action());
this.setPreferredSize(SCREEN_SIZE);

gameThread = new Thread(this);
gameThread.start();
    }
    public void newBall() {
    random = new Random();
    ball = new Ball(GAME_WIDTH/2 - BALL_DIAM/2, random.nextInt(GAME_HEIGHT - BALL_DIAM), BALL_DIAM, BALL_DIAM);

    }
    public void newPaddles() {
        paddle1 = new Paddles(0, GAME_HEIGHT/2 - PADDLE_HEIGHT/2, PADDLE_WIDTH, PADDLE_HEIGHT, 1);
   paddle2 = new Paddles(GAME_WIDTH - PADDLE_WIDTH, GAME_HEIGHT/2 - PADDLE_HEIGHT/2, PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }
    public void paint(Graphics g){
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);

    }
    public void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
         ball.draw(g);
         score.draw(g);
    }
    public void move() {
paddle1.move();
paddle2.move();
ball.move();
    }
    public void checkCollision() {
        //ball with paddels
        if(ball.intersects(paddle1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            if(ball.yVelocity>0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if(ball.intersects(paddle2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            if(ball.yVelocity>0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
//ball with a wall
        if(ball.y<0 || ball.y >=GAME_HEIGHT-BALL_DIAM) {
            ball.setYDirection(-ball.yVelocity);
        }

        //stops paddels at window edges
        if(paddle1.y <=0)
            paddle1.y=0;
if(paddle1.y>= (GAME_HEIGHT - PADDLE_HEIGHT))
    paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
//paddle 2
    if(paddle2.y <=0)
        paddle2.y=0;
    if(paddle2.y>= (GAME_HEIGHT - PADDLE_HEIGHT))
        paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;

    //points++ and new ball
        if(ball.x<=0) {
            score.player2++;
            newPaddles();
            newBall();
        } else if(ball.x>=GAME_WIDTH - BALL_DIAM) {
            score.player1++;
            newPaddles();
            newBall();
        }
    }
    public void run() {
    long lastTime = System.nanoTime();
    double amountOfTicks = 60.0;
    double ns = 1000000000 / amountOfTicks ;
    double delta = 0;
    while(true) {
        long now = System.nanoTime();
        delta+= (now - lastTime)/ns;
        lastTime = now;
        if(delta >=1) {
            move();
            checkCollision();
            repaint();
            delta--;
        }
    }
    }
    public class Action extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
