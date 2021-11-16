package src.domini.model;


import java.util.ArrayList;

public class RateRecomendation {

    private static ArrayList<myPair> arr;

    public  RateRecomendation(ArrayList<myPair> arr2 ){
        this.arr=arr2;
    }
    public void execute(){
        float res=0;
        for (int i=0;i<arr.size();++i){
            //System.out.println(" ");
            double top=(Math.pow(2, arr.get(i).getValoration())-1);
            //System.out.println(arr.get(i).getValoration());
            double down=(Math.log10(i+1+1) / Math.log10(2));

            //System.out.println(top);
            //System.out.println(down);
            top=top/down;
            res+=top;

        }
        System.out.println(res);
    }
}
