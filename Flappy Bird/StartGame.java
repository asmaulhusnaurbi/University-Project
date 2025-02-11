import javax.swing.*;

public class StartGame extends JFrame 
{
    private FlappyBird gamePanel;

    public StartGame() 
	{
        setTitle("Flappy Bird");
        setSize(360, 640);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gamePanel = new FlappyBird();
        add(gamePanel);

    }

    public static void main(String[] args) 
	{
        
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);
    }
}
