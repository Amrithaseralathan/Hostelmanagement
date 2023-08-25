/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelmanagement;

/**
 *
 * @author ARUN
 */
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
interface management{
    void display();
    void add();
}
class roomnotfound extends Exception{
    roomnotfound(String s){
        super(s);
    }
}
class studentnotfound extends Exception{
    studentnotfound(String s){
        super(s);
    }
}
class minimumbalance extends Exception{
    minimumbalance(String s){
        super(s);
    }
}
class student{
    int rollno;
    String name;
    int year;
    String add;
    String DOB;
    String pwd;
    int amount;
    static int bal=3000;
    static ArrayList<student> stu=new ArrayList<student>();
    public Scanner in=new Scanner(System.in);
    public void getdetails(){
        System.out.println("Enter your name:");
        this.name=in.next();
        System.out.println("Enter your Rollnumber:");
        this.rollno=in.nextInt();
        System.out.println("Year of pursuing:");
        this.year=in.nextInt();
        System.out.println("Enter your Address:");
        this.add=in.next();
        System.out.println("Enter your DOB:");
        this.DOB=in.next();
        System.out.println("Enter your password:");
        this.pwd=in.next();
        System.out.println("Registered Successfully");
        System.out.println("Pointer check: "+this.name);
       stu.add(this);
      }
}
class room implements management{
    public static ArrayList<Integer> rooms=new ArrayList<Integer>();
    public Scanner in=new Scanner(System.in);
    @Override
    public void add(){
        System.out.println("Enter room number:");
        int num=in.nextInt();
       rooms.add(num);
       System.out.println("Room added Successfull");
    }
   
    public void reserve(int num)throws roomnotfound{
        int c=0;
        for(int i=0;i<rooms.size();i++){
            if(num==rooms.get(i)){
                rooms.set(i, 0);
                c=1;
                System.out.println("Room reserved successfully");
            }
        }
        if(c==0){
           throw  new roomnotfound("Room is already occupied"); 
        }
    }
   @Override
    public void display(){
        System.out.println("Available rooms:");
        for(int i=0;i<rooms.size();i++){
            System.out.println(rooms.get(i));
        }
    }
    
}
class outpass extends student{
     public String place;
     public String start;
     public int no_of_days;
     public int response;
     public static ArrayList<outpass>outstu=new ArrayList<outpass>();
    public void requestpass(){
        System.out.println("Enter Roll Number:");
        super.rollno=in.nextInt();
        System.out.println("Enter your name");
        super.name=in.next();
        System.out.println("Enter visitingplace ");
        this.place=in.next();
        System.out.println("Enter leaving date:");
        this.start=in.next();
        System.out.println("Enter no of days proposed to stay");
        this.no_of_days=in.nextInt();
        outstu.add(this);
       System.out.println("OUTPASS REQUEST SENT");
      
    }
    public void viewresponse(int roll){
        for(int i=0;i<outstu.size();i++){
            if(outstu.get(i).rollno==roll){
                if(outstu.get(i).response==1)
                    System.out.println("Your output was verified");
                else
                    System.out.println("Wait for your approvel");
            }
        }
    }
        
}
class outpassadmin extends outpass {
    void viewpass(){
        System.out.println("View outpass students");
        if(outstu.size()==0){
            System.out.println("No pending students:");
        }
        else{
        for(int i=0;i<outstu.size();i++){
            if(outstu.get(i).response==0){
            System.out.println(outstu.get(i).rollno+" "+outstu.get(i).name+" "+"  "+outstu.get(i).place+" "
            +outstu.get(i).start +outstu.get(i).no_of_days);}
        }
        System.out.println("GIVE RESPONSE BY ENTER ROLL NUMBER:");
        System.out.println("Enter roll number");
        int roll=in.nextInt();
        for(int j=0;j<outstu.size();j++){
           if(outstu.get(j).rollno==roll){
              outstu.get(j).response=1;
          }
            
    }
}  
}
}
class laundry extends student{
    int kg;
    String date;
  
    String receive="no";
    static ArrayList<laundry>laun=new ArrayList<laundry>();
    public void give()throws studentnotfound{
        int c=0;
        System.out.println("Enter your rollnumber");
        super.rollno=in.nextInt();
        for(int i=0;i<stu.size();i++){
            if(stu.get(i).rollno==super.rollno)c=1;
        }
        if(c==1){
        System.out.println("Enter your name:");
        super.name=in.next();
        System.out.println("Enter kg:");
        this.kg=in.nextInt();
        System.out.println("Enter date:");
        this.date=in.next();
        laun.add(this);
        
        }
        else{
           throw new studentnotfound("No student registerd under these roll number"); 
        }
     }
    public void viewstatus(int roll){
        int c=0;
        int i;
       for(i=0;i<laun.size();i++){
           if(laun.get(i).rollno==roll){
               c=1;
               break;
           }
           
       } 
       if(c==1){
           System.out.println("index:"+i);
            System.out.println("Status:"+laun.get(i).receive);
            System.out.println("Balance:"+laun.get(i).bal);
       }
    }
    
}
class laundryadmin extends laundry{
    public void viewpending(){
        if(laun.size()==0){
            System.out.println("No pending list");
        }
        else{
        System.out.println("pending List");
        for(int i=0;i<laun.size();i++){
            if(laun.get(i).receive=="no")
                System.out.println(laun.get(i).rollno+" "+laun.get(i).name+" "+laun.get(i).kg+" "+laun.get(i).date);
        }
        System.out.println("SEND RESPONSE BY ENTERING ROLL NUMBER");
        System.out.println("Enter roll number:");
        int roll=in.nextInt();
        for(int j=0;j<laun.size();j++){
            if(laun.get(j).rollno==roll){
                if(laun.get(j).bal<165){
                    laun.get(j).receive="Amount not sufficient";
                    laun.remove(j);
                }
                else{
                    if(laun.get(j).kg==5){
                        laun.get(j).bal-=165;
                    }
                    if(laun.get(j).kg==8){
                        laun.get(j).bal-=270;
                    }
                   laun.get(j).receive="sent";
                }
                    
                    
            }
        }
    }
    
}
}
class complaint extends student{
   String block;
   String query;
  static  ArrayList<complaint> comp=new ArrayList<complaint>();
    public void raisecomplaint(){
        System.out.println("Enter Roll number:");
        super.rollno=in.nextInt();
      
        System.out.println("Enter your block:");
        this.block=in.next();
        in.nextLine();
        System.out.print("Enter your compliant:");
        this.query=in.nextLine();
        comp.add(this);
    }
    
}
class hosteladmin extends complaint {
    public void viewqueries(){
        System.out.println("---STUDENT COMPLIANTS-------");
        for(int i=0;i<comp.size();i++){
            System.out.println(comp.get(i).rollno+" "+comp.get(i).block+" "+comp.get(i).query);
        }
    }
    
}
class health extends student{
      String problem;
      String ch;
      public void getinfo(){
          System.out.println("Enter your name:");
          super.name=in.next();
          System.out.println("Enter Roll no:");
          super.rollno=in.nextInt();
          System.out.println("Enter your problem:");
          this.problem=in.nextLine();
          System.out.println("do you need ambulance service:");
          this.ch=in.next();      
      
      }
}
class stores extends student{
    String item;
    int count;
    int price;
    static ArrayList<stores>product=new ArrayList<stores>();
    public void buy()throws minimumbalance{
        storesadmin s=new storesadmin();
        s.display();
        System.out.println("Enter your roll number:");
        super.rollno=in.nextInt();
        System.out.println("Enter your items by give the number followed by quantity -:");
        int prod=-1;
        int tot=0;
        while(true){
           
            prod=in.nextInt();
            if(prod==-2){
                break;
            }
          
            int amt=product.get(prod).price;
            int c=in.nextInt();
            tot+=c*amt;
            if(tot>this.bal){
                throw  new minimumbalance("Minimum balance reached");
            }
        }
        System.out.println("Total:"+tot);
        System.out.println("Balance:"+(this.bal-tot));
       }
}
class storesadmin extends stores implements management{
    @Override
    public void add(){
        System.out.println("Enter product name:");
        this.item=in.next();
        System.out.println("Enter quantity:");
        this.count=in.nextInt();
        System.out.println("Enter price per Item:");
        this.price=in.nextInt();
        product.add(this);
        
    }
    @Override
    public void display(){
        System.out.println("-----AVAILABEL PRODUCTS IN OUR STORES-----");
        for(int i=0;i<product.size();i++){
            System.out.println(i+" "+product.get(i).item+" "+product.get(i).price+" "+product.get(i).count);
        }
    }
    
}
class fees extends student implements management{
  String status="not paid";
   static ArrayList<fees>f=new ArrayList<fees>();
   public fees(){
       System.out.println("Hello");
   }
   @Override
   public void add(){
       System.out.println("Enter your name:");
        this.name=in.next();
        System.out.println("Enter your rollnumber:");
        this.rollno=in.nextInt();
        System.out.println("Enter year:");
        this.year=in.nextInt();
        payment p=new payment(this);
        Thread t=new Thread(p);
        t.start();
      try {
          t.join();
      } catch (InterruptedException ex) {
          Logger.getLogger(fees.class.getName()).log(Level.SEVERE, null, ex);
      }
        f.add(this);
       
        System.out.println(this.rollno+" "+this.amount+" "+this.status+" "+LocalDateTime.now());
                
    }
    @Override
    public void display(){
        for(int i=0;i<f.size();i++){
            System.out.println(f.get(i).rollno+" "+f.get(i).amount+" "+f.get(i).status);
    }
 }
}
class payment extends fees implements Runnable{
    fees f;
    public payment(fees f){
        this.f=f;
    }
   public void run(){
         
        System.out.println("NORMAL HOSTEL VEG-32,000 \n NORMAL HOSTEL NON_VEG-40,000\n NRI HOSTEL VEG-50,000 \n NRI HOSTEL NON_VEG-75,000");
        System.out.println("Enter amount:");
        f.amount=in.nextInt();
       System.out.println("Given amt:"+f.name);
        f.status="Success";
        System.out.println("Transaction Completed");
      
        
    }
    
}
    
public class Hostelmanagement{
    
    public static void main(String args[]){
        
        Scanner in=new Scanner(System.in);
        System.out.println("**************HOSTEL MANAGEMENT SYSTEM******************");
        int choice=-1;
        do{
            System.out.println("1.ADMIN \n 2.STUDENT \n 3.LOGOUT");
            choice=in.nextInt();
            int achoice=-1;
            
            if(choice==1){
                do{
                    System.out.println("1.ROOMS \n 2OUT PASS \n 3.LAUNDRY \n 4.STORES \n5.COMPLIANTS\n6.FEES 7.logout");
                    achoice=in.nextInt();
                    if(achoice==1){
                        room r=new room();
                        System.out.println("1.VIEW AVAILABLE ROOMS \n 2.ADD EXTRA ROOM");
                        int rc=in.nextInt();
                        if(rc==1){
                            r.display();
                        }
                        else{
                            r.add();
                        }
                    }
                    if(achoice==2){
                        outpassadmin o=new outpassadmin();
                        System.out.println("VIEW AND RESPOND OUTPASS");
                        o.viewpass();
                        
                    }
                    if(achoice==3){
                        laundryadmin l=new laundryadmin();
                        System.out.println("VIEW PENDING:");
                        l.viewpending();
                        
                    }
                    if(achoice==4){
                        storesadmin sa=new storesadmin();
                        System.out.println("1.ADD NEW PRODUCTS \n 2.VIEW AVAILABLE PRODUCTS");
                        int sc=in.nextInt();
                        if(sc==1){
                            sa.add();
                        }
                        else{
                            sa.display();
                        }
                        
                    }
                    if(achoice==5){
                        hosteladmin h=new hosteladmin();
                        System.out.println("----COMPLAINTS BY STUDENTS--------");
                        h.viewqueries();
                    }
                  if(achoice==6){
                        fees f=new  fees();
                        System.out.println("----PAID STUDENT DETAILS--------");
                        f.display();
                    }
                    
                }while(achoice!=7);
                
            }
            else if(choice==2){
                int schoice;
              do{
                System.out.println("*********WELCOME TO STUDENT PORTAL**********");
                System.out.println("1.REGISTER \n 2.FEES\n 3.OUTPASS \n 4.LAUNDRY\n 5.COMPLAINTS \n 6.STORES \n 7.HEALTH"
                        + "\n8.ROOM \n 9.LOGOUT");
                schoice=in.nextInt();
                if(schoice==1){
                    System.out.println("***START YOUR JOURNEY*****");
                   
                    student s=new student();
                   // s[s1]=new student();
                    //s[s1].getdetails();
                    //s1++;
                    s.getdetails();
                }
                if(schoice==2){
                    System.out.println("Proceed your payment");
                    fees fl=new fees();
                    fl.add();
                  
                    }
                if(schoice==3){
                    System.out.println("1.REQUEST PASS \n 2.VIEW PASS");
                    int ch=in.nextInt();
                    outpass o=new outpass();
                    if(ch==1){
                   
                        o.requestpass();
                       
                    }
                    else{
                        System.out.println("Enter your Roll Number:");
                        int roll=in.nextInt();
                        outpass ot=new outpass();
                        ot.viewresponse(roll);
                    }
                }
                if(schoice==4){
                    laundry l=new laundry();
                    System.out.println("****LAUNDRY PORTAL******");
                    System.out.println("1.GIVE \n 2.VIEW");
                    int ch1=in.nextInt();
                    if(ch1==1){
                        try{
                            l.give();
                        }catch(studentnotfound e){
                            System.out.println(e);
                        }
                    }
                    else{
                       System.out.println("Enter your roll number:");
                       int r=in.nextInt();
                        l.viewstatus(r);
                    }
                }
                if(schoice==5){
                    System.out.println("**********SUBMIT YOUR COMPLAINTS*******");
                    complaint c=new complaint();
                    c.raisecomplaint();
                }
                if(schoice==6){
                    System.out.println("***********STORES*******");
                    stores s=new stores();
                    try{
                    s.buy();
                    }catch(minimumbalance e){
                        System.out.println(e);
                    }
                }
                if(schoice==7){
                    System.out.println("********HEALTH CENTER 24X7**************");
                    health h=new health();
                    h.getinfo();
                }
                if(schoice==8){
                    System.out.println("************ROOMS**********");
                    room r=new room();
                    System.out.println("Enter room no:");
                    int rno=in.nextInt();
                    try{
                    r.reserve(rno);
                    }catch(roomnotfound e){
                        System.out.println(e);
                    }
                }
              }while(schoice!=9);
            }    
     
     }while(choice!=3);
    }
}


  