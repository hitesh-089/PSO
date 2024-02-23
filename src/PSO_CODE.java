import java.io.*;
import java.util.Random;

public class PSO_CODE
{
    static Random random = new Random();
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
    public static double shubert(double x,double y)// Minimum value= -186.7309
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
	public static double function(double x,double y)
    {
       // double val = (x*x)-(x*y)+(y*y)+2*x+4*y+3;
       //return val;
        double sum = hasen(x,y);
        return sum;
    }
    public static double update_velocity(double v,double c1,double c2,double w,double r1,double r2,double pbest,double gbest,double x)
    {
        double inertia = w*v;
        double val=inertia + c1*r1*(pbest-x) + c2*r2*(gbest-x);
        return val;
    }
    public static void print(double velocity[][],double position[][],int pop_size,long max_itr, double pbest[][],double gbest[],int pos,double fx[]){
        //  System.out.println("               Velocity                                        Position                               f(x)                        pbest");
        //         System.out.println("x                        y                       x                        y                                        x                y");
        //     for(int j=0;j<pop_size;j++)
        //             System.out.println(velocity[j][0]+"  "+velocity[j][1]+"     "+position[j][0]+"   "+position[j][1]+"           "+fx[j]+"             "+pbest[j][0]+"      "+pbest[j][1]);
                
        for(int i = 0;i<pop_size;i++)
        System.out.println("x: "+position[i][0]+" y: "+position[i][1]);
                    System.out.println("Gbest = " +gbest[0]+ " " + gbest[1]);
                    System.out.println("f(x) min " + fx[pos] + " at iteration = "+ max_itr );
    }

    public static double[] calculate(int pop_size,long max_itr,double[][] velocity,double position[][],
                                    double c1,double c2,double w,double r1,double r2,double[][] pbest,double gbest[],
                                    int max_pos,int min_pos,double[] fx,double min,int pos)
    {
        while(max_itr>0)
        {
            double min1=999999;
            int pos1=-1;
            double value;
            w = w*0.99;
            if(w<=0.4)
            w=0.4;
            for(int i=0;i<pop_size;i++)
            {
              r1=random.nextDouble();
              r2=random.nextDouble();
                velocity[i][0]=update_velocity(velocity[i][0],c1,c2,w,r1,r2,pbest[i][0],gbest[0],position[i][0]);
                position[i][0]+=velocity[i][0];
                if(position[i][0]>max_pos)
                position[i][0] = max_pos;
                if(position[i][0]<min_pos)
                position[i][0]=min_pos;
                
                velocity[i][1]=update_velocity(velocity[i][1],c1,c2,w,r1,r2,pbest[i][1],gbest[1],position[i][1]);
                position[i][1]+=velocity[i][1];
                
                if(position[i][1]>max_pos)
                position[i][1] = max_pos;
                if(position[i][1]<min_pos)
                position[i][1]=min_pos;
                
                value=function(position[i][0],position[i][1]);
                if(value<min1)
                {
                    min1=fx[i];
                    pos1=i;
                }

                if(fx[i]>value)
                {
                  fx[i]=value;
                    pbest[i][0] = position[i][0];
                    pbest[i][1] = position[i][1];
                }
                
            }
            if(min1<min)
            {
              min = min1;
                    gbest[0]=position[pos1][0];
                    gbest[1]=position[pos1][1];
                }

                    print(velocity, position, pop_size, max_itr, pbest,gbest,pos,fx);
            max_itr--;
        }       
        return gbest;
    }
    public static void main (String[] args) throws IOException
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        /*Main obj= new Main();*/
        //Declaring Variable as input
        System.out.println("Population Size=" );
        int pop_size =Integer.parseInt(br.readLine());
        System.out.println("C1 = ");
        double c1=Double.parseDouble(br.readLine());
        System.out.println("C2 = ");
        double c2=Double.parseDouble(br.readLine());
        System.out.println("Max Iteration = ");
        long max_itr = Long.parseLong(br.readLine());
        System.out.println("Inertia Weight(w) = ");
        double w=Double.parseDouble(br.readLine());
        double velocity[][]=new double[pop_size][2];
        double position[][]=new double[pop_size][2];
        double fx[]=new double[pop_size];
        double pbest[][]=new double[pop_size][2];
        double gbest[]=new double[2];
        int max_vel=1;
        int min_vel=0;
        int max_pos=10;
        int min_pos=-10;
        double r1=Math.random() * (max_vel - min_vel + 1) + min_vel;
        double r2=Math.random() * (max_vel - min_vel + 1) + min_vel;
        //double r1=2;
        //double r2=2;
        //initialize variables;
        
        for(int i=0;i<pop_size;i++)
        {
            velocity[i][0]=Math.random() * (max_vel - min_vel+ 1) + min_vel;
            velocity[i][1]=Math.random() * (max_vel - min_vel+ 1) + min_vel;
            position[i][0]=Math.random() * (max_pos - min_pos+ 1) + min_pos;
            position[i][1]=Math.random() * (max_pos - min_pos+ 1) + min_pos;
        }
        double min=999999;
        int pos=-1;
        for(int i=0;i<pop_size;i++)
        {
            fx[i]=function(position[i][0],position[i][1]);
            if(fx[i]<min)
            {
                min=fx[i];
                pos=i;
            }
            pbest[i][0]=position[i][0];
            pbest[i][1]=position[i][1];
        }
        if(min<999999)
        {
        gbest[0]=position[pos][0];
        gbest[1]=position[pos][1];
    } 
        calculate(pop_size,max_itr,velocity,position,c1,c2,w,r1,r2,pbest,gbest,max_pos,min_pos,fx,min,pos);
        print(velocity, position, pop_size, max_itr, pbest,gbest,pos,fx);
        System.out.println("Out of Loop");
        System.out.println("global minima = "+function(gbest[0],gbest[1]));
    }
}