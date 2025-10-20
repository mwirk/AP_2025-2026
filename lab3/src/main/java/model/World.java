package model;

import java.util.ArrayList;
import java.util.List;

public class World {
    private static List<Corporation> corporationList = new ArrayList<>();
    public static List<Corporation> getCorporationList(){
        return corporationList;
    }
    public static void addNewToCorporationList(Corporation corporation){
        corporationList.add(corporation);
    }
}
