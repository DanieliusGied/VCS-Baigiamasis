//package lt.vcs.baigiamasis.repository;
//
//import static lt.vcs.baigiamasis.inventory.model.ItemType.WEAPON;
//import static lt.vcs.baigiamasis.inventory.model.ItemType.ARMOR;
//
//import androidx.room.TypeConverter;
//
//import lt.vcs.baigiamasis.common.Constant;
//import lt.vcs.baigiamasis.inventory.model.ItemType;
//
//public class Converter {
//    @TypeConverter
//    public static ItemType enumFromString(String itemType) {
//        if (itemType == null) {
//            return null;
//        } else if (itemType == "WEAPON") {
//            return WEAPON;
//        } else if (itemType == "ARMOR") {
//            return ARMOR;
//    } else return null;
//}
//
//    @TypeConverter
//    public static String enumToString(ItemType itemType){
//        if (itemType == null) {
//            return null;
//        } else if (itemType == WEAPON) {
//            return Constant.WEAPON;
//        } else if (itemType == ARMOR) {
//            return Constant.ARMOR;
//        } else return null;
//    }
//}
