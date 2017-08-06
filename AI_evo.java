package ai_evo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;


class AI {
    public double x[],y[],z[],fit_probability[],population[][];
    public double minx,maxx,miny,maxy,sum;
    public AI(double minx, double maxx, double miny, double maxy) {
        this.minx = minx;
        this.maxx = maxx;
        this.miny = miny;
        this.maxy = maxy;
        x=new double[10];
        y=new double[10];
        z=new double[10];
        fit_probability=new double[10];
        population=new double[4][10];
    }  
    public void Initialization(){
        Random();
        z();
        fit_probability=fitness(z,sum);
        population=population(x,y,z,fit_probability);
        for (int i = 0; i < 10; i++)
            {System.out.println("Iteration#"+(i+1));
            //for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 10; k++) {
                    /*try{
                    File write=new File("C:\\Users\\abdul.DESKTOP-D3MALQ9\\Desktop\\abcd.txt");
                    //write.createNewFile();
                    FileOutputStream is=new FileOutputStream(write);
                    OutputStreamWriter osw=new OutputStreamWriter(is);
                    Writer w=new BufferedWriter(osw);
                    w.append("x="+x[i]+" y="+y[i]+" z="+z[i]+"\n");
                    w.close();
                    }catch(IOException e){}
                    */
                    
    System.out.println(k+"."+"x="+population[0][k]+"  y="+population[1][k]+"  z="+population[2][k]);
                    //System.out.println();
                }population=selection();
            //}
        }   
    }
    public void Random(){
        Random r=new Random();                                    
        for (int i = 0; i < 10; i++) {
            x[i]=r.nextDouble()*(maxx-minx)+minx;
        }
        for (int i = 0; i < 10; i++) {
            y[i]=r.nextDouble()*(maxy-miny)+miny;
        }
    }
    public void z(){
        for (int i = 0; i < 10; i++) {
            z[i]=function(x[i],y[i]);
            sum+=z[i];
        }
    }
    public double[] fitness(double z[],double sum){
        double prob[]=new double[10];
        for (int i = 0; i < 10; i++) {
            prob[i]=(z[i]/sum);
            //System.out.println("probability="+prob[i]);
        }
        return prob;
    }
    public double[][] population(double x[],double y[],double z[],double prob[]){
        double population[][]=new double[4][10];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                if(i==0){
                    population[i][j]=x[j];
                }
                else if(i==1)
                    population[i][j]=y[j];
                else if(i==2)
                    population[i][j]=z[j];
                else
                    population[i][j]=prob[j];
            }
        }
        return population;
    }
    public double[][] selection(){
        mutation();
        double temp[][]=new double[4][10];
        temp=population(x,y,z,fit_probability);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (temp[3][i]>population[3][j]) {
                    population[0][j]=temp[0][i];
                    population[1][j]=temp[1][i];
                    population[2][j]=temp[2][i];
                    population[3][j]=temp[3][i];
                }
            }       
        }
        return temp;
    }
    public void crossover(){
        double temp[]=new double[5];
        temp[0]=0;
        for (int i = 0; i < 5; i++) {
            temp[i]=search(temp[0]);
        }
    }
    public double search(double pre){
        double temp[]=new double[10];
        double tempx=0;
        for (int i = 0; i < 10; i++) {
            temp[i]=population[3][i];
            if(temp[i]>tempx)
                tempx=temp[i];
        }
        return tempx;
    }
    public void mutation(){
        //double temp_x[]=new double[10],temp_y[]=new double[10],temp_z[]=new double[10],temp_prob[]=new double[10],
        double add = 0;
        for (int i = 0; i < 10; i++) {
                x[i]=x[i]-0.25;
                double temp=function(x[i],y[i]);
                if(temp>z[i])
                    z[i]=temp;
                else{
                    x[i]=x[i]+0.5;
                    temp=function(x[i],y[i]);
                    if(temp>z[i])
                        z[i]=temp;
                    else
                        x[i]=x[i]-0.25;
                }
                y[i]=y[i]-0.25;
                temp=function(x[i],y[i]);
                if(temp>z[i])
                    z[i]=temp;
                else{
                    y[i]=y[i]+0.5;
                    temp=function(x[i],y[i]);
                    if(temp>z[i])
                        z[i]=temp;
                    else
                        y[i]=y[i]-0.25;
                }
                if(x[i]>maxx)
                    x[i]=maxx;
                if(x[i]<minx)
                    x[i]=minx;
                if(y[i]>maxy)
                    y[i]=maxy;
                if(y[i]<miny)
                    y[i]=miny;
            z[i]=function(x[i],y[i]);
            add+=z[i];
        }   
        fit_probability=fitness(z,add);
    }
    
    private double function(double x,double y){
        return Math.pow(1-x, 2)+100*Math.pow(Math.pow(x, 2)-y,2);
        //return Math.pow(x, 2)+Math.pow(y, 2);
    }
}
public class AI_evo {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        System.out.println("Enter the max value of x");
        double max_x=scan.nextDouble();
        System.out.println("Enter the min value of x");
        double min_x=scan.nextDouble();
        System.out.println("Enter the max value of y");
        double max_y=scan.nextDouble();
        System.out.println("Enter the min value of y");
        double min_y=scan.nextDouble();
        AI gene=new AI(min_x,max_x,min_y,min_y);
        gene.Initialization();
    }
}