package week4.src.main.java.com.App.DisruptLecture;

public class DisruptLecture {
    public static void main(String[] args) {
    CellPhone noiseMaker = new CellPhone();
    ObnoxiousTune ot = new ObnoxiousTune();
    noiseMaker.ring(ot); 
    }
   }