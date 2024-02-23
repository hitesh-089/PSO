import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

public class My_panel extends JPanel implements ActionListener {
    private int point[][];
    private int particles = 100;
    private boolean running=false;
    private double position[][],global_best[],fx[],personal_best[][],velocity[][];
    private Timer timer;
    private long iterations = 500;
    private double cognitive = 2, social=2, w =1, r1,r2;
    private int max_vel=1,min_vel=0,max_pos=10,min_pos=-10;
    private double min=999999;
    private int pos=-1;
    private int count=0;
    private Random random=new Random();
    My_panel()
    {
        super();
        this.setBounds(0,0, 618, 703);
        this.setLayout(null);
        this.setBackground(Color.black);
        initialize();
        running =true;
        timer = new Timer(100, this);
        timer.start();
    }
    public void initialize()
    {
      point =new int[particles][2];
      position = new double[particles][2];
      global_best = new double[2];
      fx=new double[particles];
      personal_best=new double[particles][2];
      velocity=new double[particles][2];
      r1=Math.random() * (max_vel - min_vel + 1) + min_vel;
      r2=Math.random() * (max_vel - min_vel + 1) + min_vel;


      for(int i=0;i<particles;i++)
        {
            velocity[i][0]=Math.random() * 0.25;
            velocity[i][1]=Math.random() * 0.25;
            position[i][0]=Math.random() * (max_pos - min_pos+ 1) + min_pos;
            position[i][1]=Math.random() * (max_pos - min_pos+ 1) + min_pos;
            point[i][0]= (int)(position[i][0]*25) +302;
            point[i][1]=(int)(-1*position[i][1]*25)+302;
        }
        
        for(int i=0;i<particles;i++)
        {
            fx[i]=function(position[i][0],position[i][1]);
            if(fx[i]<min)
            {
                min=fx[i];
                pos=i;
            }
            personal_best[i][0]=position[i][0];
            personal_best[i][1]=position[i][1];
        }
          if(min<999999)
          {
          global_best[0]=position[pos][0];
          global_best[1]=position[pos][1];
          } 
    }

    public void paint(Graphics g)
    {
      
      super.paint(g);
      
      Graphics2D g2d = (Graphics2D)(g);
      
      g2d.setColor(Color.BLUE);
		  g2d.fillRect(0, 0, 618, 60);
      g2d.setColor(Color.white);
      g2d.setFont(new Font("Bodoni MT",Font.BOLD,20));
		  g2d.drawString(" Particle Swarm Optimization ", 180, 35);
		
		  g2d.setFont(new Font("Bodoni MT",Font.PLAIN,15));
		  g2d.drawString(" KI : "+0.4, 10, 13);
		  g2d.drawString(" KC : "+cognitive, 10, 33);
		  g2d.drawString(" KS : "+social, 10, 53);
		  g2d.drawString(" Count : "+count, 65, 13);
		  g2d.drawString(" Iteration : "+iterations, 65, 33);
		  g2d.drawString(" Particles : "+particles, 65, 53);
		  g2d.drawString(" X    : "+global_best[0],430 , 13);
		  g2d.drawString(" Y    : "+global_best[1], 430, 33);
		  g2d.drawString(" Min : "+function(global_best[0],global_best[1]), 430, 53);


      g2d.setPaint(Color.blue);      
      g2d.setStroke(new BasicStroke(2));


      g2d.drawLine(0, 362, 603, 362);
      g2d.drawLine(302, 60, 302, 663);

      g2d.setPaint(Color.white);

      for(int i=0;i<603;i+=30)
      {
        g2d.fillOval(i, 360, 5, 5);
      }
      for(int i=60;i<663;i+=30)
      {
        g2d.fillOval(300,i, 5, 5);
      }

      g2d.setPaint(Color.green);

      for(int i=0;i<particles;i++){
              g2d.fillOval(point[i][0],point[i][1]+60,4,4);
      }
    }


    public static double hasen(double x,double y)// Minimum value= -176.541793
	{
		double sum1=0;
		double sum2=0;
		for(int i=1;i<=5;i++)
		{
			sum1+=i*Math.cos((i-1)*x+i);
			sum2+=i*Math.cos((i+1)*y+i);
		}
		sum1*=sum2;
		return sum1;
	}


    double camel(double x,double y)
	{
		double result=(4-(2.1*Math.pow(x, 2))+(Math.pow(x, 4)/3))*Math.pow(x, 2);
		result+=(x*y);
		result+=((-4+4*Math.pow(y, 2))*Math.pow(y, 2));
		return result;
	}
    public  double shubert(double x,double y)// Minimum value= -186.7309
	{
		double sum1=0;
		double sum2=0;
		for(int i=1;i<=5;i++)
		{
			sum1+=i*Math.cos((i+1)*x+i);
			sum2+=i*Math.cos((i+1)*y+i);
		}
		sum1*=sum2;
		return sum1;
	}

	public  double function(double x,double y)
    {
        double sum = hasen(x,y);
        return sum;
    }

  public  double update_velocity(double v,double cognitive,double social,double w,double r1,double r2,double personal_best,double global_best,double x)
    {
        double val=w*v + cognitive*r1*(personal_best-x) + social*r2*(global_best-x);
        return val;
    }

    public  void calculate()
    {
            double min1=999999;
            int pos1=-1;
            double value;
            w = w*0.99;
            if(w<=0.4)
            w=0.4;
            for(int i=0;i<particles;i++)
            {
              r1=random.nextDouble();
              r2=random.nextDouble();
              //Updating the x_coordinate and y_coordinate
                velocity[i][0]=update_velocity(velocity[i][0],cognitive,social,w,r1,r2,personal_best[i][0],global_best[0],position[i][0]);
                position[i][0]+=velocity[i][0];
                if(position[i][0]>max_pos)
                position[i][0] = max_pos;
                if(position[i][0]<min_pos)
                position[i][0]=min_pos;
                
                velocity[i][1]=update_velocity(velocity[i][1],cognitive,social,w,r1,r2,personal_best[i][1],global_best[1],position[i][1]);
                position[i][1]+=velocity[i][1];
                
                if(position[i][1]>max_pos)
                position[i][1] = max_pos;
                if(position[i][1]<min_pos)
                position[i][1]=min_pos;
                
                //plotting the updated coordinate on the grid
                point[i][0] = (int)(position[i][0]*25) + 302;
                point[i][1] = (int)(-1*position[i][1]*25)+302;

                //updating the Personal Best 
                value=function(position[i][0],position[i][1]);
                if(value<min1)
                {
                    min1=fx[i];
                    pos1=i;
                }

                if(fx[i]>value)
                {
                  fx[i]=value;
                    personal_best[i][0] = position[i][0];
                    personal_best[i][1] = position[i][1];
                }
            }
            if(min1<min)
            {
              min = min1;
                    global_best[0]=position[pos1][0];
                    global_best[1]=position[pos1][1];
      }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if(running)
      {
        calculate();
        count++;
        repaint();
        if(count>=iterations)
        {
          running = false;
          if(!running)
            timer.stop();
        }
      }
    }
}
