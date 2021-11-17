package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.ArrayList;

// @Author Oriol Cuellar

public class RateRecomendation {

    private static ArrayList<myPair> arr;

    private static float result;
    public  RateRecomendation(ArrayList<myPair> arr2 ){
        this.arr=arr2;
        result=-1;
    }
    public void execute(){
        float res=0;
        for (int i=0;i<arr.size();++i){
            double top=(Math.pow(2, arr.get(i).getValoration())-1);
            double down=(Math.log10(i+1+1) / Math.log10(2));
            top=top/down;
            res+=top;
        }
        result=res;
    }
    public float getResult(){
        return result;
    }
}
