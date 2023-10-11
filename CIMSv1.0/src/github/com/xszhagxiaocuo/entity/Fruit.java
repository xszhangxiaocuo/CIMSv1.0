package github.com.xszhagxiaocuo.entity;

public class Fruit {
    private static  final long serialVersionUID = 1L;
    private int id;
    private String name;
    private int price;
    private int number;
    private String remark;
    public Fruit(){
    }
    public Fruit(int id,String name){
        this.id = id;
        this.name = name;
    }

    public Fruit(int id,String name,int price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Fruit(int id,String name,int price,int number){
        this.id = id;
        this.name = name;
        this.price = price;
        this.number = number;
    }

    public Fruit(int id,String name,int price,int number,String remark){
        this.id = id;
        this.name = name;
        this.price = price;
        this.number = number;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice(){
        return price;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getRemark(){
        return remark;
    }
}
