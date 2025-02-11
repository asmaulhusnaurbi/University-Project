import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends JPanel  implements ActionListener, KeyListener{

    int boardWidth = 360;
    int boardHeight = 640;

    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    int birdx = boardWidth / 8;
    int birdy = boardHeight / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    SoundManager soundManager;
    class Bird {
        int x = birdx;
        int y = birdy;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    // pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        pipe(Image img) {
            this.img = img;
        }
    }

    // Game logic
    Bird bird;
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;

    ArrayList<pipe> pipes;
    Random random = new Random();

    Timer gameloop;
    Timer placePipesTimer;
    boolean gameOver = false;
    double score = 0;

    // Constructor
    FlappyBird() {

        setPreferredSize(new Dimension(boardWidth, boardHeight));

        setFocusable(true);
        addKeyListener(this);

        backgroundImg = new ImageIcon(getClass().getResource("/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("/bottompipe.png")).getImage();

        // bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<pipe>();

        // Initialize SoundManager
        soundManager = new SoundManager();
        soundManager.loadBackgroundMusic();
       
         
       

        // place pipes timer
        placePipesTimer = new Timer(1500, new ActionListener() 
		{
            @Override
            public void actionPerformed(ActionEvent e) 
			{
                placePipes();
            }
        });

        // pipe timer
        placePipesTimer.start();

        // game timer
        gameloop = new Timer(1000 / 60, this);
        gameloop.start();
    }

    public void placePipes() 
	{
        int randompipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));

        int openingSpace = boardHeight / 4;

        pipe toppipe = new pipe(topPipeImg);
        toppipe.y = randompipeY;
        pipes.add(toppipe);

        pipe bottompipe = new pipe(bottomPipeImg);
        bottompipe.y = toppipe.y + pipeHeight + openingSpace;
        pipes.add(bottompipe);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

       
        g.drawImage(backgroundImg, 0, 0, this.boardWidth, this.boardHeight, null);
       
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        
        for (int i = 0; i < pipes.size(); i++) {
            pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 34));
        if (gameOver) 
		{
            g.drawString("Game Over", 80, 35);
        } 
		else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    public void move() {
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        // pipes
        for (int i = 0; i < pipes.size(); i++) {
            pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width) 
			{
                pipe.passed = true;
                score += 0.5;
            }
            if (collision(bird, pipe)) 
			{
                gameOver = true;
               
            }
        }
        if (bird.y > boardHeight) 
		{
            gameOver = true;
            
        }
    }

    public boolean collision(Bird a, pipe b) 
	{
        return a.x < b.x + b.width &&   // a's top left corner doesn't reach b's top right corner
                a.x + a.width > b.x &&   // a's top right corner passes b's top left corner
                a.y < b.y + b.height &&  // a's top left corner doesn't reach b's bottom left corner
                a.y + a.height > b.y;    // a's bottom left corner passes b's top left corner
    }

    public void restartGame() {
        bird.y = birdy;
        velocityY = 0;
        pipes.clear();
        score = 0;
        gameOver = false;
        soundManager.loadBackgroundMusic();  
        placePipesTimer.start();
        gameloop.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            placePipesTimer.stop();
            soundManager.stopBackgroundMusic();
            gameloop.stop();
            

            int option = JOptionPane.showConfirmDialog(this,
                    "Game Over! Your Score is: " + (int) score + " Do you want to restart?",
                    "GAME OVER", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                restartGame();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) 
	{
        if (e.getKeyCode() == KeyEvent.VK_SPACE) 
		{
            velocityY = -9;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }
}
