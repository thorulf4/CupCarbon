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
        String[] message = Serialize.nextElements(data,3);
        PackageType type = convertToType(message[0]);

        return new TypedPackage(type, message[1], message[2]);
    }

    private static PackageType convertToType(String typeString) {
        int typeOrdinal = Integer.parseInt(typeString);
        return PackageType.values()[typeOrdinal];
    }

}

