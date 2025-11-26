import java.io.*;
import java.util.*;

class NoServiceAvailableException extends Exception
{
String msg;

NoServiceAvailableException()
{
super();
msg = null;
}

NoServiceAvailableException(String msg)
{
super(msg);
this.msg = msg;
}

public String toString()
{
return "NoServiceAvailableException :"+msg;
}
}

class Taxi
{
int taxiNo,bookingId,customerId;
char fPoint='A',tPoint='A';
int pTime,dTime;
double amount,tEarnings;

Taxi()
{
}

Taxi(int taxiNo)
{
this.taxiNo = taxiNo;
}

Taxi(Taxi t)
{
taxiNo = t.taxiNo;
bookingId = t.bookingId;
customerId = t.customerId;
fPoint = t.fPoint;
tPoint = t.tPoint;
pTime = t.pTime;
dTime = t.dTime;
amount = t.amount;
tEarnings = t.tEarnings;
}

public String toString()
{
return bookingId+"\t\t"+customerId+"\t\t"+fPoint+"\t"+tPoint+"\t"+pTime+"\t\t"+dTime+"\t\t"+amount+"\n";
}
}

class TaxiApp
{
List<Taxi> list;
List<Taxi> trips = new ArrayList<>();
char fPoint,tPoint;
int pTime;
static int bookingId = 1200;
static int customerId = 2500;
Taxi at;

TaxiApp(int n)
{
list = new ArrayList<>(n);
Taxi t[] = new Taxi[n];
for(int i=0;i<n;i++)
{
t[i] = new Taxi(i+1);
list.add(t[i]);
}
}

boolean check(char k)
{
if(k == 'A' || k == 'B' || k == 'C' || k == 'D' || k == 'E' || k == 'F')
return true;
return false;
}

Taxi findLowDistance(List<Taxi> avail)
{
int minDis = 0;
Taxi ld = null;
for(Taxi k: avail)
if(k.dTime <= pTime)
{
minDis = Math.abs(k.tPoint - fPoint);
ld = k;
break;
}
for(Taxi k: avail)
{
int kmDis = Math.abs(k.tPoint - fPoint);
if(kmDis < minDis)
{
minDis = kmDis;
ld = k;
}
}
return ld;
}

Taxi findLowEarn(List<Taxi> avail)
{
Taxi le = null;
for(Taxi k: avail)
if(k.dTime <= pTime)
{
le=k;
break;
}
for(Taxi k: avail)
if(k.tEarnings < le.tEarnings)
le = k;

return le;
}

void booking()throws Exception
{
at = null;
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
System.out.println("Enter pickup point, drop point & pickup time :");
fPoint = (char)br.read();
br.readLine();
if(check(fPoint) == false)
throw new NoServiceAvailableException("Invalid pickup point");
tPoint = (char)br.read();
br.readLine();
if(check(tPoint) == false)
throw new NoServiceAvailableException("Invalid drop point");
pTime = Integer.parseInt(br.readLine());

List<Taxi> avail = new ArrayList<>();
for(Taxi k : list)
if(k.tPoint == tPoint && k.dTime == pTime)
avail.add(k);
if(avail.size() > 0)
at = new Taxi(findLowEarn(avail));
else
{
for(Taxi k : list)
if(k.dTime <= pTime)
avail.add(k);
if(avail.size() > 0)
at = new Taxi(findLowDistance(avail));
}

if(at == null)
throw new NoServiceAvailableException("No taxi available");
System.out.println("Taxi can be allotted.");
System.out.println("Taxi-"+at.taxiNo+" is allotted");
at.bookingId = bookingId++;
at.customerId = customerId++;
at.fPoint = fPoint;
at.tPoint = tPoint;
at.pTime = pTime;
at.dTime = pTime + Math.abs(fPoint-tPoint);
at.amount = 100+(((Math.abs(fPoint-tPoint)*15)-5)*10);
at.tEarnings += at.amount;
trips.add(at);
for(Taxi k:list)
if(k.taxiNo == at.taxiNo)
{
list.remove(k);
break;
}
list.add(at);
}

void disp()
{
for(Taxi k : list)
if(k.tEarnings > 0)
{
System.out.println("TaxiNo "+"\t"+"Total Earnings");
System.out.println(k.taxiNo+"\t"+k.tEarnings);
dispAll(k);
}
}

void dispAll(Taxi t)
{
for(Taxi k : trips)
if(k.taxiNo == t.taxiNo)
{
System.out.println("BookingId" +"\t"+"CustomerId"+"\t"+"Pickup"+"\t"+"Drop"+"\t"+"PickupTime"+"\t"+"DropTime"+"\t"+"Amount");
System.out.println(k);
}
}
}

class TaxiAppDemo
{
public static void main(String arg[])throws IOException
{
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
System.out.println("Enter no.of taxi :");
int n = Integer.parseInt(br.readLine());
TaxiApp ta = new TaxiApp(n);
int ch=0;
do
{
try
{
System.out.println("****CALL TAXI APP****");
System.out.println("Booking ---1");
System.out.println("Display Details ---2");
System.out.println("Exit ---3");
System.out.println("Enter choice :");
ch = Integer.parseInt(br.readLine());
switch(ch)
{
case 1:
ta.booking();
break;

case 2:
ta.disp();
break;

case 3:
break;

default:
System.out.println("Invalid choice");
break;
}}
catch(Exception e)
{System.out.println(e);}

}while(ch != 3);

}
}
