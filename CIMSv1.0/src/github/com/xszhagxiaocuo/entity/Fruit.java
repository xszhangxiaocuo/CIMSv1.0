package github.com.xszhagxiaocuo.entity;

import github.com.xszhagxiaocuo.dao.FruitDB;

public class Fruit {
    private static  final long serialVersionUID = 1L;
    private int id;//在表中从上到下的顺序
    private int realId;//在表中的真实id
    private String name;
    private float price;
    private int number;
    private String remark;

    public Fruit(){}

    public Fruit(String name,float price,int number,String remark){
        this.name = name;
        this.price = price;
        this.number = number;
        this.remark = remark;
    }

    public void setRealId(int realId) {
        this.realId = realId;
    }

    public int getRealId() {
        return realId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){this.id=id;}

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice(){
        return price;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getRemark(){
        return remark;
    }
}
