package d504;

import d504.utils.Serialize;

public class TypedPackage {

    public PackageType type;
    public String nodeID;
    public String packageData;

    public TypedPackage(PackageType type, String nodeID, String packageData){
        this.type = type;
        this.nodeID = nodeID;
        this.packageData = packageData;
    }

    public String serialize(){
        return type.ordinal() + "&" + nodeID + "&" + packageData;
    }

    public static TypedPackage deserialize(String data){
        String[] message = Serialize.nextElements(data,2);
        String packageData = Serialize.removeElements(data, 2);
        PackageType type = convertToType(message[0]);

        return new TypedPackage(type, message[1], packageData);
    }

    private static PackageType convertToType(String typeString) {
        int typeOrdinal = Integer.parseInt(typeString);
        return PackageType.values()[typeOrdinal];
    }

}

