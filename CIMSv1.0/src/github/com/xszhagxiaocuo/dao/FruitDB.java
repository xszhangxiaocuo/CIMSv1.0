package github.com.xszhagxiaocuo.dao;

import github.com.xszhagxiaocuo.entity.Fruit;

import java.util.ArrayList;
import java.util.List;

public class FruitDB {
    private static List<Fruit> fruits = new ArrayList<>();//模拟数据库中的一张表
    //获取表中的所有记录
    public static List<Fruit> getFruits() {
        return fruits;
    }
    //数据初始化
    static {
        Fruit fruit1 = new Fruit(1,"苹果",2,1,"1");
        fruits.add(fruit1);
        Fruit fruit2 = new Fruit(2,"banana",1,3,"2");
        fruits.add(fruit2);
        Fruit fruit3 = new Fruit(3,"orange",3,2,"3");
        fruits.add(fruit3);
        Fruit fruit4 = new Fruit(4,"grapes",1,2,"4");
        fruits.add(fruit4);
        Fruit fruit5 = new Fruit(5,"watermelon",2,1,"5");
        fruits.add(fruit5);
    }

    public static int FindIndex(Integer id) {
        int index = -1;
        for (int i = 0; i < fruits.size(); i++) {
            if (fruits.get(i).getId() == id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return -1;
        }
        return index;
    }

    public static boolean Add(Fruit fruit){
        //先查询是否存在同id的数据
        List<Fruit> f = SearchById(fruit.getId());
        if (f!=null){
            return false;
        }
        try {
            fruits.add(fruit);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public static boolean Remove(Integer id){
        int index = FindIndex(id);
        try {
            fruits.remove(fruits.get(index));
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public static boolean Modify(Fruit fruit){
        int index=index=FindIndex(fruit.getId());
        if (index==-1){
            return false;
        }
        fruits.set(index,fruit);
        return true;
    }

    public static List<Fruit> SearchById(int id){
        List<Fruit> result = new ArrayList<>();
        int index = FindIndex(id);
        if (index!=-1){
            result.add(fruits.get(index));
            return result;
        }
        return null;
    }

    public static List<Fruit> SearchByName(String name){
        List<Fruit> result = new ArrayList<>();
        for (Fruit fruit : fruits) {
            if (fruit.getName().contains(name)){
                result.add(fruit);
            }
        }
        if (result.size()==0){
            return null;
        }
        return result;
    }

    public static List<Fruit> SearchByPrice(int price){
        List<Fruit> result = new ArrayList<>();
        for (Fruit fruit : fruits) {
            if (fruit.getPrice()==price){
                result.add(fruit);
            }
        }
        if (result.size()==0){
            return null;
        }
        return result;
    }

    public static List<Fruit> SearchByNumber(int number){
        List<Fruit> result = new ArrayList<>();
        for (Fruit fruit : fruits) {
            if (fruit.getNumber()==number){
                result.add(fruit);
            }
        }
        if (result.size()==0){
            return null;
        }
        return result;
    }

    public static List<Fruit> SearchByRemark(String remark){
        List<Fruit> result = new ArrayList<>();
        for (Fruit fruit : fruits) {
            if (fruit.getRemark().contains(remark)){
                result.add(fruit);
            }
        }
        if (result.size()==0){
            return null;
        }
        return result;
    }

    public static List<Fruit> Search(String field,String key){
        try {
            switch (field) {
                case "id":
                    return SearchById(Integer.parseInt(key));
                case "name":
                    return SearchByName(key);
                case "price":
                    return SearchByPrice(Integer.parseInt(key));
                case "number":
                    return SearchByNumber(Integer.parseInt(key));
                case "remark":
                    return SearchByRemark(key);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
