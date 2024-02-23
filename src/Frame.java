import java.awt.Color;
import javax.swing.*;
public class Frame extends JFrame
{
    Frame()
    {
        this.setName("Particle_Swarm_Optimisation");
        this.setSize(619,703);
        
        this.getContentPane().setBackground(Color.BLACK);
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        My_panel my_panel = new My_panel();
        this.add(my_panel);
        
    }
}
